package cs2340.m4app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainPageActivity extends AppCompatActivity {

    private HashMap<String, String> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        TextView textView = (TextView)findViewById(R.id.landingText);
        textView.setText("Welcome: " + getIntent().getExtras().getString("username") + "!");
        final Button logout = (Button) findViewById(R.id.logout);
        final Intent i = new Intent(this, LandingPageActivity.class);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i);
            }
        });

    }

}
