package com.iyaremeyocode.httpserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
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
    HashMap<String, String> headers = new HashMap<>();


    public HttpRequest parseHttpRequest(InputStream inputStream) throws HttpParsingException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);

        HttpRequest httpRequest =  new HttpRequest();

        try {
            parseRequestLine(inputStreamReader, httpRequest);
        } catch (IOException e) {
            throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }

        try {
            parseHeaders(inputStreamReader, httpRequest) ;
        } catch (IOException e) {
            throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
        String contentLength = headers.get("Content-Length");
        if(contentLength != null){
            try {
                parseBody(inputStream, httpRequest);
            } catch (Exception e) {
                throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
            }
        }
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

    public void parseHeaders(InputStreamReader inputStreamReader, HttpRequest httpRequest) throws IOException, HttpParsingException {
        StringBuilder stringBuilderHeaderName = new StringBuilder();
        StringBuilder stringBuilderHeaderValue = new StringBuilder();
        boolean seenCR = false;
        State state = State.NAME;

        int i;
        while((i = inputStreamReader.read()) >= 0){
            if(seenCR && i == LF && state == State.NAME && stringBuilderHeaderName.isEmpty()){
                break;
            }

            if(state == State.NAME){
                if(i == COLON){
                    state = State.VALUE;
                } else if(i == CR){
                    seenCR = true;
                } else if (i != LF) {
                    stringBuilderHeaderName.append((char) i);
                    seenCR = false;
                }
            } else if(state == State.VALUE){
                if(i == CR){
                    seenCR = true;
                } else if (seenCR && i == LF) {
                    headers.put(stringBuilderHeaderName.toString().trim(), stringBuilderHeaderValue.toString().trim());
                stringBuilderHeaderName.setLength(0);
                stringBuilderHeaderValue.setLength(0);
                state = State.NAME;
                } else {
                    stringBuilderHeaderValue.append((char) i);
                    seenCR = false;
                }
            }
        }
        httpRequest.setHeaders(headers);
    }

    public void parseBody(InputStream inputStream, HttpRequest httpRequest) throws IOException, HttpParsingException {
        String contentLength = headers.get("Content-Length");
        if(contentLength == null){
            return;
        }

        int parsedContentLengthValue = Integer.parseInt(contentLength);
        byte[] byteArray = new byte[Math.min(8192, parsedContentLengthValue)];

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int totalBytesRead = 0;
        int j;
        while (totalBytesRead < parsedContentLengthValue){
            int remainingBytesToBeRead = parsedContentLengthValue - totalBytesRead;
             j = inputStream.read(byteArray, 0, Math.min(byteArray.length, remainingBytesToBeRead));

            if(j == -1){
                throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
            }

            buffer.write(byteArray, 0, j);
            totalBytesRead += j;
        }

        httpRequest.setRequestBody(buffer.toByteArray());
    }
}
