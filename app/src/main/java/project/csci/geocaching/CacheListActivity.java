package project.csci.geocaching;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CacheListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache_list);
    }

    public void backButtonClicked(View view){
        finish();
    }
}
