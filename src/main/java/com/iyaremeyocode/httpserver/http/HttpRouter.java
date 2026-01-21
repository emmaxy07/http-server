package com.iyaremeyocode.httpserver.http;
import com.iyaremeyocode.httpserver.http.HttpMethod;
import com.iyaremeyocode.httpserver.http.HttpRequest;
package com.iyaremeyocode.httpserver.routing;

import java.util.ArrayList;

public class HttpRouter {

    private final ArrayList<Route> routes = new ArrayList<>();

    public void register(HttpMethod method, String path, HttpHandler handler) {
        routes.add(new Route(method, path, handler));
    }

    public HttpHandler resolve(HttpRequest request) {
        for (Route route : routes) {
            if (route.getMethod() == request.getMethod()
                    && route.getPath().equals(request.getRequestTarget())) {
                return route.getHandler();
            }
        }
        return null;
    }
}
