import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class StickersListener {
    public static void runServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8082), 0);
        server.createContext("/test", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
