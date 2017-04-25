package com.cs2340.WaterNet.Command;

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

public class PasswordResetCommand implements Command<String> {
    Intent intent;
    Context context;
    public PasswordResetCommand(Intent i, Context c) {
        intent = i;
        context = c;
    }

    public void accept(String s) {
        ((ResetPasswordActivity) context).findViewById(R.id.progressBar).setVisibility(View.GONE);
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
