package android.com.aesexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    public final String ZIGB_3_INPUT_MASK
            = "[____]{ }[____]{ }[____]{ }[____]{ }[____]{ }[____]{ }[____]{ }[____]{ }[____]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editText = findViewById(R.id.installation_code_input);
        final String[] actualText = new String[1];
        final String[] formattedText = new String[1];
        final boolean[] edited = {false};
        formattedText[0] = "";
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Log.i(TAG, "afterTextChanged:editText.getSelectionEnd() "+editText.getSelectionEnd());
                //Log.i(TAG, "afterTextChanged:editText.getSelectionStart() "+editText.getSelectionStart());
                //Log.i(TAG, "afterTextChanged: "+editable.toString());
                if(edited[0]){
                    //Log.i(TAG, "afterTextChanged: returning since override");
                    editText.setSelection(formattedText[0].length());
                    edited[0] = false;
                    return;
                }
                if(formattedText[0].length() > editText.getMaxEms()){
                    return;
                }
                actualText[0] = editable.toString();
                if ((actualText[0].length() -1) > 0 && (actualText[0].length() + noOfSpaces(formattedText[0])) % 4 == 0) {
                    formattedText[0] += actualText[0].charAt(actualText[0].length() - 1) + " ";
                    edited[0] = true;
                    Log.i(TAG, "afterTextChanged:formattedText[0] "+formattedText[0]);
                    editText.setText(formattedText[0]);
                } else if(editable.toString().length() < editText.getMaxEms()){
                    formattedText[0] += actualText[0].charAt(actualText[0].length() - 1);
                }
            }
        });


    }
    private int noOfSpaces(String text){
        int counter = text.split(" ", -1).length - 1;
        Log.i(TAG, "noOfSpaces: "+counter);
        return counter;
    }
}
