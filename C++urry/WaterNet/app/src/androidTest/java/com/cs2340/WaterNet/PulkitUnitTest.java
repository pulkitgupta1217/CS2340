package com.cs2340.WaterNet;

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
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 * created by Pulkit Gupta
 */
public class PulkitUnitTest  {

    private boolean finished;

    @Before
    public void start() {
        FirebaseAuth.getInstance().signOut();
        Facade.start();
        finished = false;
    }

    @After
    public void end() {
        FirebaseAuth.getInstance().signOut();
        finished = false;

    }

    @Test(timeout = 10000)
    public void loginTest() throws Exception {
        await().until(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                Facade.validateLogin("user", "password", new Command<AuthTuple>() {
                    @Override
                    public void accept(AuthTuple authTuple) {
                        assertTrue(authTuple.getSuccess());
                        User u = authTuple.getUser();
                        assertTrue(u.equals(new User("user", "user@water.net")));
                        assertNotNull(u);
                        assertNotNull(FirebaseAuth.getInstance().getCurrentUser());
                        FirebaseAuth.getInstance().signOut();
                        finished = true;
                    }
                });
                return finished;
            }
        });
        //TEST EXISTING USER

    }

    @Test(timeout = 10000)
    public void testUsername() {
        //test null username/email
        await().until(new Callable< Boolean>() {
            public Boolean call() throws Exception{
                Facade.validateLogin(null, "password", new Command<AuthTuple>() {
                    @Override
                    public void accept(AuthTuple authTuple) {
                        assertFalse(authTuple.getSuccess());
                        User u = authTuple.getUser();
                        assertNull(u);
                        assertTrue(authTuple.getErrorMessage().equals("Enter email address or username"));
                        assertNull(FirebaseAuth.getInstance().getCurrentUser());
                        FirebaseAuth.getInstance().signOut();
                        finished = true;
                    }
                });
                return finished;
            }
        });

    }

    @Test(timeout = 10000)
    public void testUserName2() {
        //test empty username/email
        await().until(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                Facade.validateLogin("", "password", new Command<AuthTuple>() {
                    @Override
                    public void accept(AuthTuple authTuple) {
                        assertFalse(authTuple.getSuccess());
                        User u = authTuple.getUser();
                        assertNull(u);
                        assertTrue(authTuple.getErrorMessage().equals("Enter email address or username"));
                        assertNull(FirebaseAuth.getInstance().getCurrentUser());
                        FirebaseAuth.getInstance().signOut();
                        finished = true;
                    }
                });
                return finished;
            }
        });

    }

    @Test(timeout = 10000)
    public void testPasssword() {
        //test null password
        await().until(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                Facade.validateLogin("username", null, new Command<AuthTuple>() {
                    @Override
                    public void accept(AuthTuple authTuple) {
                        assertFalse(authTuple.getSuccess());
                        User u = authTuple.getUser();
                        assertNull(u);
                        assertTrue(authTuple.getErrorMessage().equals("Enter password!"));
                        assertNull(FirebaseAuth.getInstance().getCurrentUser());
                        FirebaseAuth.getInstance().signOut();
                        finished= true;
                    }
                });
                return finished;
            }
        });

    }

    @Test(timeout = 10000)
    public void testPassword2() {
        //test empty password
        await().until(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                Facade.validateLogin("username", "", new Command<AuthTuple>() {
                    @Override
                    public void accept(AuthTuple authTuple) {
                        assertFalse(authTuple.getSuccess());
                        User u = authTuple.getUser();
                        assertNull(u);
                        assertTrue(authTuple.getErrorMessage().equals("Enter password!"));
                        assertNull(FirebaseAuth.getInstance().getCurrentUser());
                        FirebaseAuth.getInstance().signOut();
                        finished = true;
                    }
                });
                return finished;
            }
        });
    }


}