package com.cs2340.WaterNet.Factory;

import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cs2340.WaterNet.Controller.ViewUsersActivity;
import com.cs2340.WaterNet.Facade.Consumer;
import com.cs2340.WaterNet.Model.User;
import com.cs2340.WaterNet.R;

import java.util.LinkedList;
import java.util.Map;

/**
 * Created by pulki on 4/24/2017.
 */

class ViewUsersConsumer implements Consumer<Map<String, User>> {
    private Intent intent;
    private Context context;
    ViewUsersConsumer(Intent i, Context c) {
        intent = i;
        context = c;
    }

    public void accept(Map<String, User> map) {
        if (map == null) {
            Toast.makeText(context, "no users found", Toast.LENGTH_SHORT).show();
        } else {
            ((ListView) ((ViewUsersActivity) context).findViewById(R.id.user_list))
                    .setAdapter(new ArrayAdapter<>(context,
                    R.layout.user_entry, new LinkedList<>(map.values())));
        }
    }
}
