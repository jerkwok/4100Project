package project.csci.geocaching;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class CacheListActivity extends AppCompatActivity {

    ArrayList<Cache> cacheList = new ArrayList<>();
    String url = "http://pastebin.com/raw/25LhSH2a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache_list);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try{
            InputStream URLstream = OpenHttpConnection(url);
            List<String> csvLines = loadCSVLines(URLstream);
            for (int i = 0; i < csvLines.size(); i++){
                //parse csv
                //split line into sections
                String[] tokens = csvLines.get(i).split(",");

                //add to the list of housing projects a new housing project using only the data we want.
                cacheList.add(new Cache(
                        Integer.valueOf(tokens[0]), tokens[1],
                        tokens[2], Double.valueOf(tokens[3]), Double.valueOf(tokens[4])));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void backButtonClicked(View view){

        finish();
    }

    private List<String> loadCSVLines(InputStream inStream) throws IOException {
        ArrayList<String> lines = new ArrayList<>();

        BufferedReader in = new BufferedReader(new InputStreamReader(inStream));

        String line = null;
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

    public void showClicked(View view) {
        for( Cache cache : cacheList){
            Log.d("Cache", cache.toString());
        }
    }
}
