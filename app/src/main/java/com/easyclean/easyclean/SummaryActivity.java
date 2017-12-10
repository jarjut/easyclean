package com.easyclean.easyclean;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SummaryActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    DatabaseReference transactionReference;
    Transaction transaction;

    TextView textAddress, textPickUpDate, textPickUpTime, textDeliveryDate, textDeliveryTime, textServiceType, textServiceList, textTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mAuth = FirebaseAuth.getInstance();
        transactionReference = FirebaseDatabase.getInstance().getReference("Transactions");
        transaction = (Transaction) getIntent().getSerializableExtra("transaction");

        textAddress = findViewById(R.id.text_address);
        textPickUpDate = findViewById(R.id.text_pick_up_date);
        textPickUpTime = findViewById(R.id.text_pick_up_time);
        textDeliveryDate = findViewById(R.id.text_delivery_date);
        textDeliveryTime = findViewById(R.id.text_delivery_time);
        textServiceType = findViewById(R.id.text_service_type);
        textServiceList = findViewById(R.id.text_service_list);
        textTotal = findViewById(R.id.text_total);

        textAddress.setText(transaction.location);
        textPickUpDate.setText(transaction.pickUpDate);
        textPickUpTime.setText(transaction.pickUpTime);
        textDeliveryDate.setText(transaction.deliveryDate);
        textDeliveryTime.setText(transaction.deliveryTime);
        String serviceType;
        if (transaction.type.equals("regular")){
            serviceType = "Regular Laundry";
        }else{
            serviceType = "Express Laundry";
        }
        textServiceType.setText(serviceType);

        String serviceList = null;
        if (transaction.laundryBag != null){
            serviceList=transaction.laundryBag+"x Laundry Bag";
        }
        if (transaction.bedSheet != null){
            if (serviceList==null){
                serviceList=transaction.bedSheet+"x Bed Sheet";
            }else{
                serviceList+="\n"+transaction.bedSheet+"x Bed Sheet";
            }
        }
        if (transaction.bedCover != null){
            if (serviceList==null){
                serviceList=transaction.bedCover+"x Bed Cover";
            }else{
                serviceList+="\n"+transaction.bedCover+"x Bed Cover";
            }
        }
        textServiceList.setText(serviceList);
        textTotal.setText(transaction.total);

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

            case R.id.button_confirm:
                save();
                break;

            default:
                break;
        }
    }

    private void save() {
        transactionReference.child(transaction.transId).setValue(transaction);
        finish();
        startActivity(new Intent(this, ThanksActivity.class));
    }


}
