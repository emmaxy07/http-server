package com.iyaremeyocode.httpserver;

import com.iyaremeyocode.httpserver.config.Configuration;
import com.iyaremeyocode.httpserver.config.ConfigurationManager;
import com.iyaremeyocode.httpserver.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) {
        LOGGER.info("Server staring...");

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration configuration = ConfigurationManager.getInstance().getCurrentConfiguration();

        LOGGER.info("Using port: " + configuration.getPort());
        LOGGER.info("Using port: " + configuration.getWebroot());


        ServerListenerThread serverListenerThread = null;
        try {
            serverListenerThread = new ServerListenerThread(configuration.getPort(), configuration.getWebroot());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        serverListenerThread.start();
    }
}
