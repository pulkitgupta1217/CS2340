package com.cs2340.WaterNet;

import android.content.pm.LauncherApps;
import android.util.Log;

import com.cs2340.WaterNet.Facade.AuthTuple;
import com.cs2340.WaterNet.Facade.Facade;
import com.cs2340.WaterNet.Model.Consumer;
import com.cs2340.WaterNet.Model.OverallCondition;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Callable;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.*;

/**
 * Created by Danny Zhang on 4/11/2017.
 */

public class DannyUnitTest {

    @Before
    public void start() {
        Facade.start();
        Facade.validateLogin("user", "password", new Consumer<AuthTuple>() {
            @Override
            public void accept(AuthTuple authTuple) {
                Log.d("***", "finished start auth");
                //finished = true;
            }
        });
        await().until(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                return Facade.getCurrUser() != null;
            }
        });
    }

    @After
    public void end() {
        try {
            FirebaseAuth.getInstance().signOut();
        } catch (Exception e) {}
    }

    @Test
    public void testCreatePurityReport() {
        Facade.createPurityReport("12.0", "10.0", "100", "15", OverallCondition.SAFE, new Consumer<String>() {
            @Override
            public void accept(String s) {
                if (!s.equals("success!")){
                    fail("should have succeeded");
                }
            }
        });
    }

    @Test
    public void testLat1() {
        Facade.createPurityReport("", "10.0", "100", "15", OverallCondition.SAFE, new Consumer<String>() {
            @Override
            public void accept(String s) {
                if (!s.equals("Enter latitude!")) {
                    fail("Should have gotten this error");
                }
            }
        });
    }

    @Test
    public void testLat2() {
        Facade.createPurityReport(null, "10.0", "100", "15", OverallCondition.SAFE, new Consumer<String>() {
            @Override
            public void accept(String s) {
                if (!s.equals("Enter latitude!")) {
                    fail("Should have gotten this error");
                }
            }
        });
    }

    @Test
    public void testLong1() {
        Facade.createPurityReport("12.0", "", "100", "15", OverallCondition.SAFE, new Consumer<String>() {
            @Override
            public void accept(String s) {
                if (!s.equals("Enter longitude!")){
                    fail("should have gotten this error");
                }
            }
        });
    }

    @Test
    public void testLong2() {
        Facade.createPurityReport("12.0", null, "100", "15", OverallCondition.SAFE, new Consumer<String>() {
            @Override
            public void accept(String s) {
                if (!s.equals("Enter longitude!")){
                    fail("should have gotten this error");
                }
            }
        });
    }

    @Test
    public void testVPPM1() {
        Facade.createPurityReport("12.0", "10.0", "", "15", OverallCondition.SAFE, new Consumer<String>() {
            @Override
            public void accept(String s) {
                if (!s.equals("Enter virus ppm!")){
                    fail("should have thrown this error");
                }
            }
        });
    }

    @Test
    public void testVPPM2() {
        Facade.createPurityReport("12.0", "10.0", null, "15", OverallCondition.SAFE, new Consumer<String>() {
            @Override
            public void accept(String s) {
                if (!s.equals("Enter virus ppm!")){
                    fail("should have thrown this error");
                }
            }
        });
    }

    @Test
    public void testCPPM1() {
        Facade.createPurityReport("12.0", "10.0", "100", "", OverallCondition.SAFE, new Consumer<String>() {
            @Override
            public void accept(String s) {
                if (!s.equals("Enter contaminant ppm!")){
                    fail("should have thrown this error");
                }
            }
        });
    }

    @Test
    public void testCPPM2() {
        Facade.createPurityReport("12.0", "10.0", "100", null, OverallCondition.SAFE, new Consumer<String>() {
            @Override
            public void accept(String s) {
                if (!s.equals("Enter contaminant ppm!")){
                    fail("should have thrown this error");
                }
            }
        });
    }
}
