package android.com.aesexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class MainActivity extends AppCompatActivity {


    byte[] randomIV;
    byte[] randomKey;
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String publicKey = "MIIEowIBAAKCAQEAtIcZYmflBfE6fRQAoGYsnUuFSNRZjjbCUUWgxkcBJeHepzFZ94Xk9+Y2Ed/xkO3ouUbvxFVZLWGzAjku0bCxoUAWKOKv53mp2SjhjQj5UYbTHknJ4/68Asyp25PszsVH8VnRJoPfrVjUNNArETpd6f8CxtpFgBFf66TUqT7LFPwEz4Xb0JPol68ap5ibP946Hwz2pTH5dIfmr/sW4XA8BIc16/Zto5Bn+hQOF6q/HJX8nOyyhnUUBg/AiOykc9fcc4jdj31Uvkbft+4S4DwoOCtDmhxAdgJMIbLsJv04qVeu1dhSRFS5sE443Qprd62Bn1lXlD5IsxfWvmSPNlEwxQIDAQABAoIBAByINbTd4s6bn7jhDnBLdcWuh/RIy5a0hOW/cXelBUNtfXlDTYrXeKbNlcIheNaFI4EU+xQ2wQ9xH4+8Ze/yU0C/FkGEAnU9P2D5GxzlTDJccsWYt/X27tQqcLYpnQXZHFPC9FF4CkQlg/SM6iGg7fLrwQ6YtN9W36gotwvAxuwb3J/2LZEy83Wse5WIlRpJuBsjoerZTPsGuF3Oufe+E8ZkUD7vgTauwVA44uRm6FVDThm7G4rq56nYK7RsBi0iGI0GzTnYaaBSnlUNgZMgfeJw81BAZJWCap2szxtB1QX/1pSz/MsmN3zBmM6ojmpt9IhhhoaLsXRCrzPeKSvFI4kCgYEA6VduMMIHLj7T3TOj7kAiBGuttx1X0R5xGEV5PMVE13BIPukPr2JMxeFvIgPRiefttH4uSwukl5Y3xYXH0HN62RqjGSOcnAPgpaEMzmLFtb2XrSIqHyOjvu59ncBXBclaiOTOW2QWX1GF0RicGHGfOZ3V8/zPgy6yF0pHGWkZn9sCgYEAxg7JLzMdiu2LYP41XUuwfCrNZwN/NblNo76cWiwvEAqhKtU7289HSofoOAUsLux/d8t5HupRfCvU4Ka3OeX19WFREwyVv638eqdRzhax/F3WOdsPnpSuIaMiAlNycCw9GMeaKk4NjKqVDtXx7k9Axn1vDPzbw/xpOKSr5JOsI98CgYBEo3FIamrOUbDkqqIYHUlkX4BUEw+X9gpyh00ocSbU8LRvvQLeJl5K/ws/yDvFAzJLG6rmbGBYLtntAnNKgA2s+0bqyCzzP7i/P+aSdz+NPF33kcsuKb+rFGBjHQb8LsSw7A0/mOtz0nk4jrKsBhA+VI1xHfa0sfG1JCZR9TaSbQKBgBABQeC+kLSZXZoI8OcJJsnh65dXgQAmaU/CJ4Bh8auqMDv131Bz/57WPL3KLTfdhK95keAdr1gJcrp0vnm/S5D4vER9z9wdgzmmK8Yrr1ZlftpOCYUXHrs5I5Zsh+LosEdAWgTxKcckQcdhIpvPa7yehkrWZCwCSb56ECn4hQChAoGBAIQHg1ZSVHCdfFHihwPGRuu10zviagBJp6wQveGVhiwMGBoiqW4LNGYV8294rxLlhOYaHhXqiyBobou1ge3lflcpu0S4zdCGwN1MgsTTc4PMp7AQHwSwTrXp2o+AULhRaUpmaCj4SmAfRX8+ov9ZIgiYlDL+C3TxPXItYjLYsisF";

        String textToBeEncrypted = "{\n" +
                "  \"ssid\": \"MV Office\",\n" +
                "  \"psk\": \"i8some\"\n" +
                "}";
        //Step 1
        try {
            randomKey = AES256CipherUtil.getRandomAESCryptKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //Step 2
        randomIV = AES256CipherUtil.getRandomAesCryptIv();

        //Step 3 replace need to replace this with the key coming from server
        String encryptedSymmetricKey = null;
        try {
            encryptedSymmetricKey = RSA512CipherUtil.encryptText(new String(randomKey), RSA512CipherUtil.convertKeyStringtoPublicKey(publicKey));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        //Step 4
        String encryptedSymmetricIV = null;
        try {
            encryptedSymmetricIV = RSA512CipherUtil.encryptText(new String(randomIV), RSA512CipherUtil.convertKeyStringtoPublicKey(publicKey));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        //Step 5
        String convertedbase64payload = "";
        try {
            String encryptedWifiCredentials = AES256CipherUtil.encrypt(randomKey, randomIV, textToBeEncrypted);
            convertedbase64payload = new String(Base64.encode(encryptedWifiCredentials.getBytes(), Base64.DEFAULT));
            Log.i(TAG, "onCreate: convertedbase64payload " + convertedbase64payload);
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
            jsonObject.put("payload", convertedbase64payload);
            jsonObject.put("symmetrickey", encryptedSymmetricKey);
            jsonObject.put("iv", encryptedSymmetricIV);
            finalPayload = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "onCreate: Final payload " + finalPayload);
    }
}
