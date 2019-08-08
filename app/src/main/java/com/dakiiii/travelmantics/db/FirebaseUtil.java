package com.dakiiii.travelmantics.db;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.dakiiii.travelmantics.model.TravelDeal;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class FirebaseUtil {
    public static final int RC_SIGN_IN = 100;
    public static FirebaseDatabase travelDealFirebaseDatabase;
    public static DatabaseReference travelDealDatabaseReference;
    public static final String TRAVEL_DEALS_REF = "traveldeals";
    private static FirebaseUtil firebaseUtil;
    private static FirebaseAuth firebaseAuth;
    private static FirebaseAuth.AuthStateListener authStateListener;
    public static ArrayList<TravelDeal> travelDeals;
    private static Activity activity;

    private FirebaseUtil(){

    }

    private static FirebaseDatabase getFirebaseInstance(){
        if (travelDealFirebaseDatabase == null) {
            travelDealFirebaseDatabase = FirebaseDatabase.getInstance();
        }
        return travelDealFirebaseDatabase;
    }

    public static void openTravelDealFirebaseReference() {
        travelDealDatabaseReference = getFirebaseInstance().getReference().child(TRAVEL_DEALS_REF);
        travelDeals = new ArrayList<>();

    }



    public static void openFirebaseReference(final Activity activityCaller) {
        if (firebaseUtil == null) {
            firebaseUtil = new FirebaseUtil();
            firebaseAuth = FirebaseAuth.getInstance();
            activity = activityCaller;
            authStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (firebaseAuth.getInstance().getCurrentUser() == null){
                        signIn();
                    }

                    Toast.makeText(activityCaller, "Welcome Back", Toast.LENGTH_SHORT).show();
                }

                private void signIn() {
                    // Choose authentication providers
                    List<AuthUI.IdpConfig> providers = Arrays.asList(
                            new AuthUI.IdpConfig.EmailBuilder().build(),
                            new AuthUI.IdpConfig.GoogleBuilder().build());

                    // Create and launch sign-in intent


                    activity.startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(providers)
                                    .build(),
                            RC_SIGN_IN);
                }


            };

        }

    }



    public static void attachListener() {
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    public static void dettachListener() {
        firebaseAuth.removeAuthStateListener(authStateListener);
    }
}
