package project.csci.geocaching;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String username = getIntent().getStringExtra("username");
        TextView usernameText = (TextView) findViewById(R.id.username_textview);
        usernameText.setText(getString(R.string.welcome_message,username));
    }

    public void showCacheList(View view){
        Intent intent = new Intent(this, CacheListActivity.class);
    }

    public void showMap(View view){
        Intent i = new Intent(this, MapActivity.class);
        this.startActivity(i);
    }

    public void sendLogoutMessage(View view) {
        finish();
    }
}
