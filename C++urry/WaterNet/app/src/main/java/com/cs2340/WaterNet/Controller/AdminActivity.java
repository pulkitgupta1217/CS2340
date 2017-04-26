package com.cs2340.WaterNet.Controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cs2340.WaterNet.Facade.Facade;
import com.cs2340.WaterNet.Model.User;
import com.cs2340.WaterNet.R;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.security_layout);
        Facade.updateSecurityLogView(linearLayout, getApplicationContext());

        TextView banUserButton =  (TextView) findViewById(R.id.ban_user_button);
        TextView unBanUserButton =  (TextView) findViewById(R.id.unban_user_button);

        banUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
                View mView = layoutInflaterAndroid.inflate(R.layout.user_input_dialog_box, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getApplicationContext());
                alertDialogBuilderUserInput.setView(mView);

                final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Ban", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                String userToBeBanned = userInputDialogEditText.getText().toString();
<<<<<<< HEAD
                                //get user object here
                                final User u = null;
=======
                                //get user object here TODO
                                final User u = new User("hello", "hello@water.net");
>>>>>>> c870da5c7ff06fbd1b98c84ec4bb6ff97ab7fb15
                                new AlertDialog.Builder(getApplicationContext())
                                        .setMessage("Are you sure you want to ban " + userToBeBanned + "?")
                                        .setCancelable(false)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                u.setBanned(true);
                                            }
                                        })
                                        .setNegativeButton("No", null)
                                        .show();

                            }
                        })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();

            }
        });


        unBanUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
                View mView = layoutInflaterAndroid.inflate(R.layout.user_input_dialog_box, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getApplicationContext());
                alertDialogBuilderUserInput.setView(mView);

                final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Unban", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                String userToBeBanned = userInputDialogEditText.getText().toString();
                                //get user object here TODO
                                final User u = new User("hello", "hello@water.net");
                                new AlertDialog.Builder(getApplicationContext())
                                        .setMessage("Are you sure you want to unban " + userToBeBanned + "?")
                                        .setCancelable(false)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                u.setBanned(false);
                                            }
                                        })
                                        .setNegativeButton("No", null)
                                        .show();

                            }
                        })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();

            }
        });


    }
}
