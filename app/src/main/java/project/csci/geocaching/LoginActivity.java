package project.csci.geocaching;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private static final int REGISTER_CODE = 0;
    private UserDBHelper database;
    EditText passwordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        database = new UserDBHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        passwordText = (EditText) findViewById(R.id.password_entry);

        passwordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

//        CanvasView globeCanvas = (CanvasView) findViewById(R.id.globe_canvas);
//        Integer dim = (int) (80 / (getResources().getDisplayMetrics().xdpi / 160));
//        globeCanvas.setLayoutParams(new LinearLayout.LayoutParams( dim, dim));
    }

    @Override
    protected void onResume() {
        super.onResume();
        passwordText = (EditText) findViewById(R.id.password_entry);
        passwordText.setText("");
    }

    public void sendLoginMessage(View view){
        EditText usernameEditText = (EditText) findViewById(R.id.username_entry);
        EditText passwordEditText = (EditText) findViewById(R.id.password_entry);

        if(database.validatePass(usernameEditText.getText().toString(), passwordEditText.getText().toString())){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("username", usernameEditText.getText().toString());
            intent.putExtra("userCaches", database.getUserCaches(usernameEditText.getText().toString()));
            Log.d("CACHES", Integer.toString(database.getUserCaches(usernameEditText.getText().toString())) );
            startActivity(intent);
        }else{
            //display toast
            Toast.makeText(this, getString(R.string.login_fail), Toast.LENGTH_SHORT).show();
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
                intent.putExtra("userCaches", data.getIntExtra("userCaches",0));
                startActivity(intent);
            }
        }
    }
}
