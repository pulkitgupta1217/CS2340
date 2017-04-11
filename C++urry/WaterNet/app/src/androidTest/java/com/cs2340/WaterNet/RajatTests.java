package com.cs2340.WaterNet;

import com.cs2340.WaterNet.Facade.*;
import com.cs2340.WaterNet.Model.*;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by Rajat Khanna on 4/11/2017.
 */

public class RajatTests {

    /**
     * a second test
     */
    @Test
    public void createUserTest() {
        //Test for valid user creation
        Facade.createUser("", "unitTestUser", "password", UserType.USER, new Consumer<AuthTuple>() {
            public void accept(AuthTuple tuple) {
                assertTrue(tuple.getSuccess());
                assertTrue(tuple.getErrorMessage().equals("success!"));
                User u = tuple.getUser();
                assertTrue(u.equals(new User("unitTestUser", "unitTestUser@water.net")));
                assertNotNull(u);;
                assertNotNull(FirebaseAuth.getInstance().getCurrentUser());
                try {
                    FirebaseAuth.getInstance().getCurrentUser().delete();
                } catch (Exception e) {
                    fail("Should not have thrown exception");
                }
            }
        });


    }
}
