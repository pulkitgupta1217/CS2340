package com.cs2340.WaterNet.Model;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cs2340.WaterNet.Controller.MainActivity;
import com.cs2340.WaterNet.Controller.SignupActivity;
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
import com.google.firebase.auth.api.model.StringList;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

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
    private static List<Pin> pinList = new LinkedList<>();
    private static List<User> userList = new LinkedList<>();
    private static FirebaseAuth auth = FirebaseAuth.getInstance();
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();


    private Facade(){
    }

    public static void start() {
        database.getReference().child("Singleton").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Singleton.setInstance(dataSnapshot.getValue(Singleton.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private static void reset() {
        database.getReference().child("Singleton").setValue(Singleton.getInstance());
    }


    public static void validateLogin(String email, final String password, final ProgressBar progressBar, Consumer<AuthTuple> callback) {
        Log.d("FACADE: ", Thread.currentThread().getName());
        String errorMessage = "";
        AuthTuple tuple;
        if (email == null || email.length() == 0) {
            errorMessage += "Enter email address or username";
            tuple = new AuthTuple(false, errorMessage);
            callback.accept(tuple);
        } else if (password == null || password.length() == 0) {
            errorMessage += "Enter password!";
            tuple = new AuthTuple(false, errorMessage);
            callback.accept(tuple);

        } else if (password.length() < 6) {
            errorMessage += "Password too short, enter minimum 6 characters!";
            tuple = new AuthTuple(false, errorMessage);
            callback.accept(tuple);
        } else {

            if (!email.contains("@")) {
                email += "@water.net";
            }
            final String userEmail = email;
            tuple = new AuthTuple(false, "");
            Log.d("***", "attempting authentication");


            progressBar.setVisibility(View.VISIBLE);

            FirebaseAuth.getInstance().signInWithEmailAndPassword(userEmail, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            progressBar.setVisibility(View.GONE);
                            if (!task.isSuccessful()) {
                                // there was an error
                                if (password.length() >= 6) {
                                    tuple.setErrorMessage(tuple.getErrorMessage() + "Authentication failed, check your email and password or sign up");
                                } else {
                                    tuple.setErrorMessage(tuple.getErrorMessage() + "authentication failed");
                                }
                                tuple.setSuccess(false);
                                callback.accept(tuple);
                            } else {
                                tuple.setSuccess(true);
                                final FirebaseUser firebaseUser = task.getResult().getUser();
                                String message = userEmail + " logged in!";
                                SecurityLogger.writeNewSecurityLog(Singleton.getInstance().getTime() + " :: " + message);
                                database.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        User u = dataSnapshot.child("users").child(firebaseUser.getUid()).getValue(User.class);
                                        reset();

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

    public static void createUser(String tempEmail, String username, String password, final UserType userType, final ProgressBar bar, Consumer<AuthTuple> callback) {
        //TODO: DO THE THANG
        String errorMessage = "";
        String email = null;
        if (username == null || username.length() == 0) {
            errorMessage += "Enter username!";
            callback.accept(new AuthTuple(false, errorMessage));
            return;
        } else if (password == null || password.length() == 0) {
            errorMessage += "Enter password!";
            callback.accept(new AuthTuple(false, errorMessage));
            return;
        } else if (password.length() < 6) {
            errorMessage += "Password too short, enter minimum 6 characters!";
            callback.accept(new AuthTuple(false, errorMessage));
        } else if (tempEmail == null || tempEmail.length() == 0 || !tempEmail.contains("@")) {
            email = username + "@water.net";
        } else {
            email = tempEmail;
        }
        final String fullEmail = email;
        bar.setVisibility(View.VISIBLE);
        //create user
        auth.createUserWithEmailAndPassword(fullEmail, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String error = "";
                        //CODE ADDED BY PULKIT FOR SINGLETON
                        if(task.isSuccessful()) {
                            error += "success!";

                            //edit this
                            database.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Log.d("calling", "start(dataSnapshot)");
                                    reset();
                                    /*if (dataSnapshot.child("Singleton").getValue(Singleton.class) == null) {
                                        database.getReference().child("Singleton").setValue(Singleton.getInstance());
                                        Log.d("***", "adding new Singleton");
                                    } else {
                                        start(dataSnapshot);
                                        Log.d("***", "found Singleton during signup");
                                    }*/
                                    //TODO:
//                                    callback.accept(null);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                        bar.setVisibility(View.GONE);
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            error += "Authentication failed." + task.getException();
                            callback.accept(new AuthTuple(false, error));
                        } else {
                            User u = null;
                            if (userType == UserType.USER) {
                                u = new User(username, fullEmail);
                                database.getReference().child("users").child(auth.getCurrentUser()
                                        .getUid()).setValue(u);
                            } else if (userType == UserType.MANAGER) {
                                u = new Manager(username, fullEmail);
                                database.getReference().child("users").child(auth.getCurrentUser()
                                        .getUid()).setValue(u);
                            } else if (userType == UserType.WORKER) {
                                u = new Worker(username, fullEmail);
                                database.getReference().child("users").child(auth.getCurrentUser()
                                        .getUid()).setValue(u);
                            } if (userType == UserType.ADMIN) {
                                u = new Admin(username, fullEmail);
                                database.getReference().child("users").child(auth.getCurrentUser()
                                        .getUid()).setValue(u);
                            }
                            currUser = u;
                            Log.d("***", "singleton should be updated");
                            //fixed endless loop
                            //database.getInstance().getReference().child("Singleton").setValue(Singleton.getInstance());//edit
                            SecurityLogger.writeNewSecurityLog(Singleton.getInstance().getTime() + " :: " + fullEmail + " Registered on firebase");
                            callback.accept(new AuthTuple(true, error));

                        }
                    }
                });
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
        List<Report> reports = new LinkedList<>();
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
        List<Report> reports = new LinkedList<>();
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
        List<PurityReport> purityReports = new LinkedList<>();
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
        List<PurityReport> purityReports = new LinkedList<>();
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
    }
    public static /*graph*/ void getHistorical(Site site, PurityReport start, PurityReport end) {
        setPinList();
        List<PurityReport> relevant = new LinkedList<>();
        for (Pin p : pinList) {
            for (int i = p.getPurityReports().indexOf(start); i < p.getPurityReports().indexOf(end); i++) {
                relevant.add(p.getPurityReports().get(i));
            }
        }
    }

    public static void updateUser(String address, String name, String email, String phone, UserType userType) {
        currUser.setAddress(address);
        currUser.setName(name);
        currUser.setEmail(email);
        currUser.setPhone(phone);
        currUser.setUserType(userType);
        if (email.contains("@")) {
            currUser.setEmail(email);
            FirebaseAuth.getInstance().getCurrentUser().updateEmail(currUser.getEmail())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("***", "User email address updated.");
                            }
                        }
                    });
        }
        FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser()
                .getUid()).setValue(currUser); //***
        SecurityLogger.writeNewSecurityLog(Singleton.getInstance().getTime() + " :: " + currUser.getEmail() + " edited their profile");
    }

    public static void createReport(String latitude, String longitude, WaterType wt,
                                    WaterCondition wc, Consumer<String> callback) {
        String error = "";
        if (latitude == null || latitude.length() == 0) {
            error += "Enter latitude!";
            callback.accept(error);
        } else if (longitude == null || longitude.length() == 0) {
            error += "Enter longtitude!";
            callback.accept(error);
        } else {
            int lat = Integer.parseInt(latitude);
            int lng = Integer.parseInt(longitude);
            Report post = new Report(currUser.getUsername(), lat, lng, wt, wc);
            database.getReference().child("reports").push().setValue(post);
            callback.accept(error);
        }
    }

    public static void createPurityReport(String latitude, String longitude, Virus v,
                                          Contaminant c, OverallCondition oc,
                                          Consumer<String> callback) {
        String error = "";
        if (latitude == null || latitude.length() == 0) {
            error += "Enter latitude!";
            callback.accept(error);
        } else if (longitude == null || longitude.length() == 0) {
            error += "Enter longtitude!";
            callback.accept(error);
        } else {
            int lat = Integer.parseInt(latitude);
            int lng = Integer.parseInt(longitude);
            PurityReport post = new PurityReport(currUser.getUsername(), lat, lng, v, c, oc);
            database.getReference().child("purity_reports").push().setValue(post);
            callback.accept(error);
        }
    }

    public static void getLocations(GoogleMap mMap) {
        database.getReference().child("reports").addChildEventListener(new ChildEventListener() {
            LatLngBounds bounds;
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            TreeMap<Site, Pin> map = new TreeMap<Site, Pin>();

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
}
