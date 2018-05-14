package android.com.aesexample;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSA512CipherUtil {

    static {
        Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 1);
    }

    public static String encryptText(String text, PublicKey publicKey) throws UnsupportedEncodingException,
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            BadPaddingException,
            IllegalBlockSizeException {
        byte[] data = text.getBytes("UTF-8");
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPwithSHA512andMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.encodeToString(cipher.doFinal(data), Base64.DEFAULT);
    }

    public static String decryptText(String text, PrivateKey privateKey) throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            BadPaddingException,
            IllegalBlockSizeException,
            UnsupportedEncodingException {
        byte[] data = Base64.decode(text, Base64.DEFAULT);
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPwithSHA512andMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(data), "UTF-8");
    }

    public static PublicKey convertKeyStringtoPublicKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PublicKey publicKey = null;
        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(key, Base64.DEFAULT));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        publicKey = kf.generatePublic(keySpecPKCS8);
        return publicKey;
    }
}
