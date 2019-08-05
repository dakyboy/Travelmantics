package com.dakiiii.travelmantics.ui;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.dakiiii.travelmantics.R;
import com.dakiiii.travelmantics.db.FirebaseUtil;
import com.dakiiii.travelmantics.model.TravelDeal;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TravelDealListAdapter  extends RecyclerView.Adapter<TravelDealListAdapter.TravelDealViewHolder> {

    private ArrayList<TravelDeal> travelDeals;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;

    public TravelDealListAdapter(){
        FirebaseUtil.openFirebaseReference(FirebaseUtil.TRAVEL_DEALS_REF);
        firebaseDatabase = FirebaseUtil.travelDealFirebaseDatabase;
        databaseReference = FirebaseUtil.travelDealDatabaseReference;
        travelDeals = FirebaseUtil.travelDeals;
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                 TravelDeal travelDeal = dataSnapshot.getValue(TravelDeal.class);
                Log.d("this shit", travelDeal.getTitle());
                travelDeal.setId(dataSnapshot.getKey());
                travelDeals.add(travelDeal);
                notifyItemInserted(travelDeals.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReference.addChildEventListener(childEventListener);
    }

    @NonNull
    @Override
    public TravelDealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.traveldeal_row_layout,
                parent, false);

        return new TravelDealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TravelDealViewHolder holder, int position) {
//        Tra
        holder.bind(travelDeals.get(position));

    }

    @Override
    public int getItemCount() {
        return travelDeals.size();
    }

    public class TravelDealViewHolder extends RecyclerView.ViewHolder{
        TextView textViewTitle, textViewDescription, textViewPrice;
        public TravelDealViewHolder(@NonNull final View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTravelDealTitle);
            textViewDescription = itemView.findViewById(R.id.textViewTravelDealDescription);
            textViewPrice = itemView.findViewById(R.id.textViewTravelDealPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    TravelDeal travelDeal = travelDeals.get(position);
                    Intent intent = new Intent(itemView.getContext(), InsertTravelDealActivity.class);
                    intent.putExtra("travel_Deal", travelDeal);
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        void bind(TravelDeal travelDeal){
            textViewTitle.setText(travelDeal.getTitle());
            textViewDescription.setText(travelDeal.getDescription());
            textViewPrice.setText(travelDeal.getPrice());
    }


    }
}
