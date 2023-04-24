package Handlers;

import DAO.DataAccessException;
import Result.ClearResult;
import Services.ClearService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;


public class ClearHandler extends HandlerFunctions implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                ClearService cS = new ClearService();
                ClearResult result = cS.clear();

                Gson gson = new Gson();
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream toWrite = exchange.getResponseBody();
                String json = gson.toJson(result);


                writeString(json, toWrite);

                toWrite.close();

            } else { //if not "POST"
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        } catch (IOException | DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }

}
