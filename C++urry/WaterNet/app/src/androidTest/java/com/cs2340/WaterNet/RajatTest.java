package com.cs2340.WaterNet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.cs2340.WaterNet.Facade.*;
import com.cs2340.WaterNet.Model.*;
import com.google.firebase.auth.FirebaseAuth;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class RajatTest {

    @Before
    public void start() {
        FirebaseAuth.getInstance().signOut();
    }

    @After
    public void end() {
        FirebaseAuth.getInstance().signOut();

    }

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
                FirebaseAuth.getInstance().signOut();
            }
        });
    }

    @Test
    public void testUsername() {
        //test null username/email
        Facade.validateLogin(null, "password", new Consumer<AuthTuple>() {
            @Override
            public void accept(AuthTuple authTuple) {
                assertFalse(authTuple.getSuccess());
                User u = authTuple.getUser();
                assertNull(u);
                assertTrue(authTuple.getErrorMessage().equals("Enter email address or username"));
                assertNull(FirebaseAuth.getInstance().getCurrentUser());
                FirebaseAuth.getInstance().signOut();
            }
        });
    }

    @Test
    public void testUserName2() {
        //test empty username/email
        Facade.validateLogin("", "password", new Consumer<AuthTuple>() {
            @Override
            public void accept(AuthTuple authTuple) {
                assertFalse(authTuple.getSuccess());
                User u = authTuple.getUser();
                assertNull(u);
                assertTrue(authTuple.getErrorMessage().equals("Enter email address or username"));
                assertNull(FirebaseAuth.getInstance().getCurrentUser());
                FirebaseAuth.getInstance().signOut();
            }
        });
    }

    @Test
    public void testPasssword() {
        //test null password
        Facade.validateLogin("username", null, new Consumer<AuthTuple>() {
            @Override
            public void accept(AuthTuple authTuple) {
                assertFalse(authTuple.getSuccess());
                User u = authTuple.getUser();
                assertNull(u);
                assertTrue(authTuple.getErrorMessage().equals("Enter password!"));
                assertNull(FirebaseAuth.getInstance().getCurrentUser());
                FirebaseAuth.getInstance().signOut();
            }
        });
    }

    @Test
    public void testPassword2() {
        //test empty password
        Facade.validateLogin("username", "", new Consumer<AuthTuple>() {
            @Override
            public void accept(AuthTuple authTuple) {
                assertFalse(authTuple.getSuccess());
                User u = authTuple.getUser();
                assertNull(u);
                assertTrue(authTuple.getErrorMessage().equals("Enter password!"));
                assertNull(FirebaseAuth.getInstance().getCurrentUser());
                FirebaseAuth.getInstance().signOut();
            }
        });

    }


}