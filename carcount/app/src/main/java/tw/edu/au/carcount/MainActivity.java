package tw.edu.au.carcount;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements LocationListener{
    TextView jingdu;
    TextView weidu;
    TextView distance;
    LocationManager locationManager;
    Double endlongitude=null;
    Double endlatitude=null;
    boolean ck=false;
    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jingdu = (TextView)findViewById(R.id.jingdu);
        weidu = (TextView)findViewById(R.id.weidu);
        distance = (TextView)findViewById(R.id.distance);
        jingdu.setText("0000");
        weidu.setText("0000");
        distance.setText("0000");
        locationManager=(LocationManager)getSystemService(getApplicationContext().LOCATION_SERVICE);

        Log.i("map", "oncreat");

    }


    @Override
    protected void onResume() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000,10, this);
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location != null) {
            Double longitude = location.getLongitude();   //取得經度
            jingdu.setText(String.valueOf(longitude));
            Double latitude = location.getLatitude();     //取得緯度
            weidu.setText(String.valueOf(latitude));
            if((longitude != endlongitude || latitude != endlatitude ) &&ck){
                //float[] results=new float[1];
                //Location.distanceBetween(longitude, latitude, endlongitude, endlatitude, results);
                //distance (longitude, latitude, endlongitude, endlatitude);
                distance.setText(String.valueOf(distance(latitude, longitude, endlatitude, endlongitude)));
                //distance.setText(String.valueOf(endlatitude)+" "+String.valueOf(endlongitude));

            }

            endlatitude = latitude;
            endlongitude = longitude ;
        }
        else {
            Toast.makeText(this, "無法定位座標", Toast.LENGTH_LONG).show();
        }
        ck=true;
    }
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "請開啟gps或3G網路", Toast.LENGTH_LONG).show();
    }
}
