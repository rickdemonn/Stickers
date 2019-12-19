import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.LibraryLoader;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.math.BigDecimal;
import java.security.AccessController;
import java.security.PrivilegedAction;

@Slf4j
public class WeightService {


    public String getWeight() {
       // @SuppressWarnings({"rawtypes" })
       //BigDecimal res = (BigDecimal) AccessController.doPrivileged(new PrivilegedAction() {
       //     public Object run() {
                Dispatch scale = null;
                try {
                    System.setProperty(LibraryLoader.JACOB_DLL_PATH, "C:\\lib\\jacob-1.14.3-x86.dll");

                    ActiveXComponent sca = new ActiveXComponent("CAScentre_DLL_printScale.Scale");
                    scale = sca.getObject();
                    log.info("Connecting...");
                    Dispatch.put(scale,"IP","COM1");
                    Dispatch.put(scale,"Port",9600);
                    Dispatch.put(scale,"Type",0);
                    Dispatch.call(scale, "Open");
                    String res = Dispatch.get(scale,"ResultCode").toString();
                    log.info("Result: " + res);
                    String weightStr = Dispatch.get(scale, "statusWeight").toString();
                    log.info("weightStrAsIs: " + weightStr);
                    Dispatch.call(scale,"ReadCurrentStatus");
                    String weigth = Dispatch.get(scale,"statusWeight").getString();
                    log.info("weight: "+ weigth);

                    return weightStr;
                } catch (Throwable e) {
                    e.printStackTrace();
                    return "0";
                } finally {
                    if (scale != null) {
                        try {
                            Dispatch.call(scale, "Close");
                        } catch (Throwable e2) {
                        }
                    }
                }
            }
//        });
//
//        log.info("return " + res.toPlainString());
//        return res.toPlainString();
//    }
}
