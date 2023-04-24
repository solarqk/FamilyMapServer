package Handlers;

import DAO.DataAccessException;
import Request.LoadRequest;
import Request.RegisterRequest;
import Result.LoadResult;
import Result.RegisterResult;
import Services.UserRegisterService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class RegisterHandler extends HandlerFunctions implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                Gson gson = new Gson();

                InputStream is = exchange.getRequestBody();
                String bodyString = readString(is);

                UserRegisterService registerService = new UserRegisterService();
                RegisterRequest registerRequest = gson.fromJson(bodyString, RegisterRequest.class);
                RegisterResult registerResult = registerService.register(registerRequest);

                if (registerResult.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                String json = gson.toJson(registerResult);
                OutputStream os = exchange.getResponseBody();
                writeString(json, os);
                os.close();
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
