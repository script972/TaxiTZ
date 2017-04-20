package com.example.script972.taxitz.activity;

import android.Manifest;
import android.app.TabActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.script972.taxitz.R;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;


public class MainActivity extends TabActivity  implements LocationListener{
    final String TABS_TAG_1 = "Tag 1";
    final String TABS_TAG_2 = "Tag 2";


    private double x = 0, y = 0;
    String [] access={"31","123456"};
    UpdateTask updateTask;
    Thread t;
    int wait=3; //second
    int count=0;
    TextView responceView;





    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView responceView=(TextView) findViewById(R.id.responce);




        t=new Thread(){
            @Override
            public void run() {
                while (!isInterrupted()){
                    try {
                        Thread.sleep(wait*1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateTask=new UpdateTask();
                                updateTask.execute();
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };


        t.start();


        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * wait, 0, this);







        TabHost tabHost = getTabHost();
        TabHost.TabSpec tabSpec;

        tabSpec = tabHost.newTabSpec(TABS_TAG_1);
        tabSpec.setContent(new Intent(this, TabAir.class));
        View view=getLayoutInflater().inflate(R.layout.tab_air_header, null);
        tabSpec.setIndicator(view);
        tabHost.addTab(tabSpec);



        tabSpec = tabHost.newTabSpec(TABS_TAG_2);
        tabSpec.setContent(new Intent(this, TabPreviously.class));
        view=getLayoutInflater().inflate(R.layout.tab_previously_header, null);
        tabSpec.setIndicator(view);
        tabHost.addTab(tabSpec);



        tabHost.setCurrentTabByTag(TABS_TAG_1);

    }

    @Override
    public void onLocationChanged(Location location) {
        x=location.getLongitude();
        y=location.getLatitude();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {


    }


    class UpdateTask extends AsyncTask<Void, Void, Void> {
        private String response;
/*!! inlude apach.http !!*/

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            responceView.setText("end");
            responceView.setText(responceView.getText()+" "+response);
            count++;
        }

        @Override
        protected Void doInBackground(Void... params) {

            DefaultHttpClient hc = new DefaultHttpClient();
            ResponseHandler responseHandler=new BasicResponseHandler();

            HttpGet httpGet=new HttpGet("http://89.184.67.115/taxi/index.php?id_car="+access[0]+"&pass="+access[1]+"&get_order=1&x="+x+"&y="+y);
            try {
                response=(String)hc.execute(httpGet, responseHandler);
                Log.d("log", response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }



/*    *//*Proccess with tab*//*
    TabHost.TabContentFactory tabFactory = new TabHost.TabContentFactory() {

        @Override
        public View createTabContent(String tag) {
            if (tag.equals(TABS_TAG_1)) {
                return getLayoutInflater().inflate(R.layout.tab_air, null);
            } else if (tag.equals(TABS_TAG_2)) {
                return getLayoutInflater().inflate(R.layout.tab_previously, null);
            }
            return null;
        }
    };*/
}
