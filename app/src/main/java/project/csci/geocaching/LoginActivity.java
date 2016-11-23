package project.csci.geocaching;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private static final int REGISTER_CODE = 0;
    private UserDBHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        database = new UserDBHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void sendLoginMessage(View view){
        EditText usernameEditText = (EditText) findViewById(R.id.username_entry);
        EditText passwordEditText = (EditText) findViewById(R.id.password_entry);

        if(database.validatePass(usernameEditText.getText().toString(), passwordEditText.getText().toString())){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("username", usernameEditText.getText().toString());
            startActivity(intent);
        }
    }

    public void sendRegisterMessage(View view){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivityForResult(intent, REGISTER_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if(requestCode == REGISTER_CODE){
            if(resultCode == RESULT_OK){
                //login
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("username", data.getStringExtra("username"));
                startActivity(intent);
            }
        }
    }
}
