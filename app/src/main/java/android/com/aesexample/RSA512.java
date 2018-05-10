package android.com.aesexample;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSA512 {
    KeyPair keyPair;

    static {
        Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
    }

    public RSA512() {
        KeyPairGenerator keyGen = null;
        try {
            try {
                keyGen = KeyPairGenerator.getInstance("RSA", "SC");
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyGen.initialize(4096);
        keyPair = keyGen.generateKeyPair();
    }

    public String encrypt(String data) {
        //OAEPParameterSpec sp = new OAEPParameterSpec("SHA-512", "MGF1", new MGF1ParameterSpec("SHA-1"), PSource.PSpecified.DEFAULT);
        Cipher cipher = null;            // encrypt the plain text using the public key
        try {
            cipher = Cipher.getInstance("RSA/ECB/OAEPwithSHA512andMGF1Padding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        try {
            cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
            try {
                byte[] encryptedBytes = new byte[0];
                try {
                    encryptedBytes = cipher.doFinal(data.getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String encryptedBase64 = new String(Base64.encode(encryptedBytes, Base64.DEFAULT));

                Log.i("Encrypted", "encrypt: " + new String(encryptedBase64));
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            }

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } /*catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }*/
        return null;
    }



    public String decrypt(String data) {

        Cipher cipher = null;            // encrypt the plain text using the public key
        try {
            cipher = Cipher.getInstance("RSA/ECB/OAEPwithSHA512andMGF1Padding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        try {
            cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
            try {
                byte[] encryptedBytes = cipher.doFinal(data.getBytes());
                Log.i("Encrypted", "decrypt: " + new String(encryptedBytes));
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            }

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } /*catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }*/
        return null;
    }

}
