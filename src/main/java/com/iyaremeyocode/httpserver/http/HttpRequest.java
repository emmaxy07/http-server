package com.iyaremeyocode.httpserver.http;

public class HttpRequest extends HttpMessage {
    private HttpMethod method;
    private String requestTarget;
    private String httpVersion;

    HttpRequest(){

    }

    public HttpMethod getMethod(){
        return method;
    }

    void setMethod(HttpMethod httpMethod){
        this.method = method;
    }
}
