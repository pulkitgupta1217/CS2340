package com.cs2340.WaterNet;

import android.content.Context;
//import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import com.cs2340.WaterNet.Facade.*;
import com.cs2340.WaterNet.Model.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
//import static org.mockito.Mockito.*;

import android.test.InstrumentationTestCase;
import android.test.mock.MockContext;


import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//import android.content.SharedPreferences;
import static org.junit.Assert.*;

//TODO: MAKE THIS ACTUALLY RUN
/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
//@RunWith(MockitoJUnitRunner.class)
//@RunWith(AndroidJUnit4.class)
//@RunWith(JUnit4.class)
public class PulkitTests {

    /**
     * Pulkit's Unit Test to check that users are validated properly
     * @throws Exception junit exception
     */
    @Test
    public void loginTest() throws Exception {
        //TEST EXISTING USER
        Facade.validateLogin("user", "password", new Consumer<AuthTuple>() {
            @Override
            public void accept(AuthTuple authTuple) {
                assertTrue(authTuple.getSuccess());
                User u = authTuple.getUser();
                assertTrue(u.equals(new User("user", "user@water.net")));
                assertNotNull(u);
                assertNotNull(FirebaseAuth.getInstance().getCurrentUser());

            }
        });

        //test null username/email
        Facade.validateLogin(null, "password", new Consumer<AuthTuple>() {
            @Override
            public void accept(AuthTuple authTuple) {
                assertFalse(authTuple.getSuccess());
                User u = authTuple.getUser();
                assertNull(u);
                assertTrue(authTuple.getErrorMessage().equals("Enter email address or username"));
                assertNull(FirebaseAuth.getInstance().getCurrentUser() == null);
            }
        });

        //test empty username/email
        Facade.validateLogin("", "password", new Consumer<AuthTuple>() {
            @Override
            public void accept(AuthTuple authTuple) {
                assertFalse(authTuple.getSuccess());
                User u = authTuple.getUser();
                assertNull(u);
                assertTrue(authTuple.getErrorMessage().equals("Enter email address or username"));
                assertNull(FirebaseAuth.getInstance().getCurrentUser() == null);
            }
        });

        //test null password
        Facade.validateLogin("username", null, new Consumer<AuthTuple>() {
            @Override
            public void accept(AuthTuple authTuple) {
                assertFalse(authTuple.getSuccess());
                User u = authTuple.getUser();
                assertNull(u);
                assertTrue(authTuple.getErrorMessage().equals("Enter password!"));
                assertNull(FirebaseAuth.getInstance().getCurrentUser() == null);
            }
        });

        //test empty password
        Facade.validateLogin("username", "", new Consumer<AuthTuple>() {
            @Override
            public void accept(AuthTuple authTuple) {
                assertFalse(authTuple.getSuccess());
                User u = authTuple.getUser();
                assertNull(u);
                assertTrue(authTuple.getErrorMessage().equals("Enter password!"));
                assertNull(FirebaseAuth.getInstance().getCurrentUser() == null);
            }
        });

    }


}