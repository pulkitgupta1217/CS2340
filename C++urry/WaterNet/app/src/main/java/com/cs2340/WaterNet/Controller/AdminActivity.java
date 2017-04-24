package com.cs2340.WaterNet.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.cs2340.WaterNet.Facade.Facade;
import com.cs2340.WaterNet.R;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.security_layout);
        Facade.updateSecurityLogView(linearLayout, getApplicationContext());

    }
}
