package com.easyclean.easyclean;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button btnSubmit;
    TextView linkRegister;
    EditText inputEmail;
    EditText inputPassword;

    private FirebaseAuth mAuth;
    private DatabaseReference userReference;

    private static final String TAG = "LoginActivity";
    ValueEventListener checkProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSubmit = findViewById(R.id.btn_login);
        linkRegister = findViewById(R.id.link_register);
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);

        mAuth = FirebaseAuth.getInstance();


        userReference = FirebaseDatabase.getInstance().getReference("Users");

        checkProfile = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(mAuth.getCurrentUser().getUid())){
                    finish();
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                }else{
                    finish();
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        if(mAuth.getCurrentUser() != null){
            userReference.addListenerForSingleValueEvent(checkProfile);
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
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            userReference.addListenerForSingleValueEvent(checkProfile);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
