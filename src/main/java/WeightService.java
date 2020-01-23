import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.LibraryLoader;
import jssc.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.math.BigDecimal;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Slf4j
public class WeightService {

    private static SerialPort serialPort;
    private static String data = null;


    public void getWeight() throws InterruptedException, SerialPortTimeoutException, SerialPortException {
        //Передаём в конструктор имя порта
        serialPort = new SerialPort("COM3");
        try {
            //Открываем порт
            serialPort.openPort();
            //Выставляем параметры
            serialPort.setParams(SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            //Включаем аппаратное управление потоком
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN |
                    SerialPort.FLOWCONTROL_RTSCTS_OUT);
            //Устанавливаем ивент лисенер и маску
            serialPort.addEventListener(new PortReader(), SerialPort.MASK_RXCHAR);
            //Отправляем запрос устройству
            serialPort.writeString("W\n\r");
            log.info("post to com W");
        } catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }

    private static class PortReader implements SerialPortEventListener {

        public void serialEvent(SerialPortEvent event) {
            if (event.isRXCHAR() && event.getEventValue() > 0) {
                try {
                    //Получаем ответ от устройства, обрабатываем данные и т.д.
                    log.info("ready to listen weights");
                    String data = serialPort.readString(event.getEventValue());
                    log.info("response: " + data);
                    //И снова отправляем запрос
                    serialPort.writeString("W\n\r");
                } catch (SerialPortException ex) {
                    System.out.println(ex);
                }
            }
        }
    }
}