package com.dakiiii.travelmantics.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.dakiiii.travelmantics.R;
import com.dakiiii.travelmantics.db.FirebaseUtil;
import com.dakiiii.travelmantics.model.TravelDeal;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InsertTravelDealActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private EditText titleEditText, descriptionEditText, priceEditText;
    private TravelDeal deal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
//        Views
        titleEditText = findViewById(R.id.editTextTitle);
        descriptionEditText = findViewById(R.id.editTextDescription);
        priceEditText = findViewById(R.id.editTextPrice);
//        db
        FirebaseUtil.openFirebaseReference( this);
        firebaseDatabase = FirebaseUtil.travelDealFirebaseDatabase;
        databaseReference = FirebaseUtil.travelDealDatabaseReference;

        Intent intent = getIntent();
        deal = intent.getParcelableExtra("travel_Deal");
        if (deal == null) {
            deal = new TravelDeal();
        }
        titleEditText.setText(deal.getTitle());
        descriptionEditText.setText(deal.getDescription());
        priceEditText.setText(deal.getPrice());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.insert_new_travel_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_save :
                saveTravelDeal();
                clean();
                backToList();
                return true;
            case R.id.menu_item_delete_traveldeal :
                deleteTravelDeal();
                backToList();
                default: return super.onOptionsItemSelected(item);
        }

    }

    private void clean() {
        priceEditText.setText("");
        titleEditText.setText("");
        descriptionEditText.setText("");
        titleEditText.requestFocus();

    }

    private void saveTravelDeal() {
        deal.setTitle(titleEditText.getText().toString());
        deal.setPrice(priceEditText.getText().toString());
        deal.setDescription(descriptionEditText.getText().toString());
        if (deal.getId() == null){
            databaseReference.push().setValue(deal);
            Toast.makeText(this, R.string.save_toast, Toast.LENGTH_SHORT).show();
        }
        else {
            databaseReference.child(deal.getId()).setValue(deal);
            Toast.makeText(this, "Travel deal updated", Toast.LENGTH_SHORT).show();
        }

    }

    private void deleteTravelDeal(){
        if (deal == null) {
            Toast.makeText(this, "save deal before deletiing it", Toast.LENGTH_SHORT).show();
        }
        else {
            databaseReference.child(deal.getId()).removeValue();
        }
    }

    private void backToList(){
        Intent intent = new Intent(this, ViewTravelDealsActivity.class);
        startActivity(intent);
    }
}
