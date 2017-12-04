package com.easyclean.easyclean;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    TextView dashboard;
    TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        dashboard = (TextView) findViewById(R.id.text_dashboard);
        email = (TextView) findViewById(R.id.text_email);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser()!= null) {
            dashboard.setText("sudah login");
            email.setText(auth.getCurrentUser().getEmail());
        }else{
            dashboard.setText("belum login");
        }
    }
}
