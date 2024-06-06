package src.jhttp;

import java.util.HashMap;
import java.util.Map;

public class ServerConfig {
    public int maxConnections;
    public int port;
    public Map<String, Handler> routes;

    public ServerConfig() {
        maxConnections = 4;
        port = 8080;
        routes = new HashMap<>();
    }

    public ServerConfig setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
        return this;
    }

    public ServerConfig setPort(int port) {
        this.port = port;
        return this;
    }

    public ServerConfig addRoute(String route, Handler handler ) {
        this.routes.put(route, handler);
        return this;
    }
}
