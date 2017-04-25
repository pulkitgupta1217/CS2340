package com.cs2340.WaterNet;

import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cs2340.WaterNet.Facade.*;
import com.cs2340.WaterNet.Command.Command;
import com.cs2340.WaterNet.Model.*;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Callable;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.*;

/**
 * Created by Jas Pyneni on 4/11/2017.
 */

public class JasUnitTest {


    @Before
    public void start() {
        Facade.start();
        Facade.validateLogin("user", "password", new Command<AuthTuple>() {
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

    @Test(timeout = 1000)
    public void testCreateReport() {
        Facade.createReport("12.0", "10.0", WaterType.BOTTLED, WaterCondition.POTABLE, new Command<String>() {
            public void accept(String s) {
                if (!s.equals("success!")) {
                    fail("Should have succeeded");
                }
            }
        });
    }

    @Test(timeout = 1000)
    public void testLat1() {
        Facade.createReport("", "10.0", WaterType.BOTTLED, WaterCondition.POTABLE, new Command<String>() {
            public void accept(String s) {
                if (!s.equals("Enter latitude!")) {
                    fail("Should have gotten this error");
                }
            }
        });
    }

    @Test(timeout = 1000)
    public void testLat2() {
        Facade.createReport(null, "10.0", WaterType.BOTTLED, WaterCondition.POTABLE, new Command<String>() {
            public void accept(String s) {
                if (!s.equals("Enter latitude!")) {
                    fail("Should have gotten this error");
                }
            }
        });
    }

    @Test(timeout = 1000)
    public void testLong1() {
        Facade.createReport("12.0", "", WaterType.BOTTLED, WaterCondition.POTABLE, new Command<String>() {
            public void accept(String s) {
                if (!s.equals("Enter longitude!")) {
                    fail("Should have gotten this error");
                }
            }
        });
    }

    @Test(timeout = 1000)
    public void testLong2() {
        Facade.createReport("12.0", null, WaterType.BOTTLED, WaterCondition.POTABLE, new Command<String>() {
            public void accept(String s) {
                if (!s.equals("Enter longitude!")) {
                    fail("Should have gotten this error");
                }
            }
        });
    }
}
