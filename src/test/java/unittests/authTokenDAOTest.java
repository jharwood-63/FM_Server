package unittests;

import dao.DataAccessException;
import dao.DatabaseManager;
import dao.authTokenDAO;
import model.AuthToken;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

public class authTokenDAOTest {
    private Connection conn;
    private DatabaseManager manager;
    private authTokenDAO authTokenDAO;
    private AuthToken authToken;
    private AuthToken another;

    @BeforeEach
    public void setUp() throws DataAccessException {
        this.manager = new DatabaseManager();
        this.conn = manager.getConnection();
        this.authTokenDAO = new authTokenDAO(conn);

        authToken = new AuthToken("123456", "username");
        another = new AuthToken("7891011", "newUsername");
    }

    @AfterEach
    public void cleanUp() {
        manager.closeConnection(false);
    }

    @Test
    public void positiveInsertTest() throws DataAccessException {
        try {
            authTokenDAO.insertAuthToken(authToken);

            assertEquals(authToken, authTokenDAO.find(authToken.getAuthToken()));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error");
        }
    }

    @Test
    public void negativeInsertTest() throws DataAccessException {
        authTokenDAO.insertAuthToken(authToken);

        assertThrows(DataAccessException.class, ()->{authTokenDAO.insertAuthToken(authToken);});
    }

    @Test
    public void positiveFindTest() throws  DataAccessException {
        try {
            authTokenDAO.insertAuthToken(authToken);
            authTokenDAO.insertAuthToken(another);

            assertEquals(authToken, authTokenDAO.find(authToken.getAuthToken()));
            assertEquals(another, authTokenDAO.find(another.getAuthToken()));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("error");
        }
    }

    @Test
    public void negativeFindTest() throws DataAccessException {
        assertNull(authTokenDAO.find("yourmom"));

        authTokenDAO.insertAuthToken(authToken);
        authTokenDAO.clearAuthToken();

        assertNull(authTokenDAO.find(authToken.getAuthToken()));
    }

    @Test
    public void clearTest() throws DataAccessException {
        authTokenDAO.insertAuthToken(authToken);
        authTokenDAO.insertAuthToken(another);

        authTokenDAO.clearAuthToken();

        assertNull(authTokenDAO.find(authToken.getAuthToken()));
        assertNull(authTokenDAO.find(another.getAuthToken()));
    }
}
