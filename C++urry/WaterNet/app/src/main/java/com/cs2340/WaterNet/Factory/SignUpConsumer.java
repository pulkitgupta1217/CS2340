package com.cs2340.WaterNet.Factory;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.cs2340.WaterNet.Controller.MainActivity;
import com.cs2340.WaterNet.Controller.SignUpActivity;
import com.cs2340.WaterNet.Facade.AuthTuple;
import com.cs2340.WaterNet.R;

/**
 * Created by pulki on 4/24/2017.
 */

class SignUpConsumer implements Consumer<AuthTuple> {
    private Intent intent;
    private Context context;
    SignUpConsumer(Intent i, Context c) {
        intent = i;
        context = c;
    }
    public void accept(AuthTuple tuple) {
        ((SignUpActivity) context).findViewById(R.id.progressBar).setVisibility(View.GONE);
        if (tuple.getErrorMessage().length() != 0) {
            Toast.makeText(context, tuple.getErrorMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        if (tuple.getSuccess()) {
            Intent i = new Intent(context, MainActivity.class);
            context.startActivity(i);
            ((SignUpActivity) context).finish();
        }
    }
}
