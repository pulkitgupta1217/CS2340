package com.cs2340.WaterNet.Model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by rajatkhanna on 2/23/17.
 */
public class Numerics {

    private static Numerics ourInstance = new Numerics();
    private static FirebaseDatabase firebaseDatabase;
    private static int securityID;

    public static Numerics getInstance() {
        return ourInstance;
    }

    private Numerics() {

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                securityID = dataSnapshot.child("numerics").child("securityID").getValue(int.class);
                Log.d("***", "securityID set to " + securityID);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        firebaseDatabase.getReference().child("helper").setValue("");


    }
}
