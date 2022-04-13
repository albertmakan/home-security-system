package com.backend.admin.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import org.springframework.stereotype.Service;

@Service
public class KeyStoreWriterService {
    
    private KeyStore keyStore;

    public KeyStoreWriterService() {
        try {
            keyStore = KeyStore.getInstance("JKS", "SUN");
        } catch (KeyStoreException | NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    //load or create new keystore
    public void loadKeyStore(String fileName, char[] password) {
        try {
            if(fileName != null) {
                keyStore.load(new FileInputStream(fileName), password);
            } else {
                //Ako je cilj kreirati novi KeyStore poziva se i dalje load, pri cemu je prvi parametar null
                keyStore.load(null, password);
            }
        } catch (NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        }
    }

    // saving changes to keystore
    public void saveKeyStore(String fileName, char[] password) {
        try {
            keyStore.store(new FileOutputStream(fileName), password);
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        }
    }


    // saving certificate to keystore
    public void write(String alias, PrivateKey privateKey, String fileName, String password, Certificate[] certChain) {
        try {
            this.loadKeyStore(fileName,password.toCharArray());
            if(!keyStore.containsAlias(alias)) {
                keyStore.setKeyEntry(alias, privateKey, password.toCharArray(), certChain);
                this.saveKeyStore(fileName,password.toCharArray());
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }



}
