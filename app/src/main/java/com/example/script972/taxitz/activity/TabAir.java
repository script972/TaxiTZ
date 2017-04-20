package com.example.script972.taxitz.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.script972.taxitz.OrderAdapter;
import com.example.script972.taxitz.R;
import com.example.script972.taxitz.model.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TabAir extends Activity {
    ArrayList<Order> orders = new ArrayList<Order>();
    OrderAdapter orderAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_air);


        fillData();
        orderAdapter = new OrderAdapter(this, orders);

        ListView lvMain=(ListView) findViewById(R.id.lvAir);
        lvMain.setAdapter(orderAdapter);

    }

    private void fillData() {
        for (int i = 0; i < 20; i++) {
            orders.add(new Order("Адрес "+i, "Адрес "+i+1, "Описание "+i*23, 30+i*21, new Date()));
        }
    }
}
