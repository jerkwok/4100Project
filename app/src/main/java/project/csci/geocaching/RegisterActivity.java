package project.csci.geocaching;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText passwordText = (EditText) findViewById(R.id.password_entry);
        EditText passwordConfirmText = (EditText) findViewById(R.id.password_confirm);

        passwordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordConfirmText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    public void sendCancelMessage(View view){
        finish();
    }

    public void sendRegisterMessage(View view) {
        if (validatePasswords()){
            finish();
        }
        finish();
    }

    private boolean validatePasswords() {
        EditText passwordField = (EditText) findViewById(R.id.password_entry);
        EditText passwordConfirmField = (EditText) findViewById(R.id.password_confirm);

        if (passwordField.getText().equals(passwordConfirmField.getText())){
            return true;
        }
        return false;
    }
}
