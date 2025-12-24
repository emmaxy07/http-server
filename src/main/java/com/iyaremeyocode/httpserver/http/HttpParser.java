package com.iyaremeyocode.httpserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpParser {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);

    private static final int SP = 0x20; // 32
    private static final int CR = 0x0D; // 13
    private static final int LF = 0x0A; // 10


    public HttpRequest parseHttpRequest(InputStream inputStream){
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);

        HttpRequest httpRequest =  new HttpRequest();

        parseRequestLine(inputStreamReader, httpRequest);
        parseHeaders(inputStreamReader, httpRequest);
        parseBody(inputStreamReader, httpRequest);

        return httpRequest;
    }

    public void parseRequestLine(InputStreamReader inputStreamReader, HttpRequest httpRequest) throws IOException {
        int _byte;
        while((_byte = inputStreamReader.read()) >= 0){
            if(_byte == CR){
                _byte = inputStreamReader.read();
                if(_byte == LF){
                    return;
                }
            }
        }
    }

    public void parseHeaders(InputStreamReader inputStreamReader, HttpRequest httpRequest){

    }

    public void parseBody(InputStreamReader inputStreamReader, HttpRequest httpRequest){

    }
}
