package com.iyaremeyocode.httpserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpParser {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);

    private static final int SP = 0x20; // 32
    private static final int CR = 0x0D; // 13
    private static final int LF = 0x0A; // 10
    private static final int COLON = 0x3A; // 58


    public HttpRequest parseHttpRequest(InputStream inputStream) throws HttpParsingException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);

        HttpRequest httpRequest =  new HttpRequest();

        try {
            parseRequestLine(inputStreamReader, httpRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            parseHeaders(inputStreamReader, httpRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
                    if(!methodParsed || !requestTargetParsed){
                        throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                    }
                    try {
                        httpRequest.setOriginalHttpVersion(stringBuilder.toString());
                    } catch (BadHttpVersionException e) {
                        throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                    }
                } else {
                    throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                }

        }
        if(_byte == SP){
            if(!methodParsed){
                LOGGER.debug("Request line METHOD to process: {}", stringBuilder.toString());
                httpRequest.setMethod(stringBuilder.toString());
                methodParsed = true;
            } else if (!requestTargetParsed) {
                LOGGER.debug("Request line REQUEST TARGET to process: {}", stringBuilder.toString());
                httpRequest.setRequestTarget(stringBuilder.toString());
                requestTargetParsed = true;
            } else {
                throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
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

    enum State {
        NAME,
        VALUE
    }

    public void parseHeaders(InputStreamReader inputStreamReader, HttpRequest httpRequest) throws IOException {
        StringBuilder stringBuilderHeaderName = new StringBuilder();
        StringBuilder stringBuilderHeaderValue = new StringBuilder();
        StringBuilder a;
        StringBuilder b;

        String headerName = "";
        String headerValue = "";
        HashMap<String, String> headers = new HashMap<>();
        State state = State.NAME;

        int i;
        while((i = inputStreamReader.read()) >= 0){
            if(state == State.NAME){
                if(i == COLON){
                    state = State.VALUE;
                    continue;
                }
                a = stringBuilderHeaderName.append((char) i);
                headerName = String.valueOf(a);
            }
            stringBuilderHeaderName.delete(0, stringBuilderHeaderName.length());

            if(state == State.VALUE){
                if(i == CR){
                    int nextByte = inputStreamReader.read();
                    if(nextByte == LF){
                        break;
                    }
                }
                b = stringBuilderHeaderValue.append((char) i);
                headerValue = String.valueOf(b);
            }
            headers.put(headerName, headerValue);
            stringBuilderHeaderValue.delete(0, stringBuilderHeaderValue.length());
        }
    }

    public void parseBody(InputStreamReader inputStreamReader, HttpRequest httpRequest){

    }
}
