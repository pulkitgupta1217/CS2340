package com.cs2340.WaterNet.Model;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cs2340.WaterNet.Controller.MainActivity;
import com.cs2340.WaterNet.Controller.SignupActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.api.model.StringList;
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


    public static void validateLogin(String email, final String password, final ProgressBar progressBar, Consumer<AuthTuple> callback) {
        Log.d("FACADE: ", Thread.currentThread().getName());
        String errorMessage = "";
        AuthTuple tuple;
        if (email == null || email.length() == 0) {
            errorMessage += "Enter email address or username";
            tuple = new AuthTuple(false, errorMessage);
            callback.accept(tuple);
            return;
        } else if (password == null || password.length() == 0) {
            errorMessage += "Enter password!";
            tuple = new AuthTuple(false, errorMessage);
            callback.accept(tuple);
            return;

        } else if (password.length() < 6) {
            errorMessage += "Password too short, enter minimum 6 characters!";
            tuple = new AuthTuple(false, errorMessage);
            callback.accept(tuple);
            return;
        } else {

            if (email.indexOf("@") == -1) {
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
                                callback.accept(tuple);
                                return;
                            } else {
                                tuple.setSuccess(true);
                                final FirebaseUser firebaseUser = task.getResult().getUser();
                                Log.d("***", "authenticated!!!");
                                String message = userEmail + " logged in!";
                                SecurityLogger.writeNewSecurityLog(Singleton.getInstance().getTime() + " :: " + message);
                                database.getReference().addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        User u = dataSnapshot.child("users").child(firebaseUser.getUid()).getValue(User.class);
                                        Log.d("***", ("found user in db: " + (u != null)));
                                        //CODE ADDED BY PULKIT FOR SINGLETON
                                        Log.d("singleton Data", dataSnapshot.child("Singleton").getValue(Singleton.class).toString());
                                        start(dataSnapshot);
                                        //Singleton.setInstance(dataSnapshot.child("Singleton").getValue(Singleton.class));
                                        //if (Singleton.getInstance().getUserIDNoIncrement() == 0) {
                                        //    Log.d("***", "did not find Singleton at login");
                                        //}
                                        //Log.d("***", u.getUserID() + "  " + firebaseUser.getUid());
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
        /*try {
            Tasks.await(null);
        } catch (Exception e) {}*/

        //while (!tuple.isFinished());

        Log.d("Singleton complete: ", Singleton.getInstance().toString());
    }

    public static void createUser(String tempEmail, String username, String password, final UserType userType, final ProgressBar bar,  Consumer<AuthTuple> callback) {
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
                            database.getReference().addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.child("Singleton").getValue(Singleton.class) == null) {
                                        database.getReference().child("Singleton").setValue(Singleton.getInstance());
                                        Log.d("***", "adding new Singleton");
                                    } else {
                                        start(dataSnapshot);
                                        Log.d("***", "found Singleton during signup");
                                    }
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
                            //fixed endless loop
                            database.getInstance().getReference().child("Singleton").setValue(Singleton.getInstance());//edit
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
