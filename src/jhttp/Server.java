package src.jhttp;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Map;
import java.util.Set;

public class Server {
    private int port;
    private ServerSocket server;
    private ExecutorService executor;
    private Map<String, Handler> routes;

    private Handler getHandler(String path) {
        Set<String> keys = routes.keySet();
        for (String pattern : keys) {
            if (path.matches(pattern)) {
                return routes.get(pattern);
            }
        }

        return null;
    }

    public Server(ServerConfig config) {
        executor = Executors.newFixedThreadPool(config.maxConnections);
        port = config.port;
        routes = config.routes;
    }

    public void start() {
        try {
            server = new ServerSocket(port);
            server.setReuseAddress(true);

            while (true) {
                Socket client = server.accept();
                executor.submit(() -> {
                    try {
                        Request r = Request.fromSocket(client);
                        ResponseWriter w = new ResponseWriter(client);

                        Handler handler = getHandler(r.path);
                        if (handler != null) {
                            handler.serveHTTP(w, r);
                        } else {
                            w.setStatusCode(404);
                        }
                        w.finish();

                    } catch (Exception e) {
                        Logger.getInstance().Error("Unable to handle client: %s", e.getMessage());
                    }
                });
            }
        } catch (IOException e) {
            Logger.getInstance().Fatal("Cannot start server: %s", e.getMessage());
        } catch (Exception e) {
            Logger.getInstance().Error("Unexpected error: %s", e.getMessage());
        }

        // Cleanup
        executor.shutdown();
    }
}
