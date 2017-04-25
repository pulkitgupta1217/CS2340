package com.cs2340.WaterNet.Command;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.cs2340.WaterNet.Controller.MainActivity;
import com.cs2340.WaterNet.Controller.ReportActivity;

/**
 * Created by pulki on 4/24/2017.
 */

public class ReportCommand implements Command<String> {
    Intent intent;
    Context context;
    public ReportCommand(Intent i, Context c) {
        intent = i;
        context = c;
    }

    public void accept(String s) {
        if (s.equals("success!")) {
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(context, MainActivity.class);
            context.startActivity(i);
            ((ReportActivity) context).finish();
        } else {
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        }
    }
}
