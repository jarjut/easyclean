package com.easyclean.easyclean;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    EditText inputName;
    EditText inputHandphone;
    Button buttonContine;

    private FirebaseAuth mAuth;
    private static final String TAG = "ProfileActivity";

    private DatabaseReference userReference;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        inputName = findViewById(R.id.input_name);
        inputHandphone = findViewById(R.id.input_handphone);
        buttonContine = findViewById(R.id.button_continue);

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        userReference = FirebaseDatabase.getInstance().getReference("Users");

        user = mAuth.getCurrentUser();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_logout:
                mAuth.signOut();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void onClick(View view){
        if (view == buttonContine){
            saveProfile();
        }
    }

    private void saveProfile() {
        String uid = user.getUid();
        String name = inputName.getText().toString();
        String handphone = inputHandphone.getText().toString();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please enter your Name", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(handphone)){
            Toast.makeText(this, "Please enter your Phone Number", Toast.LENGTH_SHORT).show();
            return;
        }

        UserProfile userprofile = new UserProfile(uid, name, handphone);
        userReference.child(uid).setValue(userprofile);
    }
}
