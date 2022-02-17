package utests;

import dao.DataAccessException;
import dao.DatabaseManager;
import dao.personDAO;
import model.Person;

import org.junit.jupiter.api.*;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;

public class personDAOTest {
    private Connection conn;
    private DatabaseManager manager;
    private personDAO pDAO;

    @BeforeEach
    public void setUp() throws DataAccessException {
        this.manager = new DatabaseManager();
        this.conn = manager.getConnection();
        this.pDAO = new personDAO(this.conn);
    }

    @AfterEach
    public void cleanUp() {
        manager.closeConnection(false);
    }

    @Test
    @DisplayName ("Positive Insert Test")
    public void positiveInsertTest() {
        Person  testPerson1 = new Person("abcdefg","jharwood",
                "Jackson", "Harwood", "m", "12345",
                "67890", "hijkl");
        Person  testPerson2 = new Person("yourmom","jharwood",
                "Jackson", "Harwood", "m", "",
                "", "");

        assertDoesNotThrow(()->{pDAO.insertPerson(testPerson1);});
        assertDoesNotThrow(()->{pDAO.insertPerson(testPerson2);});
    }

    @Test
    @DisplayName("Negative Insert Test")
    public void negativeInsertTest() throws DataAccessException{
        Person  testPerson1 = new Person("abcdefg",null,
                "Jackson", "Harwood", "m", "12345",
                "67890", "hijkl");
        Person  testPerson2 = new Person("yourmom","jharwood",
                "Jackson", "Harwood", "m", "",
                "", "");

        pDAO.insertPerson(testPerson2);

        assertThrows(DataAccessException.class, ()->{pDAO.insertPerson(testPerson1);});
        assertThrows(DataAccessException.class, ()->{pDAO.insertPerson(testPerson2);});
    }

    @Test
    @DisplayName("Positive Find Test")
    public void positiveFindTest() throws DataAccessException {
        Person  testPerson = new Person("abcdefg","jharwood",
                "Jackson", "Harwood", "m", "12345",
                "67890", "hijkl");
        try {
            assertEquals(null, pDAO.find("12345"));

            pDAO.insertPerson(testPerson);

            assertEquals(testPerson, pDAO.find(testPerson.getPersonID()));
            assertEquals(null, pDAO.find("12345"));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error accessing data in the database");
        }
    }

    @Test
    @DisplayName("Negative Find Test")
    public void negativeFindTest() {
        //FIXME: not sure what to do here
    }

    @Test
    @DisplayName("Clear person Test")
    public void clearTest() throws DataAccessException {
        Person  testPerson1 = new Person("abcdefg","jharwood",
                "Jackson", "Harwood", "m", "12345",
                "67890", "hijkl");
        Person  testPerson2 = new Person("yourmom","jharwood",
                "Jackson", "Harwood", "m", "",
                "", "");

        try {
            pDAO.insertPerson(testPerson1);
            pDAO.insertPerson(testPerson2);

            pDAO.clearPerson();

            assertEquals(null, pDAO.find(testPerson1.getPersonID()));
            assertEquals(null, pDAO.find(testPerson2.getPersonID()));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while testing person clear");
        }
    }
}
