package com.cs2340.WaterNet.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.cs2340.WaterNet.Facade.Facade;
import com.cs2340.WaterNet.Factory.ConsumerFactory;
import com.cs2340.WaterNet.R;


public class ViewUsersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ListView userListView = (ListView) findViewById(R.id.user_list);
        //Todo: need filler intent here
        Facade.getUserList(ConsumerFactory.getConsumer(new Intent(ViewUsersActivity.this, ViewUsersActivity.class), ViewUsersActivity.this));
        /*new Consumer<Map<String, User>>() {
            @Override
            public void accept(Map<String, User> map) {
                if (map == null) {
                    Toast.makeText(ViewUsersActivity.this, "no users found", Toast.LENGTH_SHORT).show();
                } else {
                    userListView.setAdapter(new ArrayAdapter<>(ViewUsersActivity.this,
                            R.layout.user_entry, new LinkedList<>(map.values())));
                }
            }
        });*/



    }

}
