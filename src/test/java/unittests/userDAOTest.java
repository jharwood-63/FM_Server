package unittests;

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

    private User userNormal1;
    private User userNormal2;
    private User userNullUsername;

    @BeforeEach
    public void setUp() throws DataAccessException {
        this.manager = new DatabaseManager();
        this.conn = manager.getConnection();
        this.uDAO = new userDAO(this.conn);

        String personID1 = UUID.randomUUID().toString();
        String personID2 = UUID.randomUUID().toString();
        String personID3 = UUID.randomUUID().toString();

        userNormal1 = new User("jharwood", "mypassword",
                "me@byu.edu", "Jackson", "Harwood", "m", personID1);
        userNormal2 = new User("yourmom", "newpassword",
                "you@byu.edu", "Jefferson", "Harwood", "m", personID2);
        userNullUsername = new User(null, "mypassword",
                "me@byu.edu", "Jackson", "Harwood", "m", personID3);
    }

    @AfterEach
    public void cleanUp() {
        manager.closeConnection(false);
    }

    @Test
    @DisplayName ("Positive Insert Test")
    public void positiveInsertTest() throws DataAccessException {
        try {
            uDAO.insertUser(userNormal1);
            uDAO.insertUser(userNormal2);

            assertEquals(userNormal1, uDAO.find(userNormal1.getUsername()));
            assertEquals(userNormal2, uDAO.find(userNormal2.getUsername()));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered trying to insert a user into the database");
        }
    }

    @Test
    @DisplayName("Insert null username and already used username")
    public void negativeInsertTest() throws DataAccessException {
        uDAO.insertUser(userNormal2);

        assertThrows(DataAccessException.class, () -> {uDAO.insertUser(userNullUsername);});
        assertThrows(DataAccessException.class, () -> {uDAO.insertUser(userNormal2);});
    }

    @Test
    @DisplayName("Positive Find Test")
    public void positiveFindTest() throws DataAccessException {
        try {
            uDAO.insertUser(userNormal1);
            uDAO.insertUser(userNormal2);

            assertEquals(userNormal1, uDAO.find(userNormal1.getUsername()));
            assertEquals(userNormal2, uDAO.find(userNormal2.getUsername()));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error accessing data in the database");
        }
    }

    @Test
    @DisplayName("Negative Find Test")
    public void negativeFindTest() throws DataAccessException {
        try {
            assertEquals(null, uDAO.find("yourmom"));

            uDAO.insertUser(userNormal1);
            uDAO.clearUser();

            assertEquals(null, uDAO.find(userNormal1.getUsername()));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered trying to find a user in the database");
        }
    }

    @Test
    @DisplayName("Clear user Test")
    public void clearTest() throws DataAccessException {
        try {
            uDAO.insertUser(userNormal1);
            uDAO.insertUser(userNormal2);

            uDAO.clearUser();

            assertEquals(null, uDAO.find(userNormal1.getUsername()));
            assertEquals(null, uDAO.find(userNormal2.getUsername()));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while testing user clear");
        }
    }
}
