package com.cs2340.WaterNet.Factory;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.cs2340.WaterNet.Facade.Consumer;

/**
 * Created by pulki on 4/23/2017.
 */

public class ConsumerFactory {
    private ConsumerFactory(){}

    public static Consumer getConsumer(Intent i, Context c) {
        if (i == null || c == null) {
            throw new IllegalArgumentException("invalid intent or context");
        }
        String start = c.getClass().getName();
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (start.contains("LoginActivity")) {
            return new LoginConsumer(i, c);
        } else if (start.contains("PReportActivity")) {
            return new PReportConsumer(i, c);
        }
        throw new IllegalArgumentException("invalid intent or context");
    }
}
