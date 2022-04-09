package com.backend.admin.service;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;

import org.springframework.stereotype.Service;

@Service
public class SignatureService {
    
    public KeyPair generateKeys() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(2048, random);
            return keyGen.generateKeyPair();

        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] sign(byte[] data, PrivateKey privateKey) {
        try {
            // Kreiranje objekta koji nudi funkcionalnost digitalnog potpisivanja
            // Prilikom getInstance poziva prosledjujemo algoritam koji cemo koristiti
            // U ovom slucaju cemo generisati SHA-1 hes kod koji cemo potpisati upotrebom RSA asimetricne sifre
            Signature sig = Signature.getInstance("SHA1withRSA");

            // Navodimo kljuc kojim potpisujemo
            sig.initSign(privateKey);

            // Postavljamo podatke koje potpisujemo
            sig.update(data);

            // Vrsimo potpisivanje
            return sig.sign();
        } catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean verify(byte[] data, byte[] signature, PublicKey publicKey) {
        try {
            // Kreiranje objekta koji nudi funkcionalnost digitalnog potpisivanja
            // Prilikom getInstance poziva prosledjujemo algoritam koji cemo koristiti
            // U ovom slucaju cemo generisati SHA-1 hes kod koji cemo potpisati upotrebom RSA asimetricne sifre
            Signature sig = Signature.getInstance("SHA1withRSA");

            // Navodimo kljuc sa kojim proveravamo potpis
            sig.initVerify(publicKey);

            // Postavljamo podatke koje potpisujemo
            sig.update(data);

            // Vrsimo proveru digitalnog potpisa
            return sig.verify(signature);
        } catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException e) {
            e.printStackTrace();
        }
        return false;
    }
}
