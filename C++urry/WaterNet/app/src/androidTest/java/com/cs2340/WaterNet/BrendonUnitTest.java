package com.cs2340.WaterNet;

import android.util.Log;

import com.cs2340.WaterNet.Facade.AuthTuple;
import com.cs2340.WaterNet.Facade.Facade;
import com.cs2340.WaterNet.Model.Consumer;
import com.cs2340.WaterNet.Model.User;
import com.cs2340.WaterNet.Model.UserType;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Callable;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.*;

/**
 * Created by Brendon Machado on 4/11/2017.
 */

public class BrendonUnitTest {

    private User u;
    private boolean finished;
    @Before
    public void start() {
        Facade.start();
        u = null;
        finished = false;
    }

    @After
    public void end() {
        u = null;
        try {
            FirebaseAuth.getInstance().signOut();
        } catch (Exception e) {}
    }

    @Test
    public void testProfile() {
        Facade.validateLogin("user", "password", new Consumer<AuthTuple>() {
            @Override
            public void accept(AuthTuple authTuple) {
                Log.d("***", "finished start auth");
                u = authTuple.getUser();
                //finished = true;
            }
        });
        await().until(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                return Facade.getCurrUser() != null;
            }
        });
        Facade.updateUser("5555 new address ct,\n city, state, zip",
                "newName", "", "555-555-5555", UserType.USER);
        u = Facade.getCurrUser();
        assertTrue(u.getAddress().equals("5555 new address ct,\n city, state, zip"));
        assertTrue(u.getName().equals("newName"));
        assertTrue(u.getPhone().equals("555-555-5555"));
        assertTrue(u.getUserType() == UserType.USER);
    }

    @Test
    public void testAddress1() {
        Facade.validateLogin("user", "password", new Consumer<AuthTuple>() {
            @Override
            public void accept(AuthTuple authTuple) {
                Log.d("***", "finished start auth");
                u = authTuple.getUser();
                //finished = true;
            }
        });
        await().until(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                return Facade.getCurrUser() != null;
            }
        });
        Facade.updateUser("", "newName", "", "555-555-5555", UserType.USER);
        u = Facade.getCurrUser();
        assertTrue(u.getAddress().equals("no address"));
        assertTrue(u.getName().equals("newName"));
        assertTrue(u.getPhone().equals("555-555-5555"));
        assertTrue(u.getUserType() == UserType.USER);
    }

    @Test
    public void testAddress2() {
        Facade.validateLogin("user", "password", new Consumer<AuthTuple>() {
            @Override
            public void accept(AuthTuple authTuple) {
                Log.d("***", "finished start auth");
                u = authTuple.getUser();
                //finished = true;
            }
        });
        await().until(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                return Facade.getCurrUser() != null;
            }
        });
        Facade.updateUser(null, "newName", "", "555-555-5555", UserType.USER);
        u = Facade.getCurrUser();
        assertTrue(u.getAddress().equals("no address"));
        assertTrue(u.getName().equals("newName"));
        assertTrue(u.getPhone().equals("555-555-5555"));
        assertTrue(u.getUserType() == UserType.USER);
    }

    @Test
    public void testName1() {
        Facade.validateLogin("user", "password", new Consumer<AuthTuple>() {
            @Override
            public void accept(AuthTuple authTuple) {
                Log.d("***", "finished start auth");
                u = authTuple.getUser();
                //finished = true;
            }
        });
        await().until(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                return Facade.getCurrUser() != null;
            }
        });
        Facade.updateUser("5555 new address ct,\n city, state, zip",
                "", "", "555-555-5555", UserType.USER);
        u = Facade.getCurrUser();
        assertTrue(u.getAddress().equals("5555 new address ct,\n city, state, zip"));
        assertTrue(u.getName().equals("no name"));
        assertTrue(u.getPhone().equals("555-555-5555"));
        assertTrue(u.getUserType() == UserType.USER);
    }

    @Test
    public void testName2() {
        Facade.validateLogin("user", "password", new Consumer<AuthTuple>() {
            @Override
            public void accept(AuthTuple authTuple) {
                Log.d("***", "finished start auth");
                u = authTuple.getUser();
                //finished = true;
            }
        });
        await().until(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                return Facade.getCurrUser() != null;
            }
        });
        Facade.updateUser("5555 new address ct,\n city, state, zip",
                null, "", "555-555-5555", UserType.USER);
        u = Facade.getCurrUser();
        assertTrue(u.getAddress().equals("5555 new address ct,\n city, state, zip"));
        assertTrue(u.getName().equals("no name"));
        assertTrue(u.getPhone().equals("555-555-5555"));
        assertTrue(u.getUserType() == UserType.USER);
    }

    public void testPhone() {
        Facade.validateLogin("user", "password", new Consumer<AuthTuple>() {
            @Override
            public void accept(AuthTuple authTuple) {
                Log.d("***", "finished start auth");
                u = authTuple.getUser();
                //finished = true;
            }
        });
        await().until(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                return Facade.getCurrUser() != null;
            }
        });
        Facade.updateUser("5555 new address ct,\n city, state, zip",
                "newName", "", "", UserType.USER);
        u = Facade.getCurrUser();
        assertTrue(u.getAddress().equals("5555 new address ct,\n city, state, zip"));
        assertTrue(u.getName().equals("newName"));
        assertTrue(u.getPhone().equals("###-###-####"));
        assertTrue(u.getUserType() == UserType.USER);
    }

    public void testPhone2() {
        Facade.validateLogin("user", "password", new Consumer<AuthTuple>() {
            @Override
            public void accept(AuthTuple authTuple) {
                Log.d("***", "finished start auth");
                u = authTuple.getUser();
                //finished = true;
            }
        });
        await().until(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                return Facade.getCurrUser() != null;
            }
        });
        Facade.updateUser("5555 new address ct,\n city, state, zip",
                "newName", "", null, UserType.USER);
        u = Facade.getCurrUser();
        assertTrue(u.getAddress().equals("5555 new address ct,\n city, state, zip"));
        assertTrue(u.getName().equals("newName"));
        assertTrue(u.getPhone().equals("555-555-5555"));
        assertTrue(u.getUserType() == UserType.USER);
    }

    public void testUserType() {
        Facade.validateLogin("user", "password", new Consumer<AuthTuple>() {
            @Override
            public void accept(AuthTuple authTuple) {
                Log.d("***", "finished start auth");
                u = authTuple.getUser();
                //finished = true;
            }
        });
        await().until(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                return Facade.getCurrUser() != null;
            }
        });
        Facade.updateUser("5555 new address ct,\n city, state, zip",
                "newName", "", "555-555-5555", null);
        u = Facade.getCurrUser();
        assertTrue(u.getAddress().equals("5555 new address ct,\n city, state, zip"));
        assertTrue(u.getName().equals("newName"));
        assertTrue(u.getPhone().equals("555-555-5555"));
    }
}
