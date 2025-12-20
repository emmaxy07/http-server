package com.iyaremeyocode.httpserver;

import com.iyaremeyocode.httpserver.config.Configuration;
import com.iyaremeyocode.httpserver.config.ConfigurationManager;
import com.iyaremeyocode.httpserver.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);
    public static void main(String[] args) {
        System.out.println("Server starting...");

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration configuration = ConfigurationManager.getInstance().getCurrentConfiguration();

        System.out.println("Using port: " + configuration.getPort());
        System.out.println("Using port: " + configuration.getWebroot());


        ServerListenerThread serverListenerThread = null;
        try {
            serverListenerThread = new ServerListenerThread(configuration.getPort(), configuration.getWebroot());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        serverListenerThread.start();
    }
}
