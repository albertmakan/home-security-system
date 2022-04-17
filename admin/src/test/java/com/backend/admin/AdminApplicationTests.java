package com.backend.admin;
import java.util.Calendar;
import java.util.Date;

import com.backend.admin.model.CertificateSigningRequest;
import com.backend.admin.service.CertificateService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdminApplicationTests {

    @Autowired
    private CertificateService cs;

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
                "emaillll", "", "", "RS", "organisation", "organisationUnit", c.getTime(), null);
        cs.generateCertificate(req);

    }

}
