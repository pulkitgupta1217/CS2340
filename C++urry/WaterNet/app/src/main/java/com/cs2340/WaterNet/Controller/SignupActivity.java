package com.cs2340.WaterNet.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.cs2340.WaterNet.Model.Admin;
import com.cs2340.WaterNet.Model.Manager;
import com.cs2340.WaterNet.Model.SecurityLogger;
import com.cs2340.WaterNet.Model.Singleton;
import com.cs2340.WaterNet.Model.User;
import com.cs2340.WaterNet.Model.UserType;
import com.cs2340.WaterNet.Model.Worker;
import com.cs2340.WaterNet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputUsername;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private Spinner spinner;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Singleton.setInstance(Firebase.getSingleton());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        //Get FirebaseDatabase
        database = FirebaseDatabase.getInstance();

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputUsername = (EditText) findViewById(R.id.username);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, UserType.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email;
                String tempEmail = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();
                final String username = inputUsername.getText().toString().trim();
                final UserType userType = (UserType) (spinner.getSelectedItem());

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "Enter username!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(tempEmail)) {
                    email = username + getString(R.string.email_extension);
                } else {
                    email = tempEmail;
                }

                Log.d("***", email);

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                //CODE ADDED BY PULKIT FOR SINGLETON
                                if(task.isSuccessful()) {
                                    //edit this
                                    database.getReference().addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.child("Singleton").getValue(Singleton.class) == null) {
                                                database.getReference().child("Singleton").setValue(Singleton.getInstance());
                                                Log.d("***", "adding new Singleton");
                                            } else {
                                                Singleton.setInstance(dataSnapshot.child("Singleton").getValue(Singleton.class));
                                                Log.d("***", "found Singleton during signup");
                                            }
                                            finish();
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }

                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    User u = null;
                                    if (userType == UserType.USER) {
                                        u = new User(username, email);
                                        database.getReference().child("users").child(auth.getCurrentUser()
                                                .getUid()).setValue(u);
                                    } else if (userType == UserType.MANAGER) {
                                        u = new Manager(username, email);
                                        database.getReference().child("users").child(auth.getCurrentUser()
                                                .getUid()).setValue(u);
                                    } else if (userType == UserType.WORKER) {
                                        u = new Worker(username, email);
                                        database.getReference().child("users").child(auth.getCurrentUser()
                                                .getUid()).setValue(u);
                                    } if (userType == UserType.ADMIN) {
                                        u = new Admin(username, email);
                                        database.getReference().child("users").child(auth.getCurrentUser()
                                                .getUid()).setValue(u);
                                    }
                                    //fixed endless loop
                                    database.getInstance().getReference().child("Singleton").setValue(Singleton.getInstance());//edit
                                    SecurityLogger.writeNewSecurityLog(Singleton.getInstance().getTime() + " :: " + email + " Registered on firebase");
                                    Intent i = new Intent(SignupActivity.this, MainActivity.class);
                                    i.putExtra("user", u);
                                    startActivity(i);
                                    finish();
                                }
                            }
                        });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}