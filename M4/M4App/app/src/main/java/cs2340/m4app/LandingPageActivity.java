package cs2340.m4app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LandingPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        Button registerButton = (Button) findViewById(R.id.registerButton);
        Button loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setContentView(R.layout.login_page);
                EditText usernameField = (EditText) findViewById(R.id.usernameInput);
                EditText passwordField = (EditText) findViewById(R.id.passwordInput);
                Button signInButton = (Button) findViewById(R.id.signIn);

                signInButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //TODO: link to landing page
                        startActivity(new Intent());

                    }
                });

            }
        });
        //TODO: use registerbutton to link to registerpage and then to loginpage and finally to landing page

    }
}
