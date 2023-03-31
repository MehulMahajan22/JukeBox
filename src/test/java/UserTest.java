import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    User u;
    @BeforeEach
    public void setUp(){
        u = new User();
    }

    @Test
    void checkValidateUser() {
        assertEquals(1,u.validateUser("Mahajan22","admin@1234"));
        assertEquals(0,u.validateUser("Mahajan22","Admin@1234"));
    }

    @Test
    void checkUserNameAlreadyExists() {
        assertTrue(u.checkUserNameAlreadyExists("Mahajan22"));
        assertFalse(u.checkUserNameAlreadyExists("mehul214"));
    }

    @Test
    void checkPhoneAlreadyExists() {
        assertTrue(u.checkPhoneAlreadyExists(9682101021L));
        assertFalse(u.checkPhoneAlreadyExists(9419113890L));
    }
}