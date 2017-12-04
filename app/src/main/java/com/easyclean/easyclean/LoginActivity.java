package com.easyclean.easyclean;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void onClick(View v){
        if (v.getId() == R.id.btn_login){
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();
            if (TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
                Toast.makeText(getApplicationContext(), "Please enter Email and Password", Toast.LENGTH_SHORT).show();
            }else{
                loginUser(email, password);
            }
        }else if(v.getId() == R.id.link_register){
            Intent intent = new Intent (this, RegisterActivity.class);
            startActivity(intent);
        }

    }

    private void loginUser(String email, final String password){
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                        }else{
                            Intent intent = new Intent (LoginActivity.this, DashboardActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
}
