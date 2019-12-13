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

                    BigDecimal bd = new BigDecimal(weightStr);
                    weightStr = bd.toPlainString();
                    log.info("weightStr as BigDecimal: " + weightStr);

                    int delimiterPos = weightStr.indexOf(".", 0);
                    switch (delimiterPos) {
                        case 0:
                            weightStr = "0";
                        case 1:
                            // Иногда вес приходит как "1,000061" , что означает
                            // 0.16
                            String p1 = weightStr.substring(6, 7);
                            String p2 = weightStr.substring(0, 6);
                            weightStr = p1 + p2;
                            break;
                        case 2:
                            // А иногда как "61,00001" , что тоже означает 0.16
                            weightStr = weightStr.substring(0, 7);
                            break;
                        default:
                            break;
                    }
                    log.info("return " + weightStr);
                    return weightStr;
//                    return new BigDecimal(new StringBuilder(weightStr.replace(',', '.')).reverse().toString()).setScale(3,
//                            BigDecimal.ROUND_HALF_UP);
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
