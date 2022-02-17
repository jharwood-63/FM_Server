package utests;

import dao.DataAccessException;
import dao.DatabaseManager;
import dao.userDAO;
import model.User;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.util.UUID;

public class userDAOTest {
    private Connection conn;
    private DatabaseManager manager;
    private userDAO uDAO;

    @BeforeEach
    public void setUp() throws DataAccessException {
        this.manager = new DatabaseManager();
        this.conn = manager.getConnection();
        this.uDAO = new userDAO(this.conn);
    }

    @AfterEach
    public void cleanUp() {
        manager.closeConnection(false);
    }

    @Test
    @DisplayName ("Positive Insert Test")
    public void positiveInsertTest() {
        String personID1 = UUID.randomUUID().toString();
        String personID2 = UUID.randomUUID().toString();
        User testUser1 = new User("jharwood", "mypassword",
                "me@byu.edu", "Jackson", "Harwood", "m", personID1);
        User  testUser2 = new User("yourmom", "newpassword",
                "you@byu.edu", "Jefferson", "Harwood", "m", personID2);

        assertDoesNotThrow(()->{uDAO.insertUser(testUser1);});
        assertDoesNotThrow(()->{uDAO.insertUser(testUser2);});
    }

    @Test
    @DisplayName("Insert null username and already used username")
    public void negativeInsertTest() throws DataAccessException {
        String personID1 = UUID.randomUUID().toString();
        String personID2 = UUID.randomUUID().toString();
        User testUser1 = new User(null, "mypassword",
                "me@byu.edu", "Jackson", "Harwood", "m", personID1);
        User  testUser2 = new User("yourmom", "newpassword",
                "you@byu.edu", "Jefferson", "Harwood", "m", personID2);

        uDAO.insertUser(testUser2);

        assertThrows(DataAccessException.class, () -> {uDAO.insertUser(testUser1);});
        assertThrows(DataAccessException.class, () -> {uDAO.insertUser(testUser2);});
    }
}
