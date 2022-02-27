package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import services.FillService;
import services.Utility;
import services.requests.FillRequest;
import services.response.Response;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class FillHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("FillHandler");
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
                Utility utility = new Utility();
                Gson gson = new Gson();

                String urlInfo = exchange.getRequestURI().toString();
                String[] info = urlInfo.split("/", 4);
                String username = info[2];
                int generations;
                if (!info[3].equals("")) {
                    generations = Integer.parseInt(info[3]);
                }
                else {
                    generations = 4;
                }

                FillRequest fillRequest = new FillRequest(username, generations);

                FillService fillService = new FillService();
                Response response = fillService.fill(fillRequest);

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
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Error encountered trying to fill the database");
        }
    }
}
