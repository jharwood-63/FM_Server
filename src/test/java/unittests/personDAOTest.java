package unittests;

import dao.*;
import model.Person;

import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class personDAOTest {
    private Connection conn;
    private DatabaseManager manager;
    private personDAO pDAO;
    private userDAO uDAO;
    private authTokenDAO atDAO;
    private eventDAO eDAO;
    Person personNormal;
    Person personNullIDs;
    Person personNullUsername;

    @BeforeEach
    public void setUp() throws DataAccessException {
        this.manager = new DatabaseManager();
        this.conn = manager.getConnection();
        this.pDAO = new personDAO(this.conn);
        this.eDAO = new eventDAO(this.conn);
        this.atDAO = new authTokenDAO(this.conn);
        this.uDAO = new userDAO(this.conn);

        String personID1 = UUID.randomUUID().toString();
        String personID2 = UUID.randomUUID().toString();
        String personID3 = UUID.randomUUID().toString();

        String fatherID = UUID.randomUUID().toString();
        String motherID = UUID.randomUUID().toString();
        String spouseID = UUID.randomUUID().toString();

        this.personNormal = new Person(personID1,"jlharwood",
                "Jackson", "Harwood", "m", fatherID,
                motherID, spouseID);
        this.personNullIDs = new Person(personID2,"jlharwood",
                "Jackson", "Harwood", "m", null,
                null, null);
        this.personNullUsername = new Person(personID3,null,
                "Jackson", "Harwood", "m", fatherID,
                motherID, spouseID);
    }

    @AfterEach
    public void cleanUp() {
        manager.closeConnection(false);
    }

    @Test
    @DisplayName ("Positive Insert Test")
    public void positiveInsertTest() throws DataAccessException {
        try {
            pDAO.insertPerson(personNormal);
            pDAO.insertPerson(personNullIDs);

            assertEquals(personNormal, pDAO.find(personNormal.getPersonID()));
            assertEquals(personNullIDs, pDAO.find(personNullIDs.getPersonID()));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered trying to insert a person into the database");
        }
    }

    @Test
    @DisplayName("Negative Insert Test")
    public void negativeInsertTest() throws DataAccessException{
        pDAO.insertPerson(personNullIDs);

        assertThrows(DataAccessException.class, ()->{pDAO.insertPerson(personNullUsername);});
        assertThrows(DataAccessException.class, ()->{pDAO.insertPerson(personNullIDs);});
    }

    @Test
    @DisplayName("Positive Find Test")
    public void positiveFindTest() throws DataAccessException {
        try {
            pDAO.insertPerson(personNormal);
            pDAO.insertPerson(personNullIDs);

            assertEquals(personNormal, pDAO.find(personNormal.getPersonID()));
            assertEquals(personNullIDs, pDAO.find(personNullIDs.getPersonID()));
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
            assertEquals(null, pDAO.find("yourmom"));

            pDAO.insertPerson(personNormal);
            pDAO.clearPerson();

            assertEquals(null, pDAO.find(personNormal.getPersonID()));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered trying to find a person in the database");
        }
    }

    @Test
    @DisplayName("Clear person Test")
    public void clearTest() throws DataAccessException {
        try {
            pDAO.insertPerson(personNormal);
            pDAO.insertPerson(personNullIDs);

            pDAO.clearPerson();

            assertEquals(null, pDAO.find(personNormal.getPersonID()));
            assertEquals(null, pDAO.find(personNullIDs.getPersonID()));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while testing person clear");
        }
    }

    @Test
    public void clearUsernameTest() throws DataAccessException {
        try {
            pDAO.insertPerson(personNormal);
            pDAO.insertPerson(personNullIDs);

            pDAO.clearPerson(personNormal.getAssociatedUsername());

            assertEquals(null, pDAO.find(personNormal.getPersonID()));
            assertEquals(null, pDAO.find(personNullIDs.getPersonID()));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error");
        }
    }

    @Test
    @DisplayName("Positive find all test")
    public void findAllTest() throws DataAccessException {
        try {
            pDAO.insertPerson(personNormal);
            pDAO.insertPerson(personNullIDs);

            Set<Person> persons = pDAO.findAll(personNormal.getAssociatedUsername());

            for (Person person : persons) {
                assertTrue(person.isInSet(persons));
            }
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            throw new DataAccessException("Error accessing data in the database");
        }
    }
}
