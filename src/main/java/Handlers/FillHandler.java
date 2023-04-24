package Handlers;

import DAO.DataAccessException;
import Request.FillRequest;
import Result.FillResult;
import Result.LoadResult;
import Services.FillService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class FillHandler extends HandlerFunctions implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        FillRequest fillRequest;

        Gson gson = new Gson();
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                FillService fillService = new FillService();

                String urlReq = exchange.getRequestURI().toString();
                StringBuilder url = new StringBuilder(urlReq);
                url.deleteCharAt(0);

                String[] arguments = url.toString().split("/");

                FillResult fillResult;
                if (arguments.length <= 1 || arguments.length > 3) {
                    fillResult = new FillResult("Error: Bad request.", false);
                }
                else {
                    String username = arguments[1];
                    int generations = 4;
                    if (arguments.length == 3) {
                        generations = Integer.parseInt(arguments[2]);
                    }
                    fillRequest = new FillRequest(username, generations);
                    fillResult = fillService.fill(fillRequest);
                }

                // Start sending the HTTP response to the client, starting with
                // the status code and any defined headers.
                if (fillResult.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }

                String json = gson.toJson(fillResult);
                OutputStream os = exchange.getResponseBody();
                writeString(json, os);
                os.close();

                //if not "POST"
            } else {
                // We expected a POST but got something else, so we return a "bad request"
                // status code to the client.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }
            exchange.getResponseBody().close();
        } catch (IOException | DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            LoadResult loadResult = new LoadResult("Error: Error with server.", false);
            gson = new Gson();
            String json = gson.toJson(loadResult);
            OutputStream os = exchange.getResponseBody();
            writeString(json, os);
            os.close();
            e.printStackTrace();
        }
    }
}