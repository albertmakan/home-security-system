package com.backend.myhouse.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class ECCDecrypt {

    public byte[] decrypt(byte[] cipherText, PrivateKey key, byte[] IV) throws Exception {
        try {

            Cipher eccCipherEnc = Cipher.getInstance("ECIESwithAES");

            eccCipherEnc.init(Cipher.DECRYPT_MODE, key);

            return eccCipherEnc.doFinal(cipherText);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalStateException
                | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
