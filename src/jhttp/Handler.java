package src.jhttp;

public interface Handler {
    void serveHTTP(ResponseWriter w, Request request);
}
