package com.cs2340.WaterNet.Command;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cs2340.WaterNet.Controller.LoginActivity;
import com.cs2340.WaterNet.Facade.AuthTuple;
import com.cs2340.WaterNet.R;

/**
 * Created by pulki on 4/23/2017.
 */

public class LoginCommand implements Command<AuthTuple> {
    Intent intent;
    Context context;
    public LoginCommand(Intent i, Context c) {
        intent = i;
        context = c;
    }

    @Override
    public void accept(AuthTuple tuple) {

        ProgressBar pb = (ProgressBar) ((LoginActivity) context).findViewById(R.id.progressBar);
        pb.setVisibility(View.GONE);
        String error = tuple.getErrorMessage();
        Log.d("Error", error);
        if (error.length() != 0) {
            Toast.makeText(context, error, Toast.LENGTH_SHORT)
                    .show();
        }
        if (tuple.getSuccess()) {
            Log.d("starting", "main activity");
            context.startActivity(intent);
            ((LoginActivity) context).finish();
        }
    }
}
