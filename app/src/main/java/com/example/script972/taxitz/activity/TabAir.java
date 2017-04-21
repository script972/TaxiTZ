package com.example.script972.taxitz.activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.ListView;

import com.example.script972.taxitz.OrderAdapter;
import com.example.script972.taxitz.R;
import com.example.script972.taxitz.SpliterLine;
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
    SpliterLine spliterLine;
    String[] access = {"31", "123456"};
    UpdateTask updateTask;
    Thread t;
    int wait = 3; //second
    int count = 0;
    ListView lvMain;
    private double x = 0, y = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_air);


        lvMain = (ListView) findViewById(R.id.lvAir);


        orderAdapter = new OrderAdapter(this, orders);
        lvMain.setAdapter(orderAdapter);



        /*GET*/
        t = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateTask = new UpdateTask();
                            updateTask.execute();
                        }
                    });

                    try {
                        Thread.sleep(wait * 1000);
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

  /*  private void fillData() {
        for (int i = 0; i < 20; i++) {
            orders.add(new Order("Адрес "+i, "Адрес "+i+1, "Описание "+i*23, 30+i*21, new Date()));
        }
    }*/

    @Override
    public void onLocationChanged(Location location) {
        x = location.getLongitude();
        y = location.getLatitude();

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
        List<Order> list = new ArrayList<>();
        private String response;
/*!! inlude apach.http !!*/

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
           /* responceView.setText("end");*/

            // responceView.setText(responceView.getText()+String.valueOf(count)+" "+response);
            for (Order or :
                    list) {
                Log.i("sok", or.toString());

            }
            count++;
            //fillData();
            ArrayList<Order> temp = new ArrayList<Order>();

            for (int i = 0; i < list.size(); i++) {
                //orders.add(new Order("Адрес "+i, "Адрес "+i+1, "Описание "+i*23, 30+i*21, new Date()));
                if (i == 0)
                    continue;
                temp.add(new Order(list.get(i).getPoint1(), "", list.get(i).getDescription(), list.get(i).getPrice(), new Date()));
            }


            orders.addAll(temp);
            orderAdapter.notifyDataSetChanged();


            //orders= (ArrayList<Order>) list;


            //

        }

        @Override
        protected Void doInBackground(Void... params) {

            DefaultHttpClient hc = new DefaultHttpClient();
            ResponseHandler responseHandler = new BasicResponseHandler();


            Log.i("sok", "ADDRES REQUEST:" + "http://89.184.67.115/taxi/index.php?id_car=" + access[0] + "&pass=" + access[1] + "&get_order=1&x=" + x + "&y=" + y);
            HttpGet httpGet = new HttpGet("http://89.184.67.115/taxi/index.php?id_car=" + access[0] + "&pass=" + access[1] + "&get_order=1&x=" + x + "&y=" + y);
            try {
                response = (String) hc.execute(httpGet, responseHandler);
                spliterLine = new SpliterLine(response);
                list = spliterLine.splitStringByObjectAir();
                Log.d("sok", response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
