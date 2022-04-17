package com.backend.admin.service;

import java.lang.reflect.Field;

import java.math.BigInteger;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.stereotype.Service;

import com.backend.admin.model.CertificateSigningRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CertificateService {

    private final SignatureService signatureService;
    private final KeyStoreReaderService keyStoreReaderService;
    private final KeyStoreWriterService keyStoreWriterService;
    private final UUIDService uuidService;

    private final String rootKeyStoreFile = "rootKeyStore.jks";
    private final String rootKeyStorePassword = "admin"; // TODO hide pass, switch to config constants
    private final String intermAlias = "adagradinterm";
    private final String rootAlias = "adagrad root";

    public X509Certificate generateCertificate(CertificateSigningRequest request) throws CertificateException,
            NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, CertIOException {

        // fixes the "no such provider: BC" exception
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        // builder to create object which contain issuer private key, used for signing
        JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
        builder = builder.setProvider("BC");

        // getting issuer certificate
        X509Certificate issuerCert = keyStoreReaderService.readCertificate(rootKeyStoreFile, rootKeyStorePassword,
                intermAlias);

        // getting issuer private key
        PrivateKey issuerPrivateKey = keyStoreReaderService.readPrivateKey(rootKeyStoreFile, rootKeyStorePassword,
                intermAlias, rootKeyStorePassword);

        // building the object containing the private key, used for signing
        ContentSigner contentSigner = null;
        try {
            contentSigner = builder.build(issuerPrivateKey);
        } catch (OperatorCreationException e) {
            e.printStackTrace();
        }

        // generating x500 name based on CSR info
        X500NameBuilder x500NameBuilder = new X500NameBuilder(BCStyle.INSTANCE);

        x500NameBuilder.addRDN(BCStyle.CN, request.getCommonName());
        x500NameBuilder.addRDN(BCStyle.SURNAME, request.getLastName());
        x500NameBuilder.addRDN(BCStyle.GIVENNAME, request.getFirstName());
        x500NameBuilder.addRDN(BCStyle.O, request.getOrganization());
        x500NameBuilder.addRDN(BCStyle.OU, request.getOrganizationalUnit());
        x500NameBuilder.addRDN(BCStyle.C, request.getCountry());
        x500NameBuilder.addRDN(BCStyle.E, request.getEmail());
        x500NameBuilder.addRDN(BCStyle.L, request.getCity());
        x500NameBuilder.addRDN(BCStyle.ST, request.getState());

        // generate UUID for user ID (UID)
        String id = uuidService.getUUID();
        x500NameBuilder.addRDN(BCStyle.UID, String.valueOf(id));
        X500Name x500Name = x500NameBuilder.build();

        // generating new key pair
        KeyPair keyPair = signatureService.generateKeys();

        // TODO generating new serial number same way as UID?
        String newSerial = uuidService.getUUID();

        // setting cert data
        X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(
                new JcaX509CertificateHolder(issuerCert).getSubject(), // vezbe: issuerData.getX500name()
                new BigInteger(newSerial), new Date(), request.getEndDate(), x500Name, keyPair.getPublic()); // newly
                                                                                                             // generated
                                                                                                             // public
                                                                                                             // key

        // E X T E N S I O N S
        if (request.getKeyUsageExtension() != null) {
            Class<KeyUsage> keyUsage = KeyUsage.class;
            Field field;
            int usage = 0;
            for (String variable : request.getKeyUsageExtension()) {
                field = keyUsage.getField(variable);
                usage |= field.getInt(null);
            }
            KeyUsage ku = new KeyUsage(usage);
            certGen.addExtension(Extension.keyUsage, true, ku);
        }

        X509CertificateHolder certHolder = certGen.build(contentSigner);
        JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
        certConverter = certConverter.setProvider("BC");

        // converting holder to certificate
        X509Certificate newCertificate = certConverter.getCertificate(certHolder);

        // TODO How do we come up with aliases?
        // Maybe emails?
        String newAlias = request.getEmail();

        // getting root for certificate chain
        X509Certificate root = keyStoreReaderService.readCertificate(rootKeyStoreFile, rootKeyStorePassword, rootAlias);

        // NOTICE -> this chain is only valid if we have one root and one interm!
        X509Certificate[] certificateChain = { newCertificate, issuerCert, root };

        // Verify the issued cert signature against the issuer cert
        try {
            newCertificate.verify(issuerCert.getPublicKey(), "BC");
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException | SignatureException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // saving new certificate (with hierarchy chain) to KS
        keyStoreWriterService.write(newAlias, keyPair.getPrivate(), "rootKeyStore.jks", "admin", certificateChain);

        // TODO Do we need this saving logic or are we saving all to the same ks?

        // if(data.isRootCert()) {
        // _keyStoreWriterService.write(data.getEmail(), keyPair.getPrivate(),
        // config.getRootFileName(), config.getKsPassword(),
        // certConverter.getCertificate(certHolder));
        // }
        // else if(data.isCertificateAuthority()){
        // _keyStoreWriterService.write(data.getEmail(), keyPair.getPrivate(),
        // config.getCAFileName(), config.getKsPassword(),
        // certConverter.getCertificate(certHolder));
        // }else {
        // _keyStoreWriterService.write(data.getEmail(), keyPair.getPrivate(),
        // config.getEnd_userFileName(), config.getKsPassword(),
        // certConverter.getCertificate(certHolder));
        // }

        return newCertificate;

    }
}