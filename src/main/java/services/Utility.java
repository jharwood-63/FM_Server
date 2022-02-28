package services;

import dao.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;

public class Utility {
    public void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(sw);
        bw.write(str);
        bw.flush();
    }

    public void clear(Connection conn) throws DataAccessException {
        userDAO userDAO = new userDAO(conn);
        personDAO personDAO = new personDAO(conn);
        eventDAO eventDAO = new eventDAO(conn);
        authTokenDAO authTokenDAO = new authTokenDAO(conn);

        userDAO.clearUser();
        personDAO.clearPerson();
        eventDAO.clearEvent();
        authTokenDAO.clearAuthToken();
    }
}
