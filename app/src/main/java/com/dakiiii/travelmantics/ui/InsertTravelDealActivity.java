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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
//        Views
        titleEditText = findViewById(R.id.editTextTitle);
        descriptionEditText = findViewById(R.id.editTextDescription);
        priceEditText = findViewById(R.id.editTextPrice);
//        db
        FirebaseUtil.openFirebaseReference(FirebaseUtil.TRAVEL_DEALS_REF);
        firebaseDatabase = FirebaseUtil.travelDealFirebaseDatabase;
        databaseReference = FirebaseUtil.travelDealDatabaseReference;

        Intent intent = getIntent();
        TravelDeal travelDeal = intent.getParcelableExtra("travel_Deal");
        titleEditText.setText(travelDeal.getTitle());
        descriptionEditText.setText(travelDeal.getDescription());
        priceEditText.setText(travelDeal.getPrice());

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
                Toast.makeText(this, R.string.save_toast, Toast.LENGTH_SHORT).show();
                clean();
                return true;
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
        String title = titleEditText.getText().toString();
        String price = priceEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        TravelDeal travelDeal = new TravelDeal(title, description, price, "");
        databaseReference.push().setValue(travelDeal);
    }
}
