package Handlers;

import Request.LoginRequest;
import Result.LoadResult;
import Result.LoginResult;
import Services.UserLoginService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class LoginHandler extends HandlerFunctions implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                Gson gson = new Gson();

                InputStream is = exchange.getRequestBody();
                String bodyString = readString(is);

                UserLoginService loginService = new UserLoginService();
                LoginRequest loginRequest = gson.fromJson(bodyString, LoginRequest.class);
                LoginResult loginResult = loginService.login(loginRequest);

                if (loginResult.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                } else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }

                String json = gson.toJson(loginResult);
                OutputStream os = exchange.getResponseBody();
                writeString(json, os);
                os.close();
            }
        } catch (Exception e) {
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
