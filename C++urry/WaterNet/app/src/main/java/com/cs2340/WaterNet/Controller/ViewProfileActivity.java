package com.cs2340.WaterNet.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cs2340.WaterNet.Model.SecurityLogger;
import com.cs2340.WaterNet.Model.Singleton;
import com.cs2340.WaterNet.Model.User;
import com.cs2340.WaterNet.Model.UserType;
import com.cs2340.WaterNet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

public class ViewProfileActivity extends AppCompatActivity {

    private Button edit, save, cancel, back;

    private EditText nameField, emailField, addressField, phoneField;
    private TextView nameView, emailView, addressView, phoneView, typeView;
    private Spinner typeSpinner;
    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
        final User user = (User) (getIntent().getSerializableExtra("user"));
        Log.d("***", user.toString());

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser fuser = firebaseAuth.getCurrentUser();
                if (fuser == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    Intent i = new Intent(ViewProfileActivity.this, LoginActivity.class);
                    i.putExtra("user", user);
                    startActivity(i);
                    finish();
                }
            }
        };

        edit = (Button) findViewById(R.id.edit_profile_button);
        cancel = (Button) findViewById(R.id.cancel_changes_button);
        save = (Button) findViewById(R.id.save_changes_button);
        back = (Button) findViewById(R.id.back_button);

        nameField = (EditText) findViewById(R.id.new_name);
        emailField = (EditText) findViewById(R.id.new_user_email);
        addressField = (EditText) findViewById(R.id.new_address);
        phoneField = (EditText) findViewById(R.id.new_phone);

        phoneView = (TextView) findViewById(R.id.profile_number_view);
        nameView = (TextView) findViewById(R.id.profile_name_view);
        addressView = (TextView) findViewById(R.id.profile_address_view);
        emailView = (TextView) findViewById(R.id.profile_email_view);
        typeView = (TextView) findViewById(R.id.type_view);

        typeSpinner = (Spinner) findViewById(R.id.profileSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, UserType.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        switchToViews(user);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ViewProfileActivity.this, MainActivity.class);
                i.putExtra("user", user);
                startActivity(i);
                finish();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToFields(user);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToViews(user);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user.setAddress(addressField.getText().toString());
                user.setName(nameField.getText().toString());
                if (emailField.getText().toString().indexOf("@") > 0) {
                    user.setEmail(emailField.getText().toString());
                    fireUser.updateEmail(user.getEmail())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("***", "User email address updated.");
                                    }
                                }
                            });
                }
                user.setPhone(phoneField.getText().toString());
                user.setUserType((UserType) (typeSpinner.getSelectedItem()));
                FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser()
                        .getUid()).setValue(user); //***
                SecurityLogger.writeNewSecurityLog(Singleton.getInstance().getTime() + " :: " + user.getEmail() + " edited their profile");
                switchToViews(user);

            }
        });

//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                progressBar.setVisibility(View.VISIBLE);
//                if (fireUser != null && !newEmail.getText().toString().trim().equals("")) {
//                    fireUser.updateEmail(newEmail.getText().toString().trim())
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        Toast.makeText(MainActivity.this, "Email address is updated. Please sign in with new email id!", Toast.LENGTH_LONG).show();
//                                        signOut();
//                                        progressBar.setVisibility(View.GONE);
//                                    } else {
//                                        Toast.makeText(MainActivity.this, "Failed to update email!", Toast.LENGTH_LONG).show();
//                                        progressBar.setVisibility(View.GONE);
//                                    }
//                                }
//                            });
//                } else if (newEmail.getText().toString().trim().equals("")) {
//                    newEmail.setError("Enter email");
//                    progressBar.setVisibility(View.GONE);
//                }
//            }
//        });

    }

    private void switchToFields(User user) {
        nameField.setVisibility(View.VISIBLE);
        emailField.setVisibility(View.VISIBLE);
        addressField.setVisibility(View.VISIBLE);
        phoneField.setVisibility(View.VISIBLE);
        typeSpinner.setVisibility(View.VISIBLE);

        nameView.setVisibility(View.INVISIBLE);
        emailView.setVisibility(View.INVISIBLE);
        addressView.setVisibility(View.INVISIBLE);
        phoneView.setVisibility(View.INVISIBLE);
        typeView.setVisibility(View.INVISIBLE);

        nameField.setText(user.getName());
        emailField.setText(user.getEmail());
        addressField.setText(user.getAddress());
        phoneField.setText(user.getPhone());
        typeSpinner.setSelection(user.getUserType().ordinal());

        cancel.setVisibility(View.VISIBLE);
        save.setVisibility(View.VISIBLE);
        back.setVisibility(View.INVISIBLE);
        edit.setVisibility(View.INVISIBLE);

    }

    private void switchToViews(User user) {

        nameField.setVisibility(View.INVISIBLE);
        emailField.setVisibility(View.INVISIBLE);
        addressField.setVisibility(View.INVISIBLE);
        phoneField.setVisibility(View.INVISIBLE);
        typeSpinner.setVisibility(View.INVISIBLE);

        cancel.setVisibility(View.INVISIBLE);
        save.setVisibility(View.INVISIBLE);
        edit.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);

        nameView.setText(user.getName() + "     ID: " + user.getUserID());
        emailView.setText(user.getEmail());
        addressView.setText(user.getAddress());
        phoneView.setText(user.getPhone());
        typeView.setText(user.getUserType().toString());

        nameView.setVisibility(View.VISIBLE);
        emailView.setVisibility(View.VISIBLE);
        addressView.setVisibility(View.VISIBLE);
        phoneView.setVisibility(View.VISIBLE);
        typeView.setVisibility(View.VISIBLE);

    }

    //sign out method
    public void signOut() {
        auth.signOut();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}