package com.example.script972.taxitz;

import android.os.AsyncTask;
import android.util.Log;

import com.example.script972.taxitz.model.Order;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by script972 on 22.04.2017.
 */

public class SeltRequests {
    private Requesting request;
    private String serverResponce=null;



    public boolean isAccess(String login, String password){
        Log.i("access","enter in isAccess"+serverResponce);

        request=new Requesting();
        request.execute(login, password);


        Log.i("access","afterASYNCTASK"+serverResponce);



        return true;

        }




    class Requesting extends AsyncTask<String, Void, Void> {
        List<Order> list = new ArrayList<>();
        private String response;
/*!! inlude apach.http !!*/

        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
            serverResponce=response;
            Log.i("access", "PROBLEM>>> "+serverResponce);
            return;
        }

        @Override
        protected Void doInBackground(String... params) {
            //String[]access=params[0].split("|");


            DefaultHttpClient hc = new DefaultHttpClient();
            ResponseHandler responseHandler = new BasicResponseHandler();


            Log.i("sok", "ADDRES REQUEST:" + "http://89.184.67.115/taxi/index.php?id_car=" + params[0] + "&pass=" + params[1] + "&get_order=1&x=" + 0 + "&y=" + 0);
            HttpGet httpGet = new HttpGet("http://89.184.67.115/taxi/index.php?id_car=" + params[0] + "&pass=" + params[1] + "&get_order=1&x=" + 0 + "&y=" + 0);
            try {
                response = (String) hc.execute(httpGet, responseHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
           return null;
        }
    }

}










