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


    public HttpRequest parseHttpRequest(InputStream inputStream) throws HttpParsingException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);

        HttpRequest httpRequest =  new HttpRequest();

        try {
            parseRequestLine(inputStreamReader, httpRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        parseHeaders(inputStreamReader, httpRequest);
        parseBody(inputStreamReader, httpRequest);

        return httpRequest;
    }

    public void parseRequestLine(InputStreamReader inputStreamReader, HttpRequest httpRequest) throws IOException, HttpParsingException {
        StringBuilder stringBuilder = new StringBuilder();

        boolean methodParsed = false;
        boolean requestTargetParsed = false;

        int _byte;
        while((_byte = inputStreamReader.read()) >= 0){
            if(_byte == CR){
                _byte = inputStreamReader.read();
                if(_byte == LF){
                    return;
                }
        }
        if(_byte == SP){
            if(!methodParsed){
                LOGGER.debug("Request line METHOD to process: {}", stringBuilder.toString());
                httpRequest.setMethod(stringBuilder.toString());
                methodParsed = true;
            } else if (!requestTargetParsed) {
                LOGGER.debug("Request line REQUEST TARGET to process: {}", stringBuilder.toString());
                requestTargetParsed = true;
            }
            LOGGER.debug("Request line to process: {}", stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
        } else {
            stringBuilder.append((char) _byte);
            if(!methodParsed){
                if(stringBuilder.length() > HttpMethod.MAX_LENGTH){
                    throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
                }
            }
        }
            }
    }

    public void parseHeaders(InputStreamReader inputStreamReader, HttpRequest httpRequest){

    }

    public void parseBody(InputStreamReader inputStreamReader, HttpRequest httpRequest){

    }
}
