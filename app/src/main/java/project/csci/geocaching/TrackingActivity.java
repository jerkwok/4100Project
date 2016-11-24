package project.csci.geocaching;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TrackingActivity extends AppCompatActivity {

    private Cache trackingCache = new Cache();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        trackingCache.setCacheID(getIntent().getIntExtra("cacheID", 0));
        trackingCache.setName(getIntent().getStringExtra("cacheName"));
        trackingCache.setLat(getIntent().getDoubleExtra("cacheLat", 0));
        trackingCache.setLong(getIntent().getDoubleExtra("cacheLong", 0));
        trackingCache.setDescription(getIntent().getStringExtra("cacheDesc"));

        TextView trackingInfo = (TextView) findViewById(R.id.tracking_textview);
        trackingInfo.setText(getString(R.string.tracking_information,
                trackingCache.getCacheID(),
                trackingCache.getName(),
                trackingCache.getLat(),
                trackingCache.getLong()));
    }
}
