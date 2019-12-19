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

                    ActiveXComponent sca = new ActiveXComponent("Cas_AD_AP.Scale");
                    scale = sca.getObject();
                    log.info("Connecting...");
                    Dispatch.call(scale, "Connect", "1", "0");

                    log.info("Weighting...");
                    Dispatch.call(scale, "UpdateOnlyWeight");
                    String weightStr = Dispatch.get(scale, "Weight").toString();
                    log.info("weightStrAsIs: " + weightStr);

                    log.info("return " + weightStr);
                    return weightStr;
                } catch (Throwable e) {
                    e.printStackTrace();
                    return "0";
                } finally {
                    if (scale != null) {
                        try {
                            Dispatch.call(scale, "Disconnect");
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
