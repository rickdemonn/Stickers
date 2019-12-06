import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import lombok.extern.slf4j.Slf4j;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import java.math.BigDecimal;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;


@Slf4j
public class PrintService {

    public void makeSticker(String requestBody){
        print(requestBody);
    }

    private void print(String printerRawCommand) {
        try {
            javax.print.PrintService ps = PrintServiceLookup.lookupDefaultPrintService();
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            DocPrintJob job = ps.createPrintJob();
            byte[] bs = Arrays.copyOf(printerRawCommand.getBytes(), 1024);
            Doc doc = new SimpleDoc(bs, flavor, null);
            PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
            job.print(doc, aset);
            log.info("Print successful");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
