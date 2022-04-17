package com.backend.admin.service;

import com.backend.admin.dto.RevokeCertificateDTO;
import com.backend.admin.model.CertificateInfo;
import com.backend.admin.model.CertificateSigningRequest;
import com.backend.admin.model.Revocation;
import com.backend.admin.model.enums.CertificateStatus;
import com.backend.admin.model.enums.CertificateType;
import com.backend.admin.repository.CertificateInfoRepository;
import lombok.AllArgsConstructor;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class CertificateService {
    private final CertificateInfoRepository certificateInfoRepository;
    private final SignatureService signatureService;
    private final CertificateKeyStoreService certificateKeyStoreService;
    private final UUIDService uuidService;

    private final String intermAlias = "adagradinterm";
    private final String rootAlias = "adagrad root";

    public X509Certificate generateCertificate(CertificateSigningRequest request) throws Exception {
        Provider provider = new BouncyCastleProvider();
        Security.addProvider(provider);

        // builder to create object which contain issuer private key, used for signing
        JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption").setProvider(provider);

        // getting issuer certificate
        X509Certificate issuerCert = certificateKeyStoreService.readCertificate(intermAlias);

        // getting issuer private key
        PrivateKey issuerPrivateKey = certificateKeyStoreService.readPrivateKey(intermAlias);

        // building the object containing the private key, used for signing
        ContentSigner contentSigner = null;
        try {
            contentSigner = builder.build(issuerPrivateKey);
        } catch (OperatorCreationException e) {
            e.printStackTrace();
        }

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

        KeyPair keyPair = signatureService.generateKeys();

        // TODO generating new serial number same way as UID?
        String newSerial = uuidService.getUUID();

        // setting cert data
        X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(
                new JcaX509CertificateHolder(issuerCert).getSubject(),
                new BigInteger(newSerial), new Date(), request.getEndDate(), x500Name, keyPair.getPublic());

        // E X T E N S I O N S
        addExtensions(request, certGen);

        X509CertificateHolder certHolder = certGen.build(contentSigner);
        JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
        certConverter = certConverter.setProvider(provider);

        // converting holder to certificate
        X509Certificate newCertificate = certConverter.getCertificate(certHolder);

        // TODO How do we come up with aliases?
        String newAlias = request.getEmail();

        // getting root for certificate chain
        X509Certificate root = certificateKeyStoreService.readCertificate(rootAlias);

        // NOTICE -> this chain is only valid if we have one root and one intermediate!
        X509Certificate[] certificateChain = {newCertificate, issuerCert, root};

        // Verify the issued cert signature against the issuer cert
        try {
            newCertificate.verify(issuerCert.getPublicKey(), provider);
        } catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException e) {
            e.printStackTrace();
        }

        // saving new certificate (with hierarchy chain) to KS
        certificateKeyStoreService.write(newAlias, certificateChain, keyPair.getPrivate());

        certificateInfoRepository.save(new CertificateInfo(null, newSerial, newAlias, request.getEmail(),
                CertificateType.END_ENTITY, null));

        return newCertificate;
    }

    private void addExtensions(CertificateSigningRequest request, X509v3CertificateBuilder certificateBuilder)
            throws NoSuchFieldException, IllegalAccessException, CertIOException {

        if (request.getKeyUsage() != null) {
            Class<KeyUsage> keyUsage = KeyUsage.class;
            Field field;
            int usage = 0;
            for (String variable : request.getKeyUsage()) {
                field = keyUsage.getField(variable);
                usage |= field.getInt(null);
            }
            KeyUsage ku = new KeyUsage(usage);
            certificateBuilder.addExtension(Extension.keyUsage, true, ku);
        }

        if (request.getExtendedKeyUsage() != null) {
            Class<KeyPurposeId> keyPurposeId = KeyPurposeId.class;
            Field field;
            List<KeyPurposeId> keyPurposeIdList = new ArrayList<>();
            for (String variable : request.getExtendedKeyUsage()) {
                field = keyPurposeId.getField(variable);
                keyPurposeIdList.add((KeyPurposeId) field.get(null));
            }
            ExtendedKeyUsage eku = new ExtendedKeyUsage(keyPurposeIdList.toArray(new KeyPurposeId[0]));
            certificateBuilder.addExtension(Extension.extendedKeyUsage, true, eku);
        }

        if (request.getPathLenConstraint() != null && request.getPathLenConstraint() > 0) {
            certificateBuilder.addExtension(Extension.basicConstraints, true,
                    new BasicConstraints(request.getPathLenConstraint()));
        } else if (request.isCA()) {
            certificateBuilder.addExtension(Extension.basicConstraints, true, new BasicConstraints(true));
        }
    }

    public void revokeCertificate(RevokeCertificateDTO revokeCertificateDTO) throws Exception {
        CertificateInfo info = findInfoBySerialNumber(revokeCertificateDTO.getSerialNumber());

        if (info.getRevocation() != null)
            throw new Exception("Certificate already revoked.");

        info.setRevocation(new Revocation(revokeCertificateDTO.getSerialNumber(),
                revokeCertificateDTO.getRevokerId(), new Date(), revokeCertificateDTO.getReason()));

        certificateInfoRepository.save(info);
    }

    public X509Certificate findBySerialNumber(String serialNumber) throws Exception {
        CertificateInfo info = findInfoBySerialNumber(serialNumber);
        return certificateKeyStoreService.readCertificate(info.getAlias());
    }

    public CertificateStatus checkStatus(String serialNumber) throws Exception {
        CertificateInfo info = findInfoBySerialNumber(serialNumber);
        X509Certificate certificate = certificateKeyStoreService.readCertificate(info.getAlias());

        try {
            certificate.checkValidity();
        } catch (CertificateExpiredException | CertificateNotYetValidException e) {
            return CertificateStatus.EXPIRED; // NotYetValidException won't happen
        }

        if (info.getRevocation() != null)
            return CertificateStatus.REVOKED;

        return CertificateStatus.VALID;
    }

    private CertificateInfo findInfoBySerialNumber(String serialNumber) throws Exception {
        return certificateInfoRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new Exception("Certificate not found."));
    }

    public void loadCertificateToFile(String serialNumber) throws Exception {
        String LINE_SEPARATOR = System.getProperty("line.separator");
        Base64.Encoder encoder = Base64.getMimeEncoder(64, LINE_SEPARATOR.getBytes());
        byte[] bytes = findBySerialNumber(serialNumber).getEncoded();

        String certificate = "-----BEGIN CERTIFICATE-----" +
                LINE_SEPARATOR + new String(encoder.encode(bytes)) + LINE_SEPARATOR
                + "-----END CERTIFICATE-----";

        try (FileOutputStream fos = new FileOutputStream("certificates/" + serialNumber + ".cer")) {
            fos.write(certificate.getBytes());
        }
    }

    public List<CertificateInfo> getCertificateInfos() {
        return certificateInfoRepository.findAll();
    }
}