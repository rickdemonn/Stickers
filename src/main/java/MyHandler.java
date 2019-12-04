import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.extern.slf4j.Slf4j;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.logging.Logger;

@Slf4j
class MyHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "All good";
        httpExchange.sendResponseHeaders(200, response.length());
        InputStreamReader isr =  new InputStreamReader(httpExchange.getRequestBody(),"utf-8");
        BufferedReader br = new BufferedReader(isr);

        int b;
        StringBuilder buf = new StringBuilder(512);
        while ((b = br.read()) != -1) {
            buf.append((char) b);
        }

        br.close();
        isr.close();

        log.info("Request body for print: " + buf);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
