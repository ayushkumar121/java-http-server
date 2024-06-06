package src.jhttp;

import java.net.Socket;
import java.io.*;

public class ResponseWriter {
    private int statusCode;
    private BufferedWriter writer;

    public ResponseWriter(Socket client) throws IOException {        
        OutputStreamWriter streamWriter = new OutputStreamWriter(client.getOutputStream());
        writer = new BufferedWriter(streamWriter);
        statusCode = 200;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void finish() throws IOException {
        String response = String.format("HTTP/1.1 %d OK\r\n\r\n", statusCode);
        writer.write(response);
        writer.close();
    }
}
