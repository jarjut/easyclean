package com.easyclean.easyclean;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class OrderActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    RadioButton radioRegular;
    RadioButton radioExpress;
    CheckBox checkBox_laundryBag;
    CheckBox checkBox_bedSheet;
    CheckBox checkBox_bedCover;
    TextView increase_laundryBag;
    TextView increase_bedSheet;
    TextView increase_bedCover;
    TextView decrease_laundryBag;
    TextView decrease_bedSheet;
    TextView decrease_bedCover;
    TextView count_laundryBag;
    TextView count_bedSheet;
    TextView count_bedCover;

    int countLaundryBag = 0;
    int countBedSheet = 0;
    int countBedCover = 0;
    int total = 0;
    boolean regular = false;
    boolean express = false;
    boolean laundryBag = false;
    boolean bedSheet = false;
    boolean bedCover = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        radioRegular = findViewById(R.id.radio_regular);
        radioExpress = findViewById(R.id.radio_express);
        checkBox_laundryBag = findViewById(R.id.checkBox_laundryBag);
        checkBox_bedSheet = findViewById(R.id.checkBox_bedSheet);
        checkBox_bedCover = findViewById(R.id.checkBox_bedCover);
        increase_laundryBag = findViewById(R.id.increase_laundryBag);
        increase_bedSheet = findViewById(R.id.increase_bedSheet);
        increase_bedCover = findViewById(R.id.increase_bedCover);
        decrease_laundryBag = findViewById(R.id.decrease_laundryBag);
        decrease_bedSheet = findViewById(R.id.decrease_bedSheet);
        decrease_bedCover = findViewById(R.id.decrease_bedCover);
        count_laundryBag = findViewById(R.id.count_laundryBag);
        count_bedSheet = findViewById(R.id.count_bedSheet);
        count_bedCover = findViewById(R.id.count_bedCover);

        mAuth = FirebaseAuth.getInstance();
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
            case R.id.increase_laundryBag:
                countLaundryBag++;
                changeLaundryBag();
                break;

            case R.id.increase_bedSheet:
                countBedSheet++;
                changeBedSheet();
                break;

            case R.id.increase_bedCover:
                countBedCover++;
                changeBedCover();
                break;

            case R.id.decrease_laundryBag:
                if (countLaundryBag>0){
                    countLaundryBag--;
                    changeLaundryBag();
                }
                break;

            case R.id.decrease_bedSheet:
                if (countBedSheet >0){
                    countBedSheet--;
                    changeBedSheet();
                }
                break;
            case R.id.decrease_bedCover:
                if (countBedCover>0){
                    countBedCover--;
                    changeBedCover();
                }
                break;

            default:
                break;
        }
    }

    public void changeLaundryBag(){
        count_laundryBag.setText(countLaundryBag+"");
    }
    public void changeBedSheet(){
        count_bedSheet.setText(countBedSheet +"");
    }
    public void changeBedCover(){
        count_bedCover.setText(countBedCover +"");
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.radio_regular:
                if (checked) {
                    regular = true;
                    express = false;
                }
                break;
            case R.id.radio_express:
                if (checked){
                    regular = false;
                    express = true;
                }
                break;
        }
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.checkBox_laundryBag:
                laundryBag = checked;
                break;

            case R.id.checkBox_bedSheet:
                bedSheet = checked;
                break;

            case R.id.checkBox_bedCover:
                bedCover = checked;
                break;

        }
    }

    public void hitung(){
        int laundryBagCost = 41000;
        int bedCoverCost =  50000;
        int bedSheetCost = 25000;
        int express = 20000;

    }

}
