package com.easyclean.easyclean;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LocationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    DatabaseReference transactionReference;
    Transaction transaction;

    EditText location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mAuth = FirebaseAuth.getInstance();
        transactionReference = FirebaseDatabase.getInstance().getReference("Transactions");
        transaction = (Transaction) getIntent().getSerializableExtra("transaction");

        location = findViewById(R.id.location);


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
                return super.onOptionsItemSelected(item);

        }
    }

    public void onClick(View view){
        switch (view.getId()){

            case R.id.button_back:
                finish();
                break;

            case R.id.button_next:
                save();
                break;
            default:
                break;
        }
    }

    private void save() {
        String location = this.location.getText().toString();
        if (TextUtils.isEmpty(location)){
            Toast.makeText(this, "Please Enter Location", Toast.LENGTH_SHORT).show();
            return;
        }
        transaction.location = location;

        Intent intent = new Intent(this, PickUpActivity.class);
        intent.putExtra("transaction", transaction);
        startActivity(intent);
    }
}
