package com.dakiiii.travelmantics.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.dakiiii.travelmantics.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ViewTravelDealsActivity extends AppCompatActivity {
    private RecyclerView travelDealsRecyclerView;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference travelDealsDatabaseReference = firebaseDatabase
            .getReference()
            .child("traveldeals");
    private ChildEventListener childEventListener;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_deal_list_view);

        travelDealsRecyclerView = findViewById(R.id.recyclerViewTravelDeals);
        travelDealsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final TravelDealListAdapter travelDealListAdapter = new TravelDealListAdapter();
        travelDealsRecyclerView.setAdapter(travelDealListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.view_traveldeals_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case  R.id.menuItem_new_traveldeal :
                startActivity(new Intent(this, InsertTravelDealActivity.class));
                    return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
}
