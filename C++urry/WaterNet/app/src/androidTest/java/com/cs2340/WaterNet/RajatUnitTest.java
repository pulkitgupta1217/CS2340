package com.cs2340.WaterNet;

import com.cs2340.WaterNet.Facade.*;
import com.cs2340.WaterNet.Model.*;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

import java.util.concurrent.Callable;
import static org.awaitility.Awaitility.*;
/**
 * Created by Rajat Khanna on 4/11/2017.
 */

public class RajatUnitTest {

    private boolean finished;

    @Before
    public void start() {
        FirebaseAuth.getInstance().signOut();
        Facade.start();
        finished = false;
    }

    @After
    public void end() {
        try {
            FirebaseAuth.getInstance().getCurrentUser().delete();
        } catch (Exception e) {}
        finished = false;

    }

    /**
     * a second test
     */
    @Test
    public void createUser() {
        await().until(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                return true;
            }
        });
        //Test for valid user creation
        Facade.createUser("", "unitTestUser", "password", UserType.USER, new Consumer<AuthTuple>() {
            public void accept(AuthTuple tuple) {
                assertTrue(tuple.getSuccess());
                assertTrue(tuple.getErrorMessage().equals("success!"));
                User u = tuple.getUser();
                assertTrue(u.equals(new User("unitTestUser", "unitTestUser@water.net")));
                assertNotNull(u);
                assertNotNull(FirebaseAuth.getInstance().getCurrentUser());
                try {
                    FirebaseAuth.getInstance().getCurrentUser().delete();
                } catch (Exception e) {
                    fail("Should not have thrown exception");
                }
            }
        });
    }

    @Test
    public void testUsername1() {
        Facade.createUser("", null, "password", UserType.USER, new Consumer<AuthTuple>() {
            public void accept(AuthTuple tuple) {
                assertFalse(tuple.getSuccess());
                assertTrue(tuple.getErrorMessage().equals("Enter username!"));
                User u = tuple.getUser();
                assertNull(u);
                assertNull(FirebaseAuth.getInstance().getCurrentUser());
                try {
                    FirebaseAuth.getInstance().getCurrentUser().delete();
                    fail("Should have thrown an exception");
                } catch (Exception e) {}
            }
        });
    }

    @Test
    public void testUsername2() {
        Facade.createUser("", "", "password", UserType.USER, new Consumer<AuthTuple>() {
            public void accept(AuthTuple tuple) {
                assertFalse(tuple.getSuccess());
                assertTrue(tuple.getErrorMessage().equals("Enter username!"));
                User u = tuple.getUser();
                assertNull(u);
                assertNull(FirebaseAuth.getInstance().getCurrentUser());
                try {
                    FirebaseAuth.getInstance().getCurrentUser().delete();
                    fail("Should have thrown an exception");
                } catch (Exception e) {}

            }
        });
    }

    @Test
    public void testPassword1() {
        Facade.createUser("", "unitTestUser", "", UserType.USER, new Consumer<AuthTuple>() {
            public void accept(AuthTuple tuple) {
                assertFalse(tuple.getSuccess());
                assertTrue(tuple.getErrorMessage().equals("Enter password!"));
                User u = tuple.getUser();
                assertNull(u);
                assertNull(FirebaseAuth.getInstance().getCurrentUser());
                try {
                    FirebaseAuth.getInstance().getCurrentUser().delete();
                    fail("Should have thrown an exception");
                } catch (Exception e) {}
            }
        });
    }

    @Test
    public void testPassword2() {
        Facade.createUser("", "unitTestUser", null, UserType.USER, new Consumer<AuthTuple>() {
            public void accept(AuthTuple tuple) {
                assertFalse(tuple.getSuccess());
                assertTrue(tuple.getErrorMessage().equals("Enter password!"));
                User u = tuple.getUser();
                assertNull(u);
                assertNull(FirebaseAuth.getInstance().getCurrentUser());
                try {
                    FirebaseAuth.getInstance().getCurrentUser().delete();
                    fail("Should have thrown an exception");
                } catch (Exception e) {}
            }
        });
    }

    @Test
    public void testPassword3() {
        Facade.createUser("", "unitTestUser", "bad", UserType.USER, new Consumer<AuthTuple>() {
            public void accept(AuthTuple tuple) {
                assertFalse(tuple.getSuccess());
                assertTrue(tuple.getErrorMessage().equals("Password too short, enter minimum 6 characters!"));
                User u = tuple.getUser();
                assertNull(u);
                assertNull(FirebaseAuth.getInstance().getCurrentUser());
                try {
                    FirebaseAuth.getInstance().getCurrentUser().delete();
                    fail("Should have thrown an exception");
                } catch (Exception e) {}
            }
        });
    }
}
