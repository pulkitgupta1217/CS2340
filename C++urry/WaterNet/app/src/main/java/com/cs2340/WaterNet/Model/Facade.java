package com.cs2340.WaterNet.Model;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.cs2340.WaterNet.Controller.LoginActivity;
import com.cs2340.WaterNet.Controller.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pulkit Gupta on 2/28/2017.
 */

public class Facade {
    /**
     * goes between controller and model objects, contains the current user and is populated with the pin list,
     * updated on signin and when necessary
     * basically the ultimate information holder and interfacer.
     * activities only communicate with this and this communicates with the rest of model and
     * returns information to the activity
     */
    private static User currUser = null;
    private static List<Pin> pinList = new LinkedList<Pin>();
    private static List<User> userList = new LinkedList<User>();
    private static FirebaseAuth auth = FirebaseAuth.getInstance();
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static boolean isUpdated = false;


    private Facade(){
    }

    private static void start(DataSnapshot dataSnapshot) {
        Singleton s = dataSnapshot.child("Singleton").getValue(Singleton.class);
        if (s.getUserID() < Singleton.getInstance().getUserID()
                || s.getReportID() < Singleton.getInstance().getReportID()
                || s.getPurityReportID() < Singleton.getInstance().getPurityReportID()) {
            reset(dataSnapshot);
        }
        Singleton.setInstance(dataSnapshot.child("Singleton").getValue(Singleton.class));
        //TODO: update Singleton
    }
    private static void reset(DataSnapshot dataSnapshot) {
        database.getReference().child("Singleton").setValue(Singleton.getInstance());
    }


    public static LoginNTuple validateLogin(String email, final String password, final ProgressBar progressBar) {
        Log.d("FACADE: ", Thread.currentThread().getName());
        String errorMessage = "";
        if (email == null || email.length() == 0) {
            errorMessage += "Enter email address or username";
            return new LoginNTuple(false, errorMessage, true);
        }
        if (password == null || password.length() == 0) {
            errorMessage += "Enter password!";
            return new LoginNTuple(false, errorMessage, true);
        }
        if (password.length() < 6) {
            errorMessage += "Password too short, enter minimum 6 characters!";
            return new LoginNTuple(false, errorMessage, true);
        }

        if (email.indexOf("@") == -1) {
            email += "@water.net";
        }
        //TODO: UM THIS IS FUCKED
        final String userEmail = email;
        final LoginNTuple tuple = new LoginNTuple(false, "");
        Log.d("***", "attempting authentication");


                progressBar.setVisibility(View.VISIBLE);
                auth.signInWithEmailAndPassword(userEmail, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                Log.d("AUTH: ", Thread.currentThread().getName());
                                progressBar.setVisibility(View.GONE);

                                if (!task.isSuccessful()) {
                                    // there was an error
                                    Log.d("***", "there was an error");
                                    if (password.length() >= 6) {
                                        tuple.setErrorMessage(tuple.getErrorMessage() + "Authentication failed, check your email and password or sign up");
                                    } else {
                                        tuple.setErrorMessage(tuple.getErrorMessage() + "authentication failed");
                                    }
                                    tuple.setSuccess(false);
                                    tuple.setFinished(true);
                                } else {
                                    tuple.setSuccess(true);
                                    final FirebaseUser firebaseUser = task.getResult().getUser();
                                    Log.d("***", "authenticated!!!");
                                    String message = userEmail + " logged in!";
                                    database.getReference().addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            User u = dataSnapshot.child("users").child(firebaseUser.getUid()).getValue(User.class);
                                            Log.d("***", ("found user in db: " + (u != null)));
                                            //CODE ADDED BY PULKIT FOR SINGLETON
                                            Log.d("singleton Data", dataSnapshot.child("Singleton").getValue(Singleton.class).toString());
                                            start(dataSnapshot);
                                            Singleton.setInstance(dataSnapshot.child("Singleton").getValue(Singleton.class));

                                            //if (Singleton.getInstance().getUserIDNoIncrement() == 0) {
                                            //    Log.d("***", "did not find Singleton at login");
                                            //}
                                            //Log.d("***", u.getUserID() + "  " + firebaseUser.getUid());
                                            currUser = u;
                                            tuple.setFinished(true);
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                    SecurityLogger.writeNewSecurityLog(Singleton.getInstance().getTime() + " :: " + message);
                                }
                            }
                        });

        Log.d("Singleton complete: ", Singleton.getInstance().toString());
        return tuple;
    }
    public static User getCurrUser() {
        return currUser;
    }

    public static void setCurrUser(User currUser) { //may use firebase user instead and generate user based on that
        Facade.currUser = currUser;
    }

    public static List<Pin> getPinList() {
        setPinList();
        return pinList;
    }

    public static void setPinList() {
        //firebase grab pins from db
    }

    public static List<User> getUserList() {
        setUserList();
        return userList;
    }

    public static void setUserList() {
        //firebase grab users from db and filter out admins etc.
    }

    public List<Report> getReports() {
        setPinList();
        List<Report> reports = new LinkedList<Report>();
        for (Pin p : pinList) {
            for (Report r : p.getReports()) {
                reports.add(r);
            }
        }
        return reports;
    }

    public List<Report> getReports(double lat, double lng) {
        setPinList();
        Site site = new Site(lat, lng);
        List<Report> reports = new LinkedList<Report>();
        for (Pin p : pinList) {
            for (Report r : p.getReports()) {
                if (r.getSite().closeTo(site)) {
                    reports.add(r);
                }
            }
        }
        return reports;
    }

    public List<Report> getReports(Site site) {
        return getReports(site.getLat(), site.getLng());
    }

    public List<PurityReport> getPurityReports() {
        setPinList();
        List<PurityReport> purityReports = new LinkedList<PurityReport>();
        for (Pin p : pinList) {
            for (PurityReport pr : p.getPurityReports()) {
                purityReports.add(pr);
            }
        }
        return purityReports;
    }

    public List<PurityReport> getPurityReports(double lat, double lng) {
        setPinList();
        Site site = new Site(lat, lng);
        List<PurityReport> purityReports = new LinkedList<PurityReport>();
        for (Pin p : pinList) {
            for (PurityReport pr : p.getPurityReports()) {
                if (pr.getSite().closeTo(site)) {
                    purityReports.add(pr);
                }
            }
        }
        return purityReports;
    }

    public List<PurityReport> getPurityReports(Site site) {
        return getPurityReports(site.getLat(), site.getLng());
    }

    public static /* google Maps map with pins */ void getMap() {
        getMap(pinList);
    }
    public static /* google Maps map with pins */ void getMap(List<Pin> pins) {
        return /*MapGenerator.getMap(pins)*/;
    }
    public static /*graph*/ void getHistorical(Site site, PurityReport start, PurityReport end) {
        setPinList();
        List<PurityReport> relevant = new LinkedList<PurityReport>();
        for (Pin p : pinList) {
            for (int i = p.getPurityReports().indexOf(start); i < p.getPurityReports().indexOf(end); i++) {
                relevant.add(p.getPurityReports().get(i));
            }
        }
        return /*HistoricalReportGenerator.generate(relevant) */;
    }
}
