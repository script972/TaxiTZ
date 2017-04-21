package com.example.script972.taxitz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.script972.taxitz.model.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by script972 on 20.04.2017.
 */

public class OrderAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater layoutInflater;
    ArrayList<Order> orders;

    public OrderAdapter(Context ctx, ArrayList<Order> orders) {
        this.ctx = ctx;
        this.orders = orders;
        layoutInflater=(LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view==null){
            view=layoutInflater.inflate(R.layout.item, parent, false);
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");


        Order o=getOrder(position);

        ((TextView) view.findViewById(R.id.price)).setText(String.valueOf(o.getPrice()));
        ((TextView) view.findViewById(R.id.pointadres)).setText(o.getPoint1()+"-"+o.getPoint2());
        ((TextView) view.findViewById(R.id.pointtime)).setText(format.format(o.getDate()));
        ((TextView) view.findViewById(R.id.describe)).setText(o.getDescription());

        return view;

    }

    Order getOrder(int position){
        return (Order) getItem(position);
    }
}
