/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paint;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Handles writing user actions to a log file
 * 
 * @author tanya
 */
public class PaintLogger {
   
    // name of file where actions are saved
    private static final String LOG_FILE = "paint_log.text";
    
    /** 
     * Writes an action to the log file using another thread
     * 
     * @param action what the user did
     */
    
    public static void log(String action) {
        
        // make logging run seperately from the main program
        Thread logThread = new Thread(() -> {
            
            //get current date and time
            LocalDateTime now = LocalDateTime.now();
            
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
            
            String timeStamp = now.format(formatter);
            
            // get current file name
            String fileName = "new tab";
            
            if (GuiImage.currentFile != null) {
                fileName = GuiImage.currentFile.getName();
      
            }
            
            //create one line for the log
            String logLine = 
                    timeStamp
                    +" ["
                    + fileName
                    +" ]"
                    + action
                    + System.lineSeparator();
            
            
            try {
                
                // true means add to bottom instead of replacing file
                FileWriter writer = new FileWriter(LOG_FILE, true);
                
                writer.write(logLine);
                writer.close();
                
            }catch (IOException e) {
                
                System.out.println("Error writing to log file");
                
            }
        });
        
        //start logging thread
        logThread.start();
    }
}

