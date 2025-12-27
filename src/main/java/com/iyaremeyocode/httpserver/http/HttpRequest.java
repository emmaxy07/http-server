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

    public String getRequestTarget() {
        return requestTarget;
    }

    void setRequestTarget(String requestTarget) throws HttpParsingException {
        if(requestTarget == null || requestTarget.isEmpty()){
            throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_500_INTERNAL_SERVER_ERROR);
        }
        this.requestTarget = requestTarget;
    }
}
