package com.example.script972.taxitz.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.script972.taxitz.OrderAdapter;
import com.example.script972.taxitz.R;
import com.example.script972.taxitz.model.Order;

import java.util.ArrayList;
import java.util.Date;

public class TabPreviously extends Activity {
    ArrayList<Order> orders = new ArrayList<Order>();
    OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_previously);


        //fillData();
        orderAdapter = new OrderAdapter(this, orders);

        ListView lvMain = (ListView) findViewById(R.id.lvPreviously);
        lvMain.setAdapter(orderAdapter);

    }

    private void fillData() {
        for (int i = 0; i < 20; i++) {
            orders.add(new Order("Адрес ", "Адрес ", "Описание " + i * 23, 30 + i * 21, new Date()));
        }
    }
}
