package com.backend.admin;


import java.security.cert.CertificateException;
import java.util.Date;

import com.backend.admin.model.CertSigningRequestDummy;
import com.backend.admin.service.CertificateService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdminApplicationTests {

    @Autowired
    private CertificateService cs;

	@Test
	void contextLoads() throws CertificateException {


        ////Testing read cert from ks method
        // X509Certificate issuerCert = ksr.readCertificate("rootKeyStore.jks", "admin", "adagradinterm");
        // System.out.println(issuerCert.getSerialNumber());

        ////Testing read issuer private key method
        // PrivateKey issuerPrivateKey = ksr.readPrivateKey("rootKeyStore.jks", "admin", "adagradinterm", "admin");

        ////Testing generate sert
        // CertSigningRequestDummy req = new CertSigningRequestDummy("firstName", "lastName", "email", "RS", "organisation", "organisationUnit", new Date(2032, 4, 2), false, false, false, false, false, false);
        // cs.generateCertificate(req);


        

	}

}
