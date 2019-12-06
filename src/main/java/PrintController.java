
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;


@Slf4j
class PrintController implements HttpHandler {
    private PrintService printService = new PrintService();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "print successful";
        httpExchange.sendResponseHeaders(200, response.length());
        InputStreamReader isr =  new InputStreamReader(httpExchange.getRequestBody(),"utf-8");
        BufferedReader br = new BufferedReader(isr);

        int b;
        StringBuilder buf = new StringBuilder(1024);
        while ((b = br.read()) != -1) {
            buf.append((char) b);
        }

        printService.makeSticker(buf.toString());

        br.close();
        isr.close();

        log.info("Request body for print: " + buf);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
