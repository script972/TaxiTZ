package com.example.script972.taxitz.activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.script972.taxitz.OrderAdapter;
import com.example.script972.taxitz.R;
import com.example.script972.taxitz.model.Order;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TabAir extends Activity implements LocationListener {
    ArrayList<Order> orders = new ArrayList<Order>();
    OrderAdapter orderAdapter;

    TextView responceView;

    private double x = 0, y = 0;
    String [] access={"31","123456"};
    UpdateTask updateTask;
    Thread t;
    int wait=3; //second
    int count=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_air);

        responceView= (TextView) findViewById(R.id.responce);

        fillData();
        orderAdapter = new OrderAdapter(this, orders);

        ListView lvMain=(ListView) findViewById(R.id.lvAir);
        lvMain.setAdapter(orderAdapter);









        /*GET*/
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


        /*GPS*/
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


    }

    private void fillData() {
        for (int i = 0; i < 20; i++) {
            orders.add(new Order("Адрес "+i, "Адрес "+i+1, "Описание "+i*23, 30+i*21, new Date()));
        }
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
            responceView.setText(responceView.getText()+String.valueOf(count)+" "+response);
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
}
