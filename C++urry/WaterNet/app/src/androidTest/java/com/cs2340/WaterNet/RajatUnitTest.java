package com.cs2340.WaterNet;

import com.cs2340.WaterNet.Facade.*;
import com.cs2340.WaterNet.Command.Command;
import com.cs2340.WaterNet.Model.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    @Test(timeout = 10000)
    public void createUser() {
        await().until(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                Facade.createUser("", "unitTestUser", "password", UserType.USER, new Command<AuthTuple>() {
                    public void accept(AuthTuple tuple) {
                        FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
                        try {
                            FirebaseAuth.getInstance().getCurrentUser().delete();
                        } catch (Exception e) {
                            //fail("Should not have thrown exception");
                        }
                        finished = true;
                        assertTrue(tuple.getSuccess());
                        assertTrue(tuple.getErrorMessage().equals("success!"));
                        User u = tuple.getUser();
                        assertTrue(u.equals(new User("unitTestUser", "unitTestUser@water.net")));
                        assertNotNull(u);
                        assertNotNull(fuser);

                        finished = true;
                    }
                });
                return finished;
            }
        });
        //Test for valid user creation
    }

    /*@Test(timeout = 10000)
    public void testUsername1() {
        await().until(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                Facade.createUser("", null, "password", UserType.USER, new Command<AuthTuple>() {
                    public void accept(AuthTuple tuple) {
                        assertFalse(tuple.getSuccess());
                        assertTrue(tuple.getErrorMessage().equals("Enter username!"));
                        User u = tuple.getUser();
                        assertNull(u);;
                        assertNull(FirebaseAuth.getInstance().getCurrentUser());
                        try {
                            FirebaseAuth.getInstance().getCurrentUser().delete();
                            fail("Should have thrown an exception");
                        } catch (Exception e) {}
                        finished = true;
                    }
                });
                return finished;
            }
        });

    }

    @Test(timeout = 10000)
    public void testUsername2() {
        await().until(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                Facade.createUser("", "", "password", UserType.USER, new Command<AuthTuple>() {
                    public void accept(AuthTuple tuple) {
                        assertFalse(tuple.getSuccess());
                        assertTrue(tuple.getErrorMessage().equals("Enter username!"));
                        User u = tuple.getUser();
                        assertNull(u);;
                        assertNull(FirebaseAuth.getInstance().getCurrentUser());
                        try {
                            FirebaseAuth.getInstance().getCurrentUser().delete();
                            fail("Should have thrown an exception");
                        } catch (Exception e) {}
                        finished = true;
                    }
                });
                return finished;
            }
        });
    }

    @Test(timeout = 10000)
    public void testPassword1() {

        await().until(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                Facade.createUser("", "unitTestUser", "", UserType.USER, new Command<AuthTuple>() {
                    public void accept(AuthTuple tuple) {
                        assertFalse(tuple.getSuccess());
                        assertTrue(tuple.getErrorMessage().equals("Enter password!"));
                        User u = tuple.getUser();
                        assertNull(u);;
                        assertNull(FirebaseAuth.getInstance().getCurrentUser());
                        try {
                            FirebaseAuth.getInstance().getCurrentUser().delete();
                            fail("Should have thrown an exception");
                        } catch (Exception e) {}
                        finished = true;
                    }
                });
                return finished;
            }
        });

    }

    @Test(timeout = 10000)
    public void testPassword2() {
        await().until(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                Facade.createUser("", "unitTestUser", null, UserType.USER, new Command<AuthTuple>() {
                    public void accept(AuthTuple tuple) {
                        assertFalse(tuple.getSuccess());
                        assertTrue(tuple.getErrorMessage().equals("Enter password!"));
                        User u = tuple.getUser();
                        assertNull(u);;
                        assertNull(FirebaseAuth.getInstance().getCurrentUser());
                        try {
                            FirebaseAuth.getInstance().getCurrentUser().delete();
                            fail("Should have thrown an exception");
                        } catch (Exception e) {}
                        finished = true;
                    }
                });
                return finished;
            }
        });
    }

    @Test(timeout = 10000)
    public void testPassword3() {

        await().until(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                Facade.createUser("", "unitTestUser", "bad", UserType.USER, new Command<AuthTuple>() {
                    public void accept(AuthTuple tuple) {
                        assertFalse(tuple.getSuccess());
                        assertTrue(tuple.getErrorMessage().equals("Password too short, enter minimum 6 characters!"));
                        User u = tuple.getUser();
                        assertNull(u);;
                        assertNull(FirebaseAuth.getInstance().getCurrentUser());
                        try {
                            FirebaseAuth.getInstance().getCurrentUser().delete();
                            fail("Should have thrown an exception");
                        } catch (Exception e) {}
                        finished = true;
                    }
                });
                return finished;
            }
        });
    }*/
}
