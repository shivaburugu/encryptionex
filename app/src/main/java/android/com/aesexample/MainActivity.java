package android.com.aesexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class MainActivity extends AppCompatActivity {

    Button encrypt,decrypt;
    TextView aesencrypted,rsaencrypted,aesdecrypted,rsadecrypted;
    EditText textToEncrypt;
    byte[] randomIV;
    byte[] randomKey;
    PublicKey publicKey;
    PrivateKey privateKey;
    String publicKeyBase64,privateKeyBase64;
    private String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        encrypt = findViewById(R.id.encrypt);
        decrypt = findViewById(R.id.decrypt);
        aesencrypted = findViewById(R.id.aes_encrypted);
        aesdecrypted = findViewById(R.id.aes_decrypted);
        rsaencrypted = findViewById(R.id.rsa_encrypted);
        rsadecrypted = findViewById(R.id.rsa_decrypted);

        KeyPair kp = getKeyPair();

        publicKey = kp.getPublic();
        byte[] publicKeyBytes = publicKey.getEncoded();
        publicKeyBase64 = new String(Base64.encode(publicKeyBytes, Base64.DEFAULT));

        privateKey = kp.getPrivate();
        byte[] privateKeyBytes = privateKey.getEncoded();
        privateKeyBase64 = new String(Base64.encode(privateKeyBytes, Base64.DEFAULT));


        textToEncrypt = findViewById(R.id.editText);
        randomIV = AES256Cipher.getRandomAesCryptIv();
        try {
            randomKey = AES256Cipher.getRandomAesCryptKey();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String aesencryptedtext = AES256Cipher.encrypt(randomKey,randomIV,textToEncrypt.getText().toString());
                    aesencrypted.setText(aesencryptedtext);
                    String rsaencryptedtext = RSA256Ciper.encryptRSAToString(aesencryptedtext,publicKeyBase64);
                    rsaencrypted.setText(rsaencryptedtext);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                }
            }
        });

        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String rsadecryptedtext = RSA256Ciper.decryptRSAToString(rsadecrypted.getText().toString(),privateKeyBase64);
                    rsadecrypted.setText(rsadecryptedtext);
                    String aesdecryptedtext = AES256Cipher.decrypt(randomKey,randomIV,rsadecryptedtext);
                    aesencrypted.setText(aesdecryptedtext);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static KeyPair getKeyPair() {
        KeyPair kp = null;
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            kp = kpg.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return kp;
    }
}
