package project.csci.geocaching;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
