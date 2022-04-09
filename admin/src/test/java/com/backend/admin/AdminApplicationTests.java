package com.backend.admin;


import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import com.backend.admin.service.KeyStoreReaderService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdminApplicationTests {

	@Test
	void contextLoads() {
        KeyStoreReaderService ksr = new KeyStoreReaderService(); 
        X509Certificate issuerCert = ksr.readCertificate("rootKeyStore.jks", "admin", "adagradinterm");
        System.out.println(issuerCert.getSerialNumber());
        PrivateKey issuerPrivateKey = ksr.readPrivateKey("rootKeyStore.jks", "admin", "adagradinterm", "admin");
        

	}

}
