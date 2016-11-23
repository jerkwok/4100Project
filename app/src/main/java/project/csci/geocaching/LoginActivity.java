package project.csci.geocaching;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        String hashedPass = BCrypt.hashpw("halloworld", BCrypt.gensalt());
//        String hashedPass2 = BCrypt.hashpw("halloworld", BCrypt.gensalt());
//        String hashedPass3 = BCrypt.hashpw("halloworld", BCrypt.gensalt());
//
//        Log.d("One", hashedPass);
//        Log.d("One", hashedPass2);
//        Log.d("One", hashedPass3);
//
//        if(BCrypt.checkpw("halloworld",hashedPass)){
//            Log.d("One", "true");
//        }else{
//            Log.d("One", "false");
//        }
//
//        if(BCrypt.checkpw("halloworld",hashedPass2)){
//            Log.d("One", "true");
//        }else{
//            Log.d("One", "false");
//        }
//
//        if(BCrypt.checkpw("halloworld",hashedPass3)){
//            Log.d("One", "true");
//        }else{
//            Log.d("One", "false");
//        }

    }

    public void sendLoginMessage(View view){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void sendRegisterMessage(View view){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
