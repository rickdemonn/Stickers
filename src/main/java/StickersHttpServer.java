import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StickersHttpServer  {
    public static void runServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8082), 0);
        server.createContext("/print", new PrintController());
        server.createContext("/getweight" , new WeightController());
        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
