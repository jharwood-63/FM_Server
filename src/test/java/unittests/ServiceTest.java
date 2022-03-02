package unittests;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import dao.*;

import model.AuthToken;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import services.*;
import services.requests.*;
import services.result.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;

public class ServiceTest {
    private User[] users;
    private Person[] persons;
    private Event[] events;

    private User user;

    private LoadRequest loadRequest;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        Gson gson = new Gson();

        JsonReader jsonReader = new JsonReader(new FileReader("src/test/files/loadData.json"));
        loadRequest = (LoadRequest) gson.fromJson(jsonReader, LoadRequest.class);

        users = loadRequest.getUsers();
        persons = loadRequest.getPersons();
        events = loadRequest.getEvents();

        ClearService clearService = new ClearService();
        clearService.clear();

        user = users[0];
    }

    @Test
    @DisplayName("Clear test")
    public void ClearTest() throws DataAccessException {
        registerUser();
        ClearService clearService = new ClearService();
        Result result = clearService.clear();

        DatabaseManager manager = new DatabaseManager();
        Connection conn;
        try {
            conn = manager.getConnection();
            userDAO userDAO = new userDAO(conn);

            assertTrue(result.isSuccess());
            assertNull(userDAO.find(user.getUsername()));

            manager.closeConnection(false);
        }
        catch (DataAccessException e) {
            manager.closeConnection(false);
            e.printStackTrace();
            throw new DataAccessException("Error encountered clearing the database");
        }
    }

    @Test
    public void positiveEventTest() {
        loadData();
        AuthToken authToken = login(users[0]);
        String authTokenString = authToken.getAuthToken();

        EventRequest requestWithID = new EventRequest(events[0].getEventID(), authTokenString);
        EventRequest requestWithoutID = new EventRequest(authTokenString);

        EventService eventService = new EventService();
        Result idResult = eventService.event(requestWithID);
        Result noIDResult = eventService.event(requestWithoutID);

        assertTrue(idResult.isSuccess());
        assertTrue(noIDResult.isSuccess());
    }

    @Test
    public void negativeEventTest() {
        loadData();
        AuthToken authToken = login(users[1]);
        String authTokenString = authToken.getAuthToken();

        EventRequest requestInvalidUser = new EventRequest(events[0].getEventID(), authTokenString);

        EventService eventService = new EventService();
        Result invalidResult = eventService.event(requestInvalidUser);

        assertFalse(invalidResult.isSuccess());
    }

    @Test
    public void positiveFillTest() {
        registerUser();

        FillRequest fillRequest = new FillRequest(user.getUsername(), 4);

        FillService fillService = new FillService();
        FillResult fillResult = (FillResult) fillService.fill(fillRequest);

        assertEquals(31, fillResult.getNumPersons());
        assertEquals(122, fillResult.getNumEvents());
    }

    @Test
    public void negativeFillTest() {
        registerUser();

        FillRequest fillRequest = new FillRequest(users[1].getUsername(), 4);

        FillService fillService = new FillService();
        Result fillResult = fillService.fill(fillRequest);

        assertFalse(fillResult.isSuccess());
    }

    @Test
    public void positiveLoadTest() {
        LoadResult loadResult = loadData();

        assertEquals(users.length, loadResult.getNumUsers());
        assertEquals(persons.length, loadResult.getNumPersons());
        assertEquals(events.length, loadResult.getNumEvents());
    }

    @Test
    public void negativeLoadTest() {
        Person[] emptyArray = new Person[]{};

        LoadRequest loadRequest = new LoadRequest(users, emptyArray, events);
        LoadService loadService = new LoadService();

        Result result = loadService.load(loadRequest);

        assertFalse(result.isSuccess());
    }

    @Test
    public void positiveRegisterTest() throws DataAccessException {
        User registeredUser = registerUser();

        DatabaseManager manager = new DatabaseManager();
        Connection conn;
        try {
            conn = manager.getConnection();
            userDAO userDAO = new userDAO(conn);

            User foundUser = userDAO.find(user.getUsername());

            manager.closeConnection(false);

            assertEquals(registeredUser, foundUser);
        }
        catch (DataAccessException e) {
            manager.closeConnection(false);
            e.printStackTrace();
            throw new DataAccessException("Error");
        }
    }

    @Test
    public void negativeRegisterTest() throws DataAccessException {
        registerUser();

        RegisterRequest registerRequest = new RegisterRequest(user.getUsername(), user.getPassword(), user.getEmail(),
                user.getFirstName(), user.getLastName(), user.getGender());

        RegisterService registerService = new RegisterService();
        Result registerResult = registerService.register(registerRequest);

        assertFalse(registerResult.isSuccess());

        DatabaseManager manager = new DatabaseManager();
        Connection conn;
        try {
            conn = manager.getConnection();
            userDAO userDAO = new userDAO(conn);

            User foundUser = userDAO.find(users[1].getUsername());

            manager.closeConnection(false);

            assertNull(foundUser);
        }
        catch (DataAccessException e) {
            manager.closeConnection(false);
            e.printStackTrace();
            throw new DataAccessException("Error");
        }
    }

    @Test
    public void positiveLoginTest() throws DataAccessException {
        registerUser();
        AuthToken authToken = login(user);

        DatabaseManager manager = new DatabaseManager();
        Connection conn;
        try {
            conn = manager.getConnection();
            authTokenDAO authTokenDAO = new authTokenDAO(conn);

            AuthToken foundAuthToken = authTokenDAO.find(authToken.getAuthToken());

            manager.closeConnection(false);

            assertEquals(foundAuthToken, authToken);
        }
        catch (DataAccessException e) {
            manager.closeConnection(false);
            e.printStackTrace();
            throw new DataAccessException("error");
        }
    }

    @Test
    public void negativeLoginTest() {
        LoginRequest loginRequest = new LoginRequest(user.getUsername(), user.getPassword());
        LoginService loginService = new LoginService();

        Result loginResult = loginService.login(loginRequest);

        assertFalse(loginResult.isSuccess());
    }

    @Test
    public void positivePersonTest() {
        loadData();
        AuthToken authToken = login(users[0]);
        String authTokenString = authToken.getAuthToken();

        PersonRequest requestWithID = new PersonRequest(persons[0].getPersonID(), authTokenString);
        PersonRequest requestWithoutID = new PersonRequest(authTokenString);

        PersonService personService = new PersonService();
        Result idResult = personService.person(requestWithID);
        Result noIDResult = personService.person(requestWithoutID);

        assertTrue(idResult.isSuccess());
        assertTrue(noIDResult.isSuccess());
    }

    @Test
    public void negativePersonTest() {
        loadData();
        AuthToken authToken = login(users[1]);
        String authTokenString = authToken.getAuthToken();

        PersonRequest requestInvalidUser = new PersonRequest(persons[0].getPersonID(), authTokenString);

        PersonService personService = new PersonService();
        Result invalidResult = personService.person(requestInvalidUser);

        assertFalse(invalidResult.isSuccess());
    }

    private User registerUser() {
        RegisterRequest registerRequest = new RegisterRequest(user.getUsername(), user.getPassword(), user.getEmail(),
                user.getFirstName(), user.getLastName(), user.getGender());

        RegisterService registerService = new RegisterService();
        RegisterResult registerResult = (RegisterResult) registerService.register(registerRequest);

        User newUser = user;
        newUser.setPersonID(registerResult.getPersonID());

        return newUser;
    }

    private LoadResult loadData() {
        LoadRequest loadRequest = new LoadRequest(users, persons, events);
        LoadService loadService = new LoadService();

        return (LoadResult) loadService.load(loadRequest);
    }

    private AuthToken login(User testUser) {
        LoginRequest loginRequest = new LoginRequest(testUser.getUsername(), testUser.getPassword());
        LoginService loginService = new LoginService();

        LoginResult loginResult = (LoginResult) loginService.login(loginRequest);

        AuthToken authToken = new AuthToken(loginResult.getAuthtoken(), loginResult.getUsername());

        return authToken;
    }
}
