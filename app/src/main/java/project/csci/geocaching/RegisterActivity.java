package project.csci.geocaching;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    UserDBHelper database = new UserDBHelper(this);
    EditText usernameText = (EditText) findViewById(R.id.username_entry);
    EditText passwordText = (EditText) findViewById(R.id.password_entry);
    EditText passwordConfirmText = (EditText) findViewById(R.id.password_confirm);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        passwordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordConfirmText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    public void sendCancelMessage(View view){
        setResult(RESULT_CANCELED);
        finish();
    }

    public void sendRegisterMessage(View view) {
        if (validatePasswords() && !(database.checkUserDupes(usernameText.getText().toString())) ){
            database.addEntry(usernameText.getText().toString(), passwordText.getText().toString());
            setResult(RESULT_OK);
            finish();
        }
        //display toast, passwords don't match
        Toast.makeText(this, getString(R.string.password_dont_match), Toast.LENGTH_SHORT).show();
    }

    private boolean validatePasswords() {

        if (passwordText.getText().equals(passwordConfirmText.getText())){
            return true;
        }
        return false;
    }
}
