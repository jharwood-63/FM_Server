package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import services.LoginService;
import services.Utility;
import services.requests.LoginRequest;
import services.result.Result;

import java.io.*;
import java.net.HttpURLConnection;

public class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("LoginHandler");

        try {
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
                Gson gson = new Gson();
                Utility utility = new Utility();
                Reader reqBody = new InputStreamReader(exchange.getRequestBody());
                LoginRequest loginRequest = (LoginRequest) gson.fromJson(reqBody, LoginRequest.class);

                LoginService loginService = new LoginService();
                Result response = loginService.login(loginRequest);

                if (response.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }

                OutputStream respBody = exchange.getResponseBody();
                String jsonResult = gson.toJson(response);
                utility.writeString(jsonResult, respBody);
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
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            OutputStream respBody = exchange.getResponseBody();
            respBody.close();
        }
    }
}
