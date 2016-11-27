package project.csci.geocaching;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int MAIN_CODE = 1;
    private static final int CACHE_LIST_CODE = 2;
    private static final int TRACKING_CODE = 3;
    private Cache trackingCache = new Cache();
    int userCaches;
    String username;
    UserDBHelper database = new UserDBHelper(this);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = getIntent().getStringExtra("username");
        userCaches = getIntent().getIntExtra("userCaches", 0);
        TextView usernameText = (TextView) findViewById(R.id.username_textview);
        usernameText.setText(getString(R.string.welcome_message1,username));
        TextView welcomeText = (TextView) findViewById(R.id.welcome_textview);
        welcomeText.setText(getString(R.string.welcome_message2));
    }

    public void showCacheList(View view){
        Intent intent = new Intent(this, CacheListActivity.class);
        intent.putExtra("userCaches", userCaches);
        intent.putExtra("username", getIntent().getStringExtra("username"));
        this.startActivityForResult(intent, CACHE_LIST_CODE);
    }

    public void showMap(View view){
        Intent i = new Intent(this, MapActivity.class);
        if (trackingCache.getCacheID() != -1){
            i.putExtra("cacheSelected", true);
            i.putExtra("cacheID", trackingCache.getCacheID());
            i.putExtra("cacheName", trackingCache.getName());
            i.putExtra("cacheLat", trackingCache.getLat());
            i.putExtra("cacheLong", trackingCache.getLong());
        } else {
            i.putExtra("cacheSelected", false);
        }
        i.putExtra("userCaches", userCaches);
        i.putExtra("username", getIntent().getStringExtra("username"));
        this.startActivity(i);
    }

    public void sendLogoutMessage(View view) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("USERNAME", username);
        if( (requestCode == CACHE_LIST_CODE) && (resultCode == RESULT_OK)){
            //save the tracked cache to a file
            trackingCache.setCacheID(data.getIntExtra("cacheID", 0));
            trackingCache.setName(data.getStringExtra("cacheName"));
            trackingCache.setLat(data.getDoubleExtra("cacheLat", 0));
            trackingCache.setLong(data.getDoubleExtra("cacheLong", 0));
            trackingCache.setDescription(data.getStringExtra("cacheDesc"));

            TextView trackingInfo = (TextView) findViewById(R.id.tracking_textview);
            trackingInfo.setText(getString(R.string.tracking_information,
                    trackingCache.getCacheID(),
                    trackingCache.getName(),
                    trackingCache.getLat(),
                    trackingCache.getLong()));

        }else if ((requestCode == TRACKING_CODE) && (resultCode == RESULT_OK)){
            //cache successfully claimed

            //set new cache value for user
            int newCacheValue = 0;
            String oldCaches = Integer.toBinaryString(database.getUserCaches(getIntent().getStringExtra("username")));
            Log.d("OLD CACHES", oldCaches);
            Log.d("CACHE ID", Integer.toString(trackingCache.getCacheID()));
            if ((trackingCache.getCacheID() >= oldCaches.length()) ||
                    (oldCaches.substring(oldCaches.length() - trackingCache.getCacheID() - 1).charAt(0) == '0')
                    ){
                        double power = Math.pow(2, trackingCache.getCacheID());
                        int intPower = (int) power;
                    newCacheValue = database.getUserCaches(getIntent().getStringExtra("username"))
                        +  intPower;

                database.updateUserCache(getIntent().getStringExtra("username"),newCacheValue );
                userCaches = newCacheValue;
            }
            Log.d("NEW CACHE VALUE", Integer.toString(newCacheValue));
            //write to database

            if (data.getBooleanExtra("claimed",false)) {
                TextView trackingInfo = (TextView) findViewById(R.id.tracking_textview);
                trackingInfo.setText(R.string.claimed_cache);
            }
        }
    }

    public void showTracking(View view) {
        Intent i = new Intent(this, TrackingActivity.class);
        if (trackingCache.getCacheID() != -1){
            i.putExtra("cacheSelected", true);
            i.putExtra("cacheID", trackingCache.getCacheID());
            i.putExtra("cacheName", trackingCache.getName());
            i.putExtra("cacheLat", trackingCache.getLat());
            i.putExtra("cacheLong", trackingCache.getLong());
        } else {
            i.putExtra("cacheSelected", false);
        }
        i.putExtra("userCaches", userCaches);
        i.putExtra("username", getIntent().getStringExtra("username"));
        this.startActivityForResult(i, TRACKING_CODE);
    }
}
