package com.backend.admin.service;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.X509Extensions;
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

import com.backend.admin.model.SubjectData;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CertificateService {

    private final SignatureService signatureService;

    //TODO make Certificate Signing Request a parameter instad of SubjectData
    public X509Certificate generateCertificate(SubjectData subjectData) throws CertificateException {

        // builder to create object which contain issuer private key, used for signing
        JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
        builder = builder.setProvider("BC");

        // TODO getting issuer certificate from wherever we generated it
        X509Certificate issuerCert = null; 

        // TODO getting issuer private key from wherever we generated it
        PrivateKey issuerPrivateKey = null;

        // building the object containing the private key, used for signing
        ContentSigner contentSigner = null;
        try {
            contentSigner = builder.build(issuerPrivateKey);
        } catch (OperatorCreationException e) {
            e.printStackTrace();
        }

        //TODO generating x500Name
        X500Name x500Name = null;
        X500NameBuilder x500NameBuilder = new X500NameBuilder(BCStyle.INSTANCE);

        // String cn = data.getFirstName() + ' ' + data.getLastName();
        // builder.addRDN(BCStyle.CN, cn);
        // builder.addRDN(BCStyle.SURNAME, data.getLastName());
        // builder.addRDN(BCStyle.GIVENNAME, data.getFirstName());
        // builder.addRDN(BCStyle.O, data.getOrganisation());
        // builder.addRDN(BCStyle.OU, data.getOrganisationUnit());
        // builder.addRDN(BCStyle.C, data.getCountry());
        // builder.addRDN(BCStyle.E, data.getEmail());

        String id = null; //TODO generate UUID
        // builder.addRDN(BCStyle.UID, String.valueOf(id)); //UID = User Id
        x500Name = x500NameBuilder.build();
        
        //generating new key pair
        // TODO maybe put this in CSR creation???
        KeyPair keyPair = signatureService.generateKeys();

        //setting cert data
        X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(new JcaX509CertificateHolder(issuerCert).getSubject(), //vezbe: issuerData.getX500name()
                new BigInteger(subjectData.getSerialNumber()),
                subjectData.getStartDate(),
                subjectData.getEndDate(),
                x500Name, 
                keyPair.getPublic()); //newly generated public key


        ////TODO adding extensions based on csr
        ////similar logic to this (but cleaner):

        // if(data.isCertificateAuthority() || data.isRootCert()){

        //     KeyUsage usage = new KeyUsage(KeyUsage.keyCertSign
        //             | (data.isDigitalSignature() ? KeyUsage.digitalSignature : KeyUsage.keyCertSign )
        //             | (data.isNonRepudiation() ? KeyUsage.nonRepudiation : KeyUsage.keyCertSign  )
        //             | (data.isKeyAgreement() ? KeyUsage.keyAgreement : KeyUsage.keyCertSign )
        //             | (data.isKeyEncipherment() ? KeyUsage.keyEncipherment : KeyUsage.keyCertSign ));

        //     try {
        //         certGen.addExtension(X509Extensions.BasicConstraints, true,
        //                 new BasicConstraints(true));
        //         certGen.addExtension(Extension.keyUsage, true, usage);
        //     } catch (CertIOException e) {
        //         e.printStackTrace();
        //     }
        // } else {
        //     if(data.isDigitalSignature()){
        //         KeyUsage usage = new KeyUsage(KeyUsage.digitalSignature
        //                 | (data.isNonRepudiation() ? KeyUsage.nonRepudiation : KeyUsage.digitalSignature  )
        //                 | (data.isKeyAgreement() ? KeyUsage.keyAgreement : KeyUsage.digitalSignature )
        //                 | (data.isKeyEncipherment() ? KeyUsage.keyEncipherment : KeyUsage.digitalSignature ));

        //         try {
        //             certGen.addExtension(Extension.keyUsage, true, usage);
        //         } catch (CertIOException e) {
        //             e.printStackTrace();
        //         }
        //     }else if(data.isNonRepudiation()){
        //         KeyUsage usage = new KeyUsage(KeyUsage.nonRepudiation
        //                 | (data.isKeyAgreement() ? KeyUsage.keyAgreement : KeyUsage.nonRepudiation )
        //                 | (data.isKeyEncipherment() ? KeyUsage.keyEncipherment : KeyUsage.nonRepudiation ));

        //         try {
        //             certGen.addExtension(Extension.keyUsage, true, usage);
        //         } catch (CertIOException e) {
        //             e.printStackTrace();
        //         }
        //     }else if(data.isKeyAgreement()){
        //         KeyUsage usage = new KeyUsage(KeyUsage.keyAgreement
        //                 | (data.isKeyEncipherment() ? KeyUsage.keyEncipherment : KeyUsage.keyAgreement ));

        //         try {
        //             certGen.addExtension(Extension.keyUsage, true, usage);
        //         } catch (CertIOException e) {
        //             e.printStackTrace();
        //         }
        //     }else if(data.isKeyEncipherment()){
        //         KeyUsage usage = new KeyUsage(KeyUsage.keyEncipherment);

        //         try {
        //             certGen.addExtension(Extension.keyUsage, true, usage);
        //         } catch (CertIOException e) {
        //             e.printStackTrace();
        //         }
        //     }
        // }


        X509CertificateHolder certHolder = certGen.build(contentSigner);
        JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
        certConverter = certConverter.setProvider("BC");

        // converting holder to certificate
        X509Certificate newCertificate = certConverter.getCertificate(certHolder);

        //TODO save newly created certificate to key stores, based on whether it is a root, ca, or end user

        // if(data.isRootCert()) {
        //     _keyStoreWriterService.write(data.getEmail(), keyPair.getPrivate(), config.getRootFileName(), config.getKsPassword(), certConverter.getCertificate(certHolder));
        // }
        // else if(data.isCertificateAuthority()){
        //     _keyStoreWriterService.write(data.getEmail(), keyPair.getPrivate(), config.getCAFileName(), config.getKsPassword(), certConverter.getCertificate(certHolder));
        // }else {
        //     _keyStoreWriterService.write(data.getEmail(), keyPair.getPrivate(), config.getEnd_userFileName(), config.getKsPassword(), certConverter.getCertificate(certHolder));
        // }


        return newCertificate;

    } 
}