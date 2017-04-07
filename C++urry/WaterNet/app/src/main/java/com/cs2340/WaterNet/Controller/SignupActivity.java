package com.cs2340.WaterNet.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.cs2340.WaterNet.Facade.AuthTuple;
import com.cs2340.WaterNet.Model.Consumer;
import com.cs2340.WaterNet.Facade.Facade;
import com.cs2340.WaterNet.Model.UserType;
import com.cs2340.WaterNet.R;


public class SignUpActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputUsername;
    private Spinner spinner;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btnSignIn, btnSignUp, btnResetPassword;
        setContentView(R.layout.activity_signup);


        //Get FirebaseDatabase
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputUsername = (EditText) findViewById(R.id.username);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<UserType> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, UserType.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, ResetPasswordActivity.class));
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

                String tempEmail = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();
                final String username = inputUsername.getText().toString().trim();
                final UserType userType = (UserType) (spinner.getSelectedItem());

                progressBar.setVisibility(View.VISIBLE);

                Facade.createUser(tempEmail, username, password, userType, new Consumer<AuthTuple>() {

                    public void accept(AuthTuple tuple) {
                        progressBar.setVisibility(View.GONE);
                        if (tuple.getErrorMessage().length() != 0) {
                            Toast.makeText(getApplicationContext(), tuple.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        }
                        if (tuple.getSuccess()) {
                            Intent i = new Intent(SignupActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }

                });

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignUpActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
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
                                                Log.d("***", "found Singleton during sign up");
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
                                    Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(),
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
                                    Intent i = new Intent(SignUpActivity.this, MainActivity.class);
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