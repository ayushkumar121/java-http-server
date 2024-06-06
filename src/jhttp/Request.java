package src.jhttp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Request {
    public String method;
    public String path;
    public Map<String, String[]> headers;
    public InputStreamReader body;

    public Request() {}

    public static Request fromSocket(Socket client) throws IOException,Exception {
        Request r = new Request();
        r.headers= new HashMap<>();

        InputStreamReader streamReader =  new InputStreamReader(client.getInputStream());
        BufferedReader reader = new BufferedReader(streamReader);

        String statusLine = reader.readLine();
        if (statusLine == null || statusLine.isEmpty()) {
            throw new Exception("Invalid HTTP request");
        }

        String[] chunks = statusLine.split(" ");
        if (chunks.length != 3) {
            throw new Exception("Invalid HTTP request");
        }

        r.method = chunks[0];
        r.path = chunks[1];

        String line = reader.readLine();
        while(!line.isBlank()) {
            String[] h = line.split(":", 2);
            if (h.length != 2) {
                throw new Exception("Invalid HTTP headers");
            }

            String key = h[0];
            String value =  h[1].trim();
            
            if (r.headers.containsKey(key)) {
                String[] values = r.headers.get(key);
                String[] updatedValues = new String[values.length+1];
                System.arraycopy(values, 0, updatedValues, 0, values.length);
                updatedValues[updatedValues.length-1] = value;

                r.headers.put(key, updatedValues);
            } else {
                r.headers.put(key, new String[]{value});
            }

            line = reader.readLine();
        }

        r.body = streamReader;

        return r;
    }
}
