package com.dakiiii.travelmantics.db;

import com.dakiiii.travelmantics.model.TravelDeal;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FirebaseUtil {
    public static FirebaseDatabase travelDealFirebaseDatabase;
    public static DatabaseReference travelDealDatabaseReference;
    public static final String TRAVEL_DEALS_REF = "traveldeals";
    private static FirebaseUtil firebaseUtil;
    public static ArrayList<TravelDeal> travelDeals;

    private FirebaseUtil(){

    }

    public static void openFirebaseReference(String ref) {
        if (firebaseUtil == null) {
            firebaseUtil = new FirebaseUtil();
            travelDealFirebaseDatabase = FirebaseDatabase.getInstance();

        }
        travelDeals = new ArrayList<>();
        travelDealDatabaseReference = travelDealFirebaseDatabase
                .getReference()
                .child(ref );
    }
}
