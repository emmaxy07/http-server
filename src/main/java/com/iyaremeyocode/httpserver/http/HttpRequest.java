package com.iyaremeyocode.httpserver.http;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest extends HttpMessage {
    private HttpMethod method;
    private String requestTarget;
    private String originalHttpVersion;
    private HttpVersion bestCompatibleHttpVersion;
    private HashMap<String, String> headers;



    HttpRequest(){

    }

    public HttpMethod getMethod(){
        return method;
    }

    public String getRequestTarget() {
        return requestTarget;
    }

    public HttpVersion getBestCompatibleHttpVersion() {
        return bestCompatibleHttpVersion;
    }

    public String getOriginalHttpVersion() {
        return originalHttpVersion;
    }

    public HashMap<String, String> getHeaders(){
        return new HashMap<>(headers);
    }

    void setMethod(String httpMethodName) throws HttpParsingException {
        for (HttpMethod method: HttpMethod.values()){
            if(httpMethodName.equals(method.name())){
                this.method = method;
                return;
            }
        }
        throw new HttpParsingException(
                HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED
        );
    }

    void setRequestTarget(String requestTarget) throws HttpParsingException {
        if(requestTarget == null || requestTarget.isEmpty()){
            throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_500_INTERNAL_SERVER_ERROR);
        }
        this.requestTarget = requestTarget;
    }

     void setOriginalHttpVersion(String originalHttpVersion) throws BadHttpVersionException, HttpParsingException {
        this.originalHttpVersion = originalHttpVersion;
        this.bestCompatibleHttpVersion = HttpVersion.getBestCompatibleVersion(originalHttpVersion);
        if(this.bestCompatibleHttpVersion == null){
            throw new HttpParsingException(
                    HttpStatusCode.SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED
            );
        }
    }

    void setHeaders(HashMap<String, String> headers) throws HttpParsingException{
        this.headers = headers;
        if(this.headers == null){
            throw new HttpParsingException(
                    HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST
            );
        }
    }
}
