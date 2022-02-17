package utests;

import dao.DataAccessException;
import dao.DatabaseManager;
import dao.personDAO;
import model.Person;
import org.junit.jupiter.api.*;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class DAOTest {
    private Connection conn;
    private DatabaseManager manager;

    @BeforeEach
    public void setUp() throws DataAccessException {
        this.manager = new DatabaseManager();
        this.conn = manager.getConnection();
    }

    @AfterEach
    public void cleanUp() {
        manager.closeConnection(true);
    }

    @Test
    @DisplayName ("Positive Insert Test")
    public void personPInsertTest()  throws DataAccessException{
        Person  testPerson1 = new Person("abcdefg","jharwood",
                "Jackson", "Harwood", "m", "12345",
                "67890", "hijkl");
        Person  testPerson2 = new Person("yourmom","jharwood",
                "Jackson", "Harwood", "m", "",
                "", "");
        personDAO pdao = new personDAO(conn);

        try {
            assertTrue(pdao.insertPerson(testPerson1));
            assertTrue(pdao.insertPerson(testPerson2));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Insert test failed");
        }
    }

    @Test
    @DisplayName("Negative Insert Test")
    public void personNInsertTest() throws DataAccessException {
        Person  testPerson = new Person("abcdefg",null,
                "Jackson", "Harwood", "m", "12345",
                "67890", "hijkl");
        personDAO pDAO = new personDAO(conn);

        assertThrows(DataAccessException.class, ()->{pDAO.insertPerson(testPerson);});
    }
}
