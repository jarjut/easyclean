package com.easyclean.easyclean;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class PickUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    DatabaseReference transactionReference;
    Transaction transaction;

    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);

    TextView textPickUpDate, textPickUpTime, textDeliveryDate, textDeliveryTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        mAuth = FirebaseAuth.getInstance();
        transactionReference = FirebaseDatabase.getInstance().getReference("Transactions");
        transaction = (Transaction) getIntent().getSerializableExtra("transaction");

        textPickUpDate = findViewById(R.id.text_pick_up_date);
        textPickUpTime = findViewById(R.id.text_pick_up_time);
        textDeliveryDate = findViewById(R.id.text_delivery_date);
        textDeliveryTime = findViewById(R.id.text_delivery_time);

        setPickUpTime(hour, minute);
        setDeliveryTime(hour, minute);
        setPickUpDate(year,month,day);
        setDeliveryDate(year,month,day);

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

            case R.id.layout_pick_up_date:
                datePicker();
                break;

            case R.id.layout_pick_up_time:
                timePicker();
                break;


            default:
                break;
        }
    }

    private void timePicker() {

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hoursOfDay, int minute) {
                setPickUpTime(hoursOfDay, minute);
                setDeliveryTime(hoursOfDay, minute);
            }
        },
        hour, minute, true);

        timePickerDialog.show();
    }

    private void datePicker() {

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                setPickUpDate(year,month,day);
                setDeliveryDate(year,month,day);
            }
        },
        year,month,day);

        datePickerDialog.show();

    }

    private String dateFormater(int year, int month, int day){
        Calendar cal = new GregorianCalendar(year, month, day);
        return dateFormat.format(cal.getTime());
    }

    private void setPickUpDate(int year, int month, int day){
        textPickUpDate.setText(dateFormater(year,month,day));
    }

    private void setPickUpTime(int hour, int minute){
        textPickUpTime.setText(hour+":"+minute);
    }

    private void setDeliveryDate(int year, int month, int day){
        if (transaction.type.equals("regular")){
            day+=3;
        }else{
            day+=1;
        }
        textDeliveryDate.setText(dateFormater(year,month,day));
    }

    private void setDeliveryTime(int hour, int minute){
        textDeliveryTime.setText(hour+":"+minute);
    }

    private void save() {
        transaction.pickUpDate = textPickUpDate.getText().toString();
        transaction.pickUpTime = textPickUpTime.getText().toString();
        transaction.deliveryDate = textDeliveryDate.getText().toString();
        transaction.deliveryTime = textDeliveryTime.getText().toString();

        Intent intent = new Intent(this, SummaryActivity.class);
        intent.putExtra("transaction", transaction);
        startActivity(intent);

    }



}
