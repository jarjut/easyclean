package com.easyclean.easyclean;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button btnSubmit;
    TextView linkRegister;
    EditText inputEmail;
    EditText inputPassword;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSubmit = (Button) findViewById(R.id.btn_login);
        linkRegister = (TextView) findViewById(R.id.link_register);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null){
            startActivity(new Intent(this, DashboardActivity.class));
        }

    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onClick(View v){
        if (v.getId() == R.id.btn_login){
            loginUser(inputEmail.getText().toString(), inputPassword.getText().toString());
        }else if(v.getId() == R.id.link_register){
            Intent intent = new Intent (this, RegisterActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void loginUser(String email, String password){

        Intent intent = new Intent (this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}
