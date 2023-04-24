package Handlers;

import DAO.DataAccessException;
import Request.LoadRequest;
import Result.LoadResult;
import Services.LoadService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class LoadHandler extends HandlerFunctions implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                Gson gson = new Gson();

                InputStream is = exchange.getRequestBody();
                String bodyString = readString(is);

                LoadService loadService = new LoadService();
                LoadRequest loadRequest = gson.fromJson(bodyString, LoadRequest.class);
                LoadResult loadResult = loadService.load(loadRequest);

                if (loadResult.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }

                String json = gson.toJson(loadResult);
                OutputStream os = exchange.getResponseBody();
                writeString(json, os);
                os.close();
            } else {
                // We expected a POST but got something else, so we return a "bad request"
                // status code to the client.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }
        } catch (DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            LoadResult loadResult = new LoadResult("Error: Error with server.", false);
            Gson gson = new Gson();
            String json = gson.toJson(loadResult);
            OutputStream os = exchange.getResponseBody();
            writeString(json, os);
            os.close();
            e.printStackTrace();
        }
    }
}
