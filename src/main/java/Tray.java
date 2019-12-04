import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

@Slf4j
public class Tray {

   public void startApp() {
        if(!SystemTray.isSupported()){
            System.out.println("System tray is not supported !!! ");
            return ;
        }
        //get the systemTray of the system
        SystemTray systemTray = SystemTray.getSystemTray();

        //get default toolkit
        //Toolkit toolkit = Toolkit.getDefaultToolkit();
        //get image
        //Toolkit.getDefaultToolkit().getImage("src/resources/busylogo.jpg");
        Image image = new ImageIcon(this.getClass().getResource("/images/inbox.png")).getImage();

        //popupmenu
        PopupMenu trayPopupMenu = new PopupMenu();

        //1t menuitem for popupmenu
        MenuItem action = new MenuItem("Info");
        action.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Stickers v1.0");
            }
        });
        trayPopupMenu.add(action);

        //2nd menuitem of popupmenu
        MenuItem close = new MenuItem("Close");
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                log.info("Stickers shutdown");
                System.exit(0);
            }
        });
        trayPopupMenu.add(close);

        //setting tray icon
        TrayIcon trayIcon = new TrayIcon(image, "Stickers", trayPopupMenu);
        //adjust to default size as per system recommendation
        trayIcon.setImageAutoSize(true);

        try{
            systemTray.add(trayIcon);
        }catch(AWTException awtException){
            awtException.printStackTrace();
        }
        log.info("Stickers Started");
        try {
            StickersListener.runServer();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}