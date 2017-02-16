package cs2340.m4app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class LandingPageActivity extends AppCompatActivity {

    private HashMap<String, String> users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            ObjectInputStream in = new ObjectInputStream(getApplicationContext().openFileInput("users.txt"));
            users = (HashMap)in.readObject();
            in.close();
        } catch (Exception e) {
            users = new HashMap<>();
        }

        users.put("user", "pass");

        createMainPage();

        //TODO: use registerbutton to link to registerpage and then to loginpage and finally to landing page

    }

    private void createMainPage() {
        setContentView(R.layout.activity_landing_page);
        Button registerButton = (Button) findViewById(R.id.registerButton);
        final Button loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginstuff();

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setContentView(R.layout.register_page);
                final EditText usernameRegisterField = (EditText) findViewById(R.id.usernameRegisterInput);
                final EditText passwordRegisterField = (EditText) findViewById(R.id.passwordRegisterInput);
                final Button completeRegisterButton = (Button) findViewById(R.id.completeRegisterButton);
                Button cancel = (Button) findViewById(R.id.cancel2);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        createMainPage();
                    }
                });

                usernameRegisterField.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        usernameRegisterField.getText().clear();
                    }
                });

                passwordRegisterField.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        passwordRegisterField.getText().clear();
                    }
                });

                completeRegisterButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (usernameRegisterField.getText().toString().equals("") || passwordRegisterField.getText().toString().equals(""))
                            Toast.makeText(getApplicationContext(), "please enter a username and password!", Toast.LENGTH_SHORT).show();
                        else if (users.containsKey(usernameRegisterField.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "username taken!", Toast.LENGTH_SHORT).show();
                        } else{
                            users.put(usernameRegisterField.getText().toString(),  passwordRegisterField.getText().toString());
                            try {
                                ObjectOutputStream out = new ObjectOutputStream(getApplicationContext().openFileOutput("users.txt", Context.MODE_PRIVATE));
                                out.writeObject(users);
                                out.close();
                            } catch (Exception e) {
                                Log.e("***", e.getMessage());
                            }
                            Toast.makeText(getApplicationContext(), "account created!", Toast.LENGTH_SHORT).show();
                            loginstuff();
                        }
                    }
                });

            }
        });
    }

    private void loginstuff() {
        setContentView(R.layout.login_page);
        final EditText usernameField = (EditText) findViewById(R.id.usernameInput);
        final EditText passwordField = (EditText) findViewById(R.id.passwordInput);
        Button signInButton = (Button) findViewById(R.id.signIn);
        Button cancel = (Button) findViewById(R.id.cancel1);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createMainPage();
            }
        });

        usernameField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernameField.getText().clear();
            }
        });

        passwordField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordField.getText().clear();
            }
        });

        final Intent intent = new Intent(this, MainPageActivity.class);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO: link to landing page
                if (usernameField.getText().toString().equals("") || passwordField.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "please enter a username and password!", Toast.LENGTH_SHORT).show();
                else if (!users.containsKey(usernameField.getText().toString()))
                    Toast.makeText(getApplicationContext(), "account not found!", Toast.LENGTH_SHORT).show();
                else if (users.get(usernameField.getText().toString()).equals(passwordField.getText().toString())) {
                    intent.putExtra("username", usernameField.getText().toString());
                    startActivity(intent);
                } else
                    Toast.makeText(getApplicationContext(), "incorrect login try again", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
