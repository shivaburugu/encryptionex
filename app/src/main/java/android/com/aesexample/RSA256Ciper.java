package android.com.aesexample;

import android.util.Base64;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.MGF1ParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

public class RSA256Ciper {

    KeyPair key;

    RSA256Ciper() {
        KeyPairGenerator keyGen = null;
        try {
            keyGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyGen.initialize(512);
        key = keyGen.generateKeyPair();
    }

    public String encryptRSAToString(String clearText, String publicKey) {
        String encryptedBase64 = "";
        try {


            // get an RSA cipher object and print the provider
            //final Cipher cipher = Cipher.getInstance("AES/CBC/OAEPWITHSHA-256ANDMGF1PADDING");
            OAEPParameterSpec sp = new OAEPParameterSpec("SHA-512", "MGF1", new MGF1ParameterSpec("SHA-1"), PSource.PSpecified.DEFAULT);
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-512AndMGF1Padding");            // encrypt the plain text using the public key
            cipher.init(Cipher.ENCRYPT_MODE, key.getPublic(), sp);

            byte[] encryptedBytes = cipher.doFinal(clearText.getBytes("UTF-8"));
            encryptedBase64 = new String(Base64.encode(encryptedBytes, Base64.DEFAULT));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return encryptedBase64.replaceAll("(\\r|\\n)", "");
    }

    public String decryptRSAToString(String encryptedBase64, String privateKey) {

        String decryptedString = "";
        try {


            // get an RSA cipher object and print the provider
            //final Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");
            OAEPParameterSpec sp = new OAEPParameterSpec("SHA-512", "MGF1", new MGF1ParameterSpec("SHA-1"), PSource.PSpecified.DEFAULT);
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-512AndMGF1Padding");            // encrypt the plain text using the public key
            // encrypt the plain text using the public key
            cipher.init(Cipher.DECRYPT_MODE, key.getPrivate(), sp);

            byte[] encryptedBytes = Base64.decode(encryptedBase64, Base64.DEFAULT);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            decryptedString = new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return decryptedString;
    }
}
