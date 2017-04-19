package com.example.script972.taxitz;

import android.app.TabActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;


public class MainActivity extends TabActivity {
    final String TABS_TAG_1 = "Tag 1";
    final String TABS_TAG_2 = "Tag 2";
    final String TABS_TAG_3 = "Tag 3";


    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

        tabSpec = tabHost.newTabSpec(TABS_TAG_3);
        tabSpec.setContent(new Intent(this, MyOrder.class));
        view=getLayoutInflater().inflate(R.layout.tab_my_order_header, null);
        tabSpec.setIndicator(view);
        tabHost.addTab(tabSpec);


        tabHost.setCurrentTabByTag(TABS_TAG_1);

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
