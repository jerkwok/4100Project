package project.csci.geocaching;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TrackingActivity extends AppCompatActivity implements LocationListener, SensorEventListener {

    private static final int EARTH_RADIUS = 6373;

    private Cache trackingCache = new Cache();
    float[] mGravity;
    float[] mGeomagnetic;
    Float azimut = 0f;
    private SensorManager mSensorManager;
    Sensor accelerometer;
    Sensor magnetometer;
    TextView bearingText;
    ImageView arrowView;
    double bearing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        trackingCache.setCacheID(getIntent().getIntExtra("cacheID", 0));
        trackingCache.setName(getIntent().getStringExtra("cacheName"));
        trackingCache.setLat(getIntent().getDoubleExtra("cacheLat", 0));
        trackingCache.setLong(getIntent().getDoubleExtra("cacheLong", 0));
        trackingCache.setDescription(getIntent().getStringExtra("cacheDesc"));

        if (getIntent().getBooleanExtra("cacheSelected", false)){
            TextView trackingInfo = (TextView) findViewById(R.id.tracking_textview);
            trackingInfo.setText(getString(R.string.tracking_information,
                    trackingCache.getName(),
                    trackingCache.getLat(),
                    trackingCache.getLong()));
        }

        arrowView = (ImageView) findViewById(R.id.arrow);
        bearingText = (TextView) findViewById(R.id.bearing_title);
        setupLocationServices();
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

    }
    private void setupLocationServices() {
        requestLocationPermissions();

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // request that the user install the GPS provider
            String locationConfig = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
            Intent enableGPS = new Intent(locationConfig);
            startActivity(enableGPS);
        } else {
            // determine the location
            updateLocation();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @SuppressLint("NewApi")
    private void updateLocation() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

            // request an fine location provider
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setSpeedRequired(false);
            criteria.setCostAllowed(false);
            String recommended = locationManager.getBestProvider(criteria, true);

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);

            Location location = locationManager.getLastKnownLocation(recommended);
            if (location != null) {
                showLocationName(location);
            }
        } else {
            Log.d("LocationSample", "Location provider permission denied, perms: " + ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION));
        }
    }

    final int PERMISSIONS_REQUEST_ACCESS_LOCATION = 410020;
    @SuppressLint("NewApi")
    private void requestLocationPermissions() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Explain to the user why we need to read the contacts
            }

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_LOCATION);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateLocation();
                }
            }
        }
    }

    public void onProviderEnabled(String provider) {
        Log.d("LocationSample", "onProviderEnabled(" + provider + ")");
    }

    public void onProviderDisabled(String provider) {
        Log.d("LocationSample", "onProviderDisabled(" + provider + ")");
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("LocationSample", "onStatusChanged(" + provider + ", " + status + ", extras)");
    }

    public void onLocationChanged(Location location) {
        Log.d("LocationSample", "onLocationChanged(" + location + ")");

        showLocationName(location);
    }

    private void showLocationName(Location location) {
        Log.d("LocationSample", "showLocationName("+location+")");
        setLocation(location.getLatitude(), location.getLongitude());
    }

    private void setLocation(double currLat, double currLong) {

        double currRadLat = Math.toRadians(currLat);
        double currRadLong = Math.toRadians(currLong);
        double targetRadLat = Math.toRadians(trackingCache.getLat());
        double targetRadLong = Math.toRadians(trackingCache.getLong());

        double dlat = targetRadLat - currRadLat;
        double dlong = targetRadLong - currRadLong;

        double a = Math.pow((Math.sin(dlat/2)),2) +
                   Math.cos(currRadLat) *
                   Math.cos(targetRadLat) *
                   Math.pow((Math.sin(dlong/2)),2);

        double c = 2 * (Math.atan2(Math.sqrt(a),Math.sqrt(1-a)));
        double distance = EARTH_RADIUS * c;

        TextView trackingText = (TextView) findViewById(R.id.tracking_textview);
        TextView distanceText = (TextView) findViewById(R.id.distance_text);

        if (getIntent().getBooleanExtra("cacheSelected", false)) {
            distanceText.setText(getString(R.string.distance_information,distance));
        }else{
            trackingText.setText(getString(R.string.no_active_cache));
            distanceText.setText(R.string.no_distance);
        }

        if (getIntent().getBooleanExtra("cacheSelected", false)){
            trackingText.setText(getString(R.string.tracking_information,
                    trackingCache.getName(),
                    trackingCache.getLat(),
                    trackingCache.getLong()));
        }

        bearing = getBearing(currLong, currLat,trackingCache.getLong(),trackingCache.getLat() );
        bearing = (bearing / Math.PI) * 180;

        Button claimButton = (Button)findViewById(R.id.claim_button);

        if (distance <= 0.5) {
            claimButton.setEnabled(true);
            distanceText.setTextColor(Color.GREEN);
        } else {
            claimButton.setEnabled(false);
            distanceText.setTextColor(Color.GRAY);
        }
    }

    public void claimClicked(View view) {
        Intent output = new Intent();
        output.putExtra("claimed", true);
        setResult(RESULT_OK,output);
        finish();
    }

    public void backClicked(View view) {
        Intent output = new Intent();
        output.putExtra("claimed", false);
        setResult(RESULT_CANCELED,output);
        finish();
    }

    public double getBearing(double currLong, double currLat, double endLong, double endLat) {

        double y = Math.abs(currLong - endLong);
        double x =Math.log( Math.tan(endLat/2.0 + Math.PI/4.0) /
                Math.tan(currLat/2.0 + Math.PI/4.0));
        return (Math.atan2(y, x));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values;
        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                azimut = ((float)Math.toDegrees(orientation[0])+360)%360;

                if (bearing < azimut){
                    arrowView.setRotation(Float.valueOf(String.valueOf(360 - (azimut - bearing))));
                }else{

                    arrowView.setRotation(Float.valueOf(String.valueOf(bearing-azimut)));
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}