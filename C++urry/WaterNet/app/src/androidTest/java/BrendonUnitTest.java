import com.cs2340.WaterNet.Facade.AuthTuple;
import com.cs2340.WaterNet.Facade.Facade;
import com.cs2340.WaterNet.Model.Consumer;
import com.cs2340.WaterNet.Model.User;
import com.cs2340.WaterNet.Model.UserType;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Brendon Machado on 4/11/2017.
 */

public class BrendonUnitTest {

    User u;

    @Before
    public void start() {
        Facade.start();
        Facade.validateLogin("user", "password", new Consumer<AuthTuple>() {
            @Override
            public void accept(AuthTuple authTuple) {
                System.out.println("finished setup");
                u = authTuple.getUser();
            }
        });
    }

    @After
    public void end() {
        try {
            FirebaseAuth.getInstance().getCurrentUser().delete();
        } catch (Exception e) {}
    }

    @Test
    public void testProfile() {
        Facade.updateUser("5555 new address ct,\n city, state, zip",
                "newName", "", "555-555-5555", UserType.USER);
        u = Facade.getCurrUser();
        assertTrue(u.getAddress().equals("5555 new address ct,\n city, state, zip"));
        assertTrue(u.getName().equals("newName"));
        assertTrue(u.getPhone().equals("555-555-5555"));
        assertTrue(u.getUserType() == UserType.USER);
    }
}
