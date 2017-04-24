package com.cs2340.WaterNet.Factory;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.cs2340.WaterNet.Controller.ResetPasswordActivity;
import com.cs2340.WaterNet.R;

/**
 *
 * Created by pulki on 4/24/2017.
 */

class PasswordResetConsumer implements Consumer<String> {
    Intent intent;
    Context context;
    public PasswordResetConsumer(Intent i, Context c) {
        intent = i;
        context = c;
    }

    public void accept(String s) {
        ((ResetPasswordActivity) context).findViewById(R.id.progressBar).setVisibility(View.GONE);
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
