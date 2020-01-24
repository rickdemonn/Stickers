import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.LibraryLoader;
import jssc.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Slf4j
public class WeightService {

    private static SerialPort serialPort;
    private static String data = null;


    public String getWeight() throws InterruptedException {
        //получаем список портов
        String[] portNames = SerialPortList.getPortNames();
//        for(int i = 0; i < portNames.length; i++){
//            log.info(portNames[i]);
//        }
        //Передаём в конструктор имя порта
        serialPort = new SerialPort(portNames[portNames.length - 1]);
        try {
            //Открываем порт
            serialPort.openPort();

            //Выставляем параметры
            serialPort.setParams(SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            //Включаем аппаратное управление потоком
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_XONXOFF_IN |
                    SerialPort.FLOWCONTROL_XONXOFF_OUT);

            //Отправляем запрос устройству
            serialPort.writeString("S\r\n");
            log.info("post to com");
            Thread.sleep(100);
            log.info("ready to listen weights");

            data = serialPort.readString();
            log.info("response: " + data);
            if(data == null || data.isEmpty()){
                throw new IOException("Check power and connection on weight and restart application");
            }
            serialPort.closePort();
        } catch (SerialPortException ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
            return "Check your com port and restart application";
        } catch (IOException ex){
            log.error("Check power and connection on weight and restart application");
            ex.printStackTrace();
            return "Check power and connection on weight and restart application";
        }
        return data;
    }
}