package com.backend.admin.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class ECCKeyGenerator {
    public static final int ECC_KEY_SIZE = 224;
    public static final int GCM_IV_LENGTH = 12;
    public static final int GCM_TAG_LENGTH = 16;

    public ECCKeyGenerator() {
        Security.addProvider(new BouncyCastleProvider());
    }

    public KeyPair generateKeys() throws InvalidAlgorithmParameterException {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC", "BC");
            keyGen.initialize(new ECGenParameterSpec("brainpoolP384r1"));
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

}
