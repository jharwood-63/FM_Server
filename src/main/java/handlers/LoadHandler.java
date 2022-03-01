package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import services.LoadService;
import services.Utility;
import services.requests.LoadRequest;
import services.result.Result;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;

public class LoadHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("LoadHandler");
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
                Gson gson = new Gson();
                Utility utility = new Utility();
                Reader reqBody = new InputStreamReader(exchange.getRequestBody());
                LoadRequest loadRequest = (LoadRequest) gson.fromJson(reqBody, LoadRequest.class);

                LoadService loadService = new LoadService();
                Result response = loadService.load(loadRequest);

                if (response.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
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
            throw new IOException("Error encountered trying to load the database with new data");
        }
    }
}
