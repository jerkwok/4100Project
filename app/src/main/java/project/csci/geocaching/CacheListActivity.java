package project.csci.geocaching;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class CacheListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    ArrayList<Cache> cacheList = new ArrayList<>();
    Cache selected = null;
    String url = "http://pastebin.com/raw/5jCpxuyN";
    String userCachesBits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache_list);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        userCachesBits = Integer.toBinaryString(getIntent().getIntExtra("userCaches", 0));
        Log.d("BITS", userCachesBits);

        try{
            InputStream URLstream = OpenHttpConnection(url);
            List<String> csvLines = loadCSVLines(URLstream);

            for (int i = 0; i < csvLines.size(); i++){
                //parse csv
                //split line into sections
                String[] tokens = csvLines.get(i).split(",");

                cacheList.add(new Cache(
                        Integer.valueOf(tokens[0]), tokens[1],
                        tokens[2], Double.valueOf(tokens[3]), Double.valueOf(tokens[4])));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        if (cacheList.size()> 0){
            for( Cache cache : cacheList){
                Log.d("Cache", cache.toString());
            }
            refreshDisplay();
        }
    }

    public void backButtonClicked(View view) { finish(); }

    private List<String> loadCSVLines(InputStream inStream) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader in = new BufferedReader(new InputStreamReader(inStream));

        String line;
        while ((line = in.readLine()) != null) {
            lines.add(line);
        }

        return lines;
    }

    private InputStream OpenHttpConnection(String s) throws IOException {
        URLConnection conn;
        InputStream inputStream = null;
        URL url = new URL(s);
        conn = url.openConnection();
        HttpURLConnection httpConn = (HttpURLConnection) conn;
        httpConn.setRequestMethod("GET");
        httpConn.connect();

        if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            inputStream = httpConn.getInputStream();
        }
        return inputStream;
    }

    private void refreshDisplay() {
        ListView listview = (ListView) findViewById(R.id.cache_list);
        CacheListAdapter cacheAdapter = new CacheListAdapter(this,android.R.layout.simple_list_item_1,
                cacheList, userCachesBits);

        listview.setAdapter(cacheAdapter);
        listview.setOnItemClickListener(CacheListActivity.this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("CLICKED", cacheList.get(position).toString());
        Log.d("USER CACHES", userCachesBits);
        Log.d("POSITION", Integer.toString(position));
        Log.d("CHILDREN", Integer.toString(parent.getChildCount()));
        Log.d("VISIBLE", Integer.toString(parent.getFirstVisiblePosition()));

        ImageView statusImage;

        for(int a = 0; a < parent.getChildCount(); a++) {
            statusImage = (ImageView) parent.getChildAt(a).findViewById(R.id.status_image);
            statusImage.setImageResource(0);
        }

        statusImage = (ImageView) parent.getChildAt(position - parent.getFirstVisiblePosition()).findViewById(R.id.status_image);
        statusImage.setImageResource(R.drawable.magnifying);
        selected = cacheList.get(position);

        for(int a = 0; a < parent.getChildCount(); a++)
        {
            if (((a + parent.getFirstVisiblePosition()) < userCachesBits.length()) &&
                    (userCachesBits.substring(userCachesBits.length() - (a + parent.getFirstVisiblePosition()) - 1).charAt(0) == '1')
                    ){
                if (a == position){
                    selected = null;
                }
                Log.d("a", Integer.toString(a));
                statusImage = (ImageView) parent.getChildAt(a).findViewById(R.id.status_image);
                statusImage.setImageResource(R.drawable.star);
            }
        }
    }

    public void trackClicked(View view) {
        Intent output = new Intent();

        if (selected != null){
            output.putExtra("cacheID", selected.getCacheID());
            output.putExtra("cacheName", selected.getName());
            output.putExtra("cacheLat", selected.getLat());
            output.putExtra("cacheLong", selected.getLong());
            output.putExtra("cacheDesc", selected.getDescription());
            setResult(RESULT_OK,output);
        }else{
            setResult(RESULT_CANCELED, output);
        }
        finish();
    }
}