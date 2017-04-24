package com.cs2340.WaterNet.Facade;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cs2340.WaterNet.Model.*;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Pulkit Gupta on 2/28/2017.
 */

public final class Facade {
    /*
     * goes between controller and model objects,
     * contains the current user and is populated with the pin list,
     * updated on sign in and when necessary
     * basically the ultimate information holder and interfacer.
     * activities only communicate with this and this communicates with the rest of model and
     * returns information to the activity
     */
    private static User currUser = null;
    private static final FirebaseAuth auth = FirebaseAuth.getInstance();
    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static int loginAttempts = 0;


    private Facade(){
    }

    /**
     * sets up singleton
     */
    public static void start() {
        database.getReference().child("Singleton").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Singleton.setInstance(dataSnapshot.getValue(Singleton.class));
                Log.d("Singleton", Singleton.getInstance().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /**
     * reset singleton
     */
    private static void reset() {
        Log.d("RESET", Singleton.getInstance().toString());
        DatabaseReference child = FirebaseDatabase.getInstance().getReference().child("Singleton");
        child.child("userID").setValue(Singleton.getInstance().userID);
        child.child("reportID").setValue(Singleton.getInstance().reportID);
        child.child("purityReportID").setValue(Singleton.getInstance().purityReportID);
        //com.google.firebase.database.DatabaseReference.setValue();
    }


    /**
     * validate login
     * @param email email/usernamee
     * @param password password
     * @param callback callback
     */
    public static void validateLogin(String email, final String password,
                                     final Consumer<AuthTuple> callback) {
        Log.d("FACADE: ", Thread.currentThread().getName());
        String errorMessage = "";
        final AuthTuple tuple;
        if (email == null || email.isEmpty()) {
            errorMessage += "Enter email address or username";
            tuple = new AuthTuple(false, errorMessage);
            callback.accept(tuple);
        } else if (password == null || password.isEmpty()) {
            errorMessage += "Enter password!";
            tuple = new AuthTuple(false, errorMessage);
            callback.accept(tuple);

        } else if (password.length() < 6) {
            errorMessage += "Password too short, enter minimum 6 characters!";
            tuple = new AuthTuple(false, errorMessage);
            callback.accept(tuple);
        } else {

            loginAttempts++;
            if (loginAttempts > 3) {
                callback.accept(new AuthTuple(false, "too many login attempts, "
                        + "this device has been banned"));
                return;
            }
            if (!email.contains("@")) {
                email += "@water.net";
            }
            final String userEmail = email;
            tuple = new AuthTuple(false, "");
            Log.d("***", "attempting authentication");

            FirebaseAuth.getInstance().signInWithEmailAndPassword(userEmail, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                // there was an error
                                if (password.length() >= 6) {
                                    tuple.setErrorMessage(tuple.getErrorMessage()
                                            + "Authentication failed, check your email "
                                            + "and password or sign up");
                                } else {
                                    tuple.setErrorMessage(tuple.getErrorMessage()
                                            + "authentication failed");
                                }
                                tuple.setSuccess(false);
                                callback.accept(tuple);
                            } else {
                                tuple.setSuccess(true);
                                final FirebaseUser firebaseUser = task.getResult().getUser();
                                String message = userEmail + " logged in!";
                                SecurityLogger.writeNewSecurityLog(
                                        Singleton.getInstance().getTime() + " :: " + message);
                                database.getReference().addListenerForSingleValueEvent(
                                        new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        User u = dataSnapshot.child("users").child(
                                                firebaseUser.getUid()).getValue(User.class);
                                        tuple.setUser(u);
                                        currUser = u;

                                        callback.accept(tuple);

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });

                            }
                        }
                    });
        }

    }

    /**
     * create a user
     * @param tempEmail email to be set
     * @param username entered username
     * @param password password
     * @param userType type of user
     * @param callback callback
     */
    public static void createUser(String tempEmail, final String username, String password,
                                  final UserType userType, final Consumer<AuthTuple> callback) {

        String errorMessage = "";
        final String email;
        if (username == null || username.isEmpty()) {
            errorMessage += "Enter username!";
            callback.accept(new AuthTuple(false, errorMessage));
            return;
        } else if (password == null || password.isEmpty()) {
            errorMessage += "Enter password!";
            callback.accept(new AuthTuple(false, errorMessage));
            return;
        } else if (password.length() < 6) {
            errorMessage += "Password too short, enter minimum 6 characters!";
            callback.accept(new AuthTuple(false, errorMessage));
            return;
        } else {
            if (tempEmail == null || tempEmail.isEmpty() || !tempEmail.contains("@")) {
                email = username + "@water.net";
            } else {
                email = tempEmail;
            }
        }
        //create user
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String error = "";
                        //CODE ADDED BY PULKIT FOR SINGLETON

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            error += "Authentication failed." + task.getException();
                            callback.accept(new AuthTuple(false, error));
                        } else {
                            error += "success!";
                            start();
                            User u = null;
                            if (userType == UserType.USER) {
                                u = new User(username, email);
                                database.getReference().child("users").child(auth.getCurrentUser()
                                        .getUid()).setValue(u);
                            } else if (userType == UserType.MANAGER) {
                                u = new Manager(username, email);
                                database.getReference().child("users").child(auth.getCurrentUser()
                                        .getUid()).setValue(u);
                            } else if (userType == UserType.WORKER) {
                                u = new Worker(username, email);
                                database.getReference().child("users").child(auth.getCurrentUser()
                                        .getUid()).setValue(u);
                            } if (userType == UserType.ADMIN) {
                                u = new Admin(username, email);
                                database.getReference().child("users").child(auth.getCurrentUser()
                                        .getUid()).setValue(u);
                            }
                            reset();
                            currUser = u;
                            AuthTuple tuple = new AuthTuple(true, error);
                            tuple.setUser(u);
                            Log.d("***", "singleton should be updated");
                            //fixed endless loop
                            SecurityLogger.writeNewSecurityLog(Singleton.getInstance().getTime()
                                    + " :: " + email + " Registered on firebase");
                            callback.accept(tuple);

                        }
                    }
                });
    }

    public static void signOut() {
        try {
            loginAttempts = 0;
            FirebaseAuth.getInstance().signOut();
        } catch (Exception e) {}
    }

    /**
     * get the user
     * @return curruser
     */
    public static User getCurrUser() {
        return currUser;
    }

    /**
     * set user
     * @param currUser set user
     */
    public static void setCurrUser(User currUser) {
        Facade.currUser = currUser;
    }

    /**
     * update user in fb
     * @param address new address
     * @param name new name
     * @param email new email
     * @param phone new phone
     * @param userType new usertype
     */
    public static void updateUser(String address, String name, String email, String phone,
                                  UserType userType) {
        if (address != null && !address.isEmpty()) {
            currUser.setAddress(address);
        } else {
            currUser.setAddress("no address");
        }
        if (name != null && !name.isEmpty()) {
            currUser.setName(name);
        } else {
            currUser.setName("no name");
        }
        if (email != null && !email.isEmpty()) {
            if (email.contains("@")) {
                currUser.setEmail(email);
                FirebaseAuth.getInstance().getCurrentUser().updateEmail(currUser.getEmail())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("***", "email address updated.");
                                }
                            }
                        });
            }
        }
        if (phone != null && !phone.isEmpty()) {
            currUser.setPhone(phone);
        } else {
            currUser.setPhone("###-###-####");
        }

        currUser.setUserType(userType);

        FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser()
                .getUid()).setValue(currUser); //***
        SecurityLogger.writeNewSecurityLog(Singleton.getInstance().getTime() + " :: "
                + currUser.getEmail() + " edited their profile");
    }

    /**
     * create report in fb
     * @param latitude latitude
     * @param longitude longitude
     * @param wt watertype
     * @param wc watercondition
     * @param callback callback
     */
    public static void createReport(String latitude, String longitude, WaterType wt,
                                    WaterCondition wc, Consumer<String> callback) {
        String error = "";
        if (latitude == null || latitude.isEmpty()) {
            error += "Enter latitude!";
            callback.accept(error);
        } else if (longitude == null || longitude.isEmpty()) {
            error += "Enter longitude!";
            callback.accept(error);
        } else {
            error += "success!";
            double lat = Double.parseDouble(latitude);
            double lng = Double.parseDouble(longitude);
            Report post = new Report(currUser.getUsername(), lat, lng, wt, wc);
            database.getReference().child("reports").push().setValue(post);
            callback.accept(error);
        }
    }

    /**
     * create purity report in fb
     * @param latitude lat
     * @param longitude long
     * @param v virus
     * @param c contaminant
     * @param oc overall condition
     * @param callback callback
     */
    public static void createPurityReport(String latitude, String longitude, String v,
                                          String c, OverallCondition oc,
                                          Consumer<String> callback) {
        String error = "";
        if (latitude == null || latitude.isEmpty()) {
            error += "Enter latitude!";
            callback.accept(error);
        } else if (longitude == null || longitude.isEmpty()) {
            error += "Enter longitude!";
            callback.accept(error);
        } if (v == null || v.isEmpty()) {
            error += "Enter virus ppm!";
            callback.accept(error);
        } else if (c == null || c.isEmpty()) {
            error += "Enter contaminant ppm!";
            callback.accept(error);
        } else {
            error += "success!";
            double lat = Double.parseDouble(latitude);
            double lng = Double.parseDouble(longitude);
            long vppm = Long.parseLong(v);
            long cppm = Long.parseLong(c);
            PurityReport post = new PurityReport(currUser.getUsername(), lat, lng,
                    new Virus(vppm), new Contaminant(cppm), oc);
            database.getReference().child("purity_reports").push().setValue(post);
            callback.accept(error);
        }
    }

    /**
     * populate map
     * @param mMap the map to be populated
     */
    public static void getLocations(final GoogleMap mMap) {
        database.getReference().child("reports").addChildEventListener(new ChildEventListener() {
            LatLngBounds bounds;
            final LatLngBounds.Builder builder = new LatLngBounds.Builder();
            final Map<Site, Pin> map = new TreeMap<>();

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Report r = dataSnapshot.getValue(Report.class);
                Pin p;
                if (map.get(r.getSite()) == null) {
                    p = new Pin(r);
                    map.put(r.getSite(), p);

                    double latitude = p.getLat();
                    double longitude = p.getLng();

                    // Create LatLng for each locations
                    LatLng mLatlng = new LatLng(latitude, longitude);

                    // Make sure the map boundary contains the location
                    builder.include(mLatlng);
                    bounds = builder.build();

                    // Add a marker for each logged location
                    MarkerOptions mMarkerOption = new MarkerOptions()
                            .position(mLatlng)
                            .snippet(p.reportListString())
                            .title(p.getLat() + " " + p.getLng());

                    Marker m = mMap.addMarker(mMarkerOption);
                    p.setMarker(m);


                } else {
                    p = map.get(r.getSite());
                    p.addReport(r);
                    p.getMarker().setSnippet(p.reportListString());


                }

                // Zoom map to the boundary that contains every logged location
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds,
                        100));


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static RecyclerView.Adapter createReportAdapter(int report_item_layout) {
        return new FirebaseRecyclerAdapter<Report, ReportHolder>(Report.class,
                report_item_layout, ReportHolder.class, FirebaseDatabase.getInstance().getReference().child("reports")) {
            @Override
            public void populateViewHolder(ReportHolder reportViewHolder, Report report,
                                           int position) {
                reportViewHolder.setWaterConditionTV(report.getWaterCondition().toString());
                reportViewHolder.setWaterTypeTV(report.getWaterType().toString());
                reportViewHolder.setInfoTV(report.getCreator() + "  "
                        + report.getDateTime());
                reportViewHolder.setLocationTV(report.getSite().toString());
            }
        };
    }

    public static void sendResetInstructions(String email, final Consumer<String> callback) {
        if (email == null || email.isEmpty()) {
            callback.accept("Enter your registered email id");
        } else {
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                callback.accept("We have sent you instructions "
                                        + "to reset your password!");
                            } else {
                                callback.accept("Failed to send reset email!");
                            }
                        }
                    });
        }
    }

    public static void setGraphListener(final LineGraphSeries<DataPoint> virus_series, final LineGraphSeries<DataPoint> containment_series, final GraphView graph) {
        FirebaseDatabase.getInstance().getReference().child("purity_reports")
                .addValueEventListener(
            new ValueEventListener() {

                int index = 0;
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    virus_series.resetData(new DataPoint[]{});
                    containment_series.resetData(new DataPoint[]{});
                    for (DataSnapshot ds: dataSnapshot.getChildren()) {
                        index++;
                        PurityReport pr = ds.getValue(PurityReport.class);

                        try {
                            virus_series.appendData(new DataPoint(
                                    Singleton.getInstance().getDateTimeFormat().parse(pr.getDateTime()),
                                    pr.getVirus().getPPM()),true, 100);
                            containment_series.appendData(new DataPoint(
                                    Singleton.getInstance().getDateTimeFormat().parse(pr.getDateTime()),
                                    pr.getContaminant().getPPM()),true, 100);
                        } catch (ParseException e) {
                            Log.d("***", Arrays.toString(e.getStackTrace()));
                        }
                    }
                    graph.removeAllSeries();
                    graph.addSeries(virus_series);
                    graph.addSeries(containment_series);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }

    public static RecyclerView.Adapter createPurityReportAdapter(int preport_item_layout) {
        return new FirebaseRecyclerAdapter<PurityReport, PurityReportHolder>(PurityReport.class,
                preport_item_layout, PurityReportHolder.class,
                database.getReference().child("purity_reports")) {
            @Override
            public void populateViewHolder(PurityReportHolder purityReportViewHolder,
                                           PurityReport purityReport, int position) {
                purityReportViewHolder.setOverallConditionTV(
                        purityReport.getOverallCondition().toString());
                purityReportViewHolder.setInfoTV(purityReport.getCreator() + "  "
                        + purityReport.getDateTime());
                purityReportViewHolder.setLocationTV(purityReport.getSite().toString());
                purityReportViewHolder.setContaminant_PPM_TV("Contaminant PPM: "
                        + purityReport.getContaminant().getPPM());
                purityReportViewHolder.setVirus_PPM_TV("Virus PPM: "
                        + purityReport.getVirus().getPPM());
            }
        };
    }

    public static void getUserList(final Consumer<Map<String, User>> callback) {
        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, User> users = new HashMap<String, User>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    users.put(ds.getKey(), (User) ds.getValue());
                }
                callback.accept(users);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void updateSecurityLogView(final LinearLayout linearLayout, final Context context) {

        FirebaseDatabase.getInstance().getReference().child("securityLog").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                TextView textView = new TextView(context);
                textView.setText(dataSnapshot.getValue().toString());
                linearLayout.addView(textView);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
