package com.backend.admin.service;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Service
public class CertificateKeyStoreService {
    private KeyStore keyStore;
    private String keyStorePath;
    private char[] password;
    private char[] keyPassword;

    public CertificateKeyStoreService() {
        try {
            Security.addProvider(new BouncyCastleProvider());
            password = "admin".toCharArray();
            keyPassword = password;
            keyStorePath = "rootKeyStore.jks";
            keyStore = KeyStore.getInstance("JKS", "SUN");
            keyStore.load(new BufferedInputStream(new FileInputStream(keyStorePath)), password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(String alias, X509Certificate[] certificateChain, PrivateKey privateKey) {
        try {
            keyStore.setKeyEntry(alias, privateKey, password, certificateChain);
            keyStore.store(new FileOutputStream(keyStorePath), password);
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        }
    }

    public X509Certificate readCertificate(String alias) {
        try {
            return (X509Certificate) keyStore.getCertificate(alias);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PrivateKey readPrivateKey(String alias) {
        try {
            return (PrivateKey) keyStore.getKey(alias, keyPassword);
        } catch (KeyStoreException | UnrecoverableKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
