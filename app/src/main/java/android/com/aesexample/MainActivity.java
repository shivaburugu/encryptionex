package android.com.aesexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

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
    RSA512 rsa512 = new RSA512();

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

        String textToBeEncrypted = "{\n" +
                "  \"ssid\": \"MV Office\",\n" +
                "  \"psk\": \"i8some\"\n" +
                "}";
        //Step 1
        try {
            randomKey = AES256Cipher.getRandomAesCryptKey();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //Step 2
        randomIV = AES256Cipher.getRandomAesCryptIv();

        //Step 3 replace need to replace this with the key coming from server
        String rsaKey = privateKeyBase64;
        String encryptedSymmetricKey = rsa512.encrypt(new String(randomKey));

        //Step 4
        String encryptedSymmetricIV = rsa512.encrypt(new String(randomIV));

        //Step 5
        String convertedbase64payload = "";
        try {
            String encryptedWifiCredentials = AES256Cipher.encrypt(randomKey,randomIV,textToBeEncrypted);
            convertedbase64payload = new String(Base64.encode(encryptedWifiCredentials.getBytes(), Base64.DEFAULT));
            Log.i(TAG, "onCreate: convertedbase64payload "+convertedbase64payload);
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

        String finalPayload = "";
        try {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("payload",convertedbase64payload);
        jsonObject.put("symmetrickey",encryptedSymmetricKey);
        jsonObject.put("iv",encryptedSymmetricIV);
        finalPayload = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "onCreate: Final Payload "+finalPayload);
        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //try {
                    //String aesencryptedtext = AES256Cipher.encrypt(randomKey,randomIV,textToEncrypt.getText().toString());
                    //aesencrypted.setText(aesencryptedtext);
                    String rsaencryptedtext = rsa512.encrypt(textToEncrypt.getText().toString());
                    rsaencrypted.setText(rsaencryptedtext);
                /*} catch (UnsupportedEncodingException e) {
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
                }*/
            }
        });

        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //try {
                    String rsadecryptedtext = rsa512.encrypt(rsaencrypted.getText().toString());
                    rsadecrypted.setText(rsadecryptedtext);
                    Log.i(TAG, "onClick: rsadecrypted:: "+rsadecryptedtext);
                    //String aesdecryptedtext = AES256Cipher.decrypt(randomKey,randomIV,rsadecryptedtext);
                    //aesdecrypted.setText(aesdecryptedtext);
                    //Log.i(TAG, "onClick: aesdecrypted:: "+aesdecryptedtext);

                /*} catch (UnsupportedEncodingException e) {
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
                }*/
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
