package project.csci.geocaching;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    UserDBHelper database = new UserDBHelper(this);
    EditText usernameText;
    EditText passwordText;
    EditText passwordConfirmText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameText = (EditText) findViewById(R.id.username_entry);
        passwordText = (EditText) findViewById(R.id.password_entry);
        passwordConfirmText = (EditText) findViewById(R.id.password_confirm);

        passwordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordConfirmText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        // Set button click highlights.
        ButtonHelper buttonHelper = new ButtonHelper();
        buttonHelper.buttonClickSetter(this, findViewById(R.id.reg_registerButton));
        buttonHelper.buttonClickSetter(this, findViewById(R.id.reg_cancelButton));
    }

    public void sendCancelMessage(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void sendRegisterMessage(View view) {
        if (validatePasswords()) {
            Log.d("Password:", "TRUE");
        } else {
            Log.d("Password:", "FALSE");
        }

        if (!(database.checkUserDupes(usernameText.getText().toString()))) {
            Log.d("Dupe:", "TRUE");
        } else {
            Log.d("Dupe:", "FALSE");
        }

        if(usernameText.getText().toString().compareTo("") == 0 || passwordText.getText().toString().compareTo("") == 0 ||
                passwordConfirmText.getText().toString().compareTo("") == 0){
            Toast.makeText(this, getString(R.string.no_empty_text), Toast.LENGTH_SHORT).show();
        }else if (validatePasswords() && !(database.checkUserDupes(usernameText.getText().toString())) ) {
            database.addEntry(usernameText.getText().toString(), passwordText.getText().toString());
            Intent output = new Intent();
            output.putExtra("username",usernameText.getText().toString() );
            setResult(RESULT_OK,output);
            finish();
        } else if (database.checkUserDupes(usernameText.getText().toString())) {
            Toast.makeText(this, getString(R.string.duplicate_user), Toast.LENGTH_SHORT).show();
        } else {
            //display toast, passwords don't match
            Toast.makeText(this, getString(R.string.password_dont_match), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validatePasswords() {
        Log.d("password", passwordText.getText().toString());
        Log.d("password2", passwordConfirmText.getText().toString());

        if (passwordText.getText().toString().equals(passwordConfirmText.getText().toString())){
            return true;
        }
        return false;
    }
}