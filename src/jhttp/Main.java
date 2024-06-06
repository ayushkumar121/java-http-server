package src.jhttp;

class MyHandler implements Handler {

  @Override
  public void serveHTTP(ResponseWriter w, Request request) {
    w.setStatusCode(500);
  }

}

class Main {

  public static void main(String[] args) {
    ServerConfig c = new ServerConfig()
        .setMaxConnections(10)
        .setPort(8080)
        .addRoute(".*", new MyHandler());

    Server s = new Server(c);
    s.start();
  }
}
