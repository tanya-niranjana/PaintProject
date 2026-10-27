/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;


import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;


/**
 *Handles system notifications
 * 
 * @author tanya
 */
public class NotificationManager {
    
    private static TrayIcon trayIcon;
    
    /**
     * Sets up the system tray icon.
     * 
     */
    public static void setup() {
        
        // check if system notifications are supported
        if (!SystemTray.isSupported()) {
            return;
        }
        
        try {
            
            // create a small blank image for the tray icon
            
            Image image = new BufferedImage(16,15,BufferedImage.TYPE_INT_ARGB);
            
            trayIcon = new TrayIcon(image,"Paint");
            
            trayIcon.setImageAutoSize(true);
            
            // add icon to system tray
            SystemTray.getSystemTray().add(trayIcon);
            
        } catch (Exception e) {
            
            System.out.println("Could not create system notification");
        }
    }
    /** 
     * Shows a notification from Paint
     * 
     * @param message message shown to user
     */
    
    public static void showNotification(String message){
        if (trayIcon == null) {
            return;
        }
        
        trayIcon.displayMessage(
                "Paint",
                message,
                TrayIcon.MessageType.INFO
        );
    }
}
