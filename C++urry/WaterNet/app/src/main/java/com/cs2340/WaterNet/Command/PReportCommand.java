package com.cs2340.WaterNet.Command;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.cs2340.WaterNet.Controller.PReportActivity;

/**
 * Created by pulki on 4/23/2017.
 */

public class PReportCommand implements Command<String> {
    private Intent intent;
    private Context context;
    public PReportCommand(Intent i, Context c) {
        intent = i;
        context = c;
    }

    public void accept(String s) {
        if (s.equals("success!")) {
            Toast.makeText(context, s, Toast.LENGTH_SHORT)
                    .show();
            context.startActivity(intent);
            ((PReportActivity) context).finish();
        } else {
            Toast.makeText(context, s, Toast.LENGTH_SHORT)
                    .show();
        }
    }
}