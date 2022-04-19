package com.backend.admin;

import java.util.Calendar;
import java.util.Date;

import com.backend.admin.model.CertificateSigningRequest;
import com.backend.admin.dto.RevokeCertificateDTO;
import com.backend.admin.model.enums.CertificateStatus;
import com.backend.admin.service.CertificateService;

import com.backend.admin.service.EmailService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdminApplicationTests {

    @Autowired
    private CertificateService cs;
    @Autowired
    private EmailService emailService;

    @Test
    void contextLoads() throws Exception {

        //// Testing read cert from ks method
        // X509Certificate issuerCert = ksr.readCertificate("rootKeyStore.jks", "admin",
        //// "adagradinterm");
        // System.out.println(issuerCert.getSerialNumber());

        //// Testing read issuer private key method
        // PrivateKey issuerPrivateKey = ksr.readPrivateKey("rootKeyStore.jks", "admin",
        //// "adagradinterm", "admin");

        // Testing generate cert (NO EXTENSIONS FOR NOW)
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, 5);
        CertificateSigningRequest req = new CertificateSigningRequest("1", "commonName", "firstName", "lastName",
                "emaillll", "", "", "RS", "organisation", "organisationUnit", c.getTime(), null, null, false, 0, true);
        cs.generateCertificate(req);

    }

    @Test
    public void revocationTest() throws Exception {
        cs.revokeCertificate(new RevokeCertificateDTO("4935827174799823196", new ObjectId(), "had to"));
    }

    @Test
    public void checkStatusTest() throws Exception {
        Assertions.assertEquals(CertificateStatus.REVOKED, cs.checkStatus("4935827174799823196"));
    }

    @Test
    public void mailTest() throws Exception {
        cs.loadCertificateToFile("6684749470397079359");
        emailService.sendMailWithAttachment("makanalbert@gmail.com", "cer proba", "certificate",
                "certificates/6684749470397079359.cer");
    }

}
