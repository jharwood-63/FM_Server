package handlers;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import services.RegisterService;
import services.requests.RegisterRequest;
import services.response.Response;
import java.io.*;
import java.net.HttpURLConnection;

public class RegisterHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("RegisterHandler");
        //FIXME: use hasALLValues to check if it has all the values
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
                Gson gson = new Gson();
                Reader reqBody = new InputStreamReader(exchange.getRequestBody());
                RegisterRequest registerRequest = (RegisterRequest) gson.fromJson(reqBody, RegisterRequest.class);

                RegisterService registerService = new RegisterService();
                Response response = registerService.register(registerRequest);

                if (response.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
                }

                OutputStream respBody = exchange.getResponseBody();
                String jsonResult = gson.toJson(response);
                writeString(jsonResult, respBody);
                respBody.close();
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                OutputStream respBody = exchange.getResponseBody();
                respBody.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Error encountered trying to register a user");
        }

    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(sw);
        bw.write(str);
        bw.flush();
    }

    private boolean hasAllValues(RegisterRequest registerRequest) {
        if (registerRequest.getUsername().equals("") || registerRequest.getUsername() == null) {
            return false;
        }

        if (registerRequest.getPassword().equals("") || registerRequest.getPassword() == null) {
            return false;
        }

        if (registerRequest.getEmail().equals("") || registerRequest.getEmail() == null) {
            return false;
        }

        if (registerRequest.getFirstName().equals("") || registerRequest.getFirstName() == null) {
            return false;
        }

        if (registerRequest.getLastName().equals("") || registerRequest.getLastName() == null) {
            return false;
        }

        if (registerRequest.getGender().equals("") || registerRequest.getGender() == null) {
            return false;
        }

        return true;
    }
}
