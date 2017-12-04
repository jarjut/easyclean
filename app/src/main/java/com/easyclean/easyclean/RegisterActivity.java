package com.easyclean.easyclean;

import android.content.Intent;
import android.nfc.Tag;
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

public class RegisterActivity extends AppCompatActivity {

    EditText inputName;
    EditText inputEmail;
    EditText inputPassword;
    Button btnRegister;
    TextView linkLogin;

    private FirebaseAuth auth;
    Snackbar snackbar;
    LinearLayout activityRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputName = (EditText) findViewById(R.id.input_name);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        btnRegister = (Button) findViewById(R.id.btn_register);
        linkLogin = (TextView) findViewById(R.id.link_login);
        activityRegister = (LinearLayout) findViewById(R.id.activity_register);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void onClick(View v){
        if (v.getId() == R.id.btn_register){
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();
            if (TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
                Toast.makeText(getApplicationContext(), "Please enter required field", Toast.LENGTH_SHORT).show();
            }else{
                registerUser(email, password);
            }
        }else if(v.getId() == R.id.link_login){
            Intent intent = new Intent (this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(), "Register Failed", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Register Success", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent (RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
}
