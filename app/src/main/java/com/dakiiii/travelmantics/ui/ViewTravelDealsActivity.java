package com.dakiiii.travelmantics.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dakiiii.travelmantics.R;
import com.dakiiii.travelmantics.db.FirebaseUtil;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ViewTravelDealsActivity extends AppCompatActivity {
    private RecyclerView travelDealsRecyclerView;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_deal_list_view);

        mFirebaseAuth = FirebaseAuth.getInstance();

        travelDealsRecyclerView = findViewById(R.id.recyclerViewTravelDeals);
        travelDealsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final TravelDealListAdapter travelDealListAdapter = new TravelDealListAdapter();
        travelDealsRecyclerView.setAdapter(travelDealListAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mFirebaseAuth == null) {

        }
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

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUtil.openFirebaseReference(this);
        FirebaseUtil.attachListener();
    }

    @Override
    protected void onPause() {
        super.onPause();

        FirebaseUtil.dettachListener();
    }


}
