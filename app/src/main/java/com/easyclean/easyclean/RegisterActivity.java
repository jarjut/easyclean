package com.easyclean.easyclean;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onClick(View v){
        if (v.getId() == R.id.btn_register){
            registerUser(inputEmail.getText().toString(), inputPassword.getText().toString());
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
                            snackbar = Snackbar.make(activityRegister, "Error: "+task.getException(), Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }
                        else{
                            snackbar = Snackbar.make(activityRegister, "Register Success", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                            Intent intent = new Intent (RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
}
