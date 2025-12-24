package com.iyaremeyocode.httpserver.http;

public enum HttpStatusCode {

    CLIENT_ERROR_400_BAD_REQUEST(400, "Bad Request"),
    CLIENT_ERROR_401_METHOD_NOT_ALLOWED(401, "Method not allowed"),
    CLIENT_ERROR_414_BAD_REQUEST(414, "URI too long"),

    SERVER_ERROR_500_BAD_REQUEST(500, "Internal server error"),
    SERVER_ERROR_501_BAD_REQUEST(501, "Not implemented");


    public final int STATUS_CODE;
    public final String MESSAGE;

    HttpStatusCode(int STATUS_CODE, String MESSAGE) {
        this.STATUS_CODE = STATUS_CODE;
        this.MESSAGE = MESSAGE;
    }
}
