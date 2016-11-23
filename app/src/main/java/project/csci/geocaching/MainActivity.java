package project.csci.geocaching;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private static final int MAIN_CODE = 1;
    private static final int CACHE_LIST_CODE = 2;
    private Cache trackingCache = new Cache();

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
        this.startActivityForResult(intent, CACHE_LIST_CODE);
    }

    public void showMap(View view){
        Intent i = new Intent(this, MapActivity.class);
        this.startActivity(i);
    }

    public void sendLogoutMessage(View view) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if( (requestCode == CACHE_LIST_CODE) && (resultCode == RESULT_OK)){
            //save the tracked cache to a file
            trackingCache.setCacheID(data.getIntExtra("cacheID", 0));
            trackingCache.setName(data.getStringExtra("cacheName"));
            trackingCache.setLat(data.getDoubleExtra("cacheLat", 0));
            trackingCache.setLongitude(data.getDoubleExtra("cacheLong", 0));
            trackingCache.setDescription(data.getStringExtra("cacheDesc"));

            TextView trackingInfo = (TextView) findViewById(R.id.tracking_textview);
            trackingInfo.setText(getString(R.string.tracking_information,
                    trackingCache.getCacheID(),
                    trackingCache.getName(),
                    trackingCache.getLat(),
                    trackingCache.getLongitude()));
        }
    }
}
