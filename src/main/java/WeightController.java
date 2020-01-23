import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import jssc.SerialPortException;
import jssc.SerialPortTimeoutException;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

@Slf4j
public class WeightController implements HttpHandler {
    private WeightService weightService = new WeightService();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = null;
        try {
            try {
//                response = weightService.getWeight();
                 weightService.getWeight();
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            log.error("InterruptedException");
            e.printStackTrace();
        } catch (SerialPortTimeoutException e) {
            e.printStackTrace();
        }
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
