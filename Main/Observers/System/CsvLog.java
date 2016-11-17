/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers.System;

import Main.Observers.LogWriter;
import Main.Settings.Settings;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class CsvLog implements Observer {
    
    private static CsvLog singleton;
    private File output;
    private PrintWriter pw;
    private CsvLog() {
        reset();
    }
    private void reset() {
        System.out.println("starting log ");
        output = new File(Settings.PATH + "log.csv");
        try {
            if (!output.exists()) {
                if (output.getParentFile() != null) {
                    output.getParentFile().mkdirs();
                }
                output.createNewFile();
            }
            pw = new PrintWriter(new BufferedWriter(new FileWriter(output, true)));
            pw.println("run, prefId, algo, prefDist, param, util, utilities");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static CsvLog getConsole() {
        if (singleton == null) {
            singleton = new CsvLog();
            PostBox.listen(singleton, MessageType.LOG_ENTRY);
        }
        return singleton;
    }

    @Override
    public void update(Observable o, Object o1) {
        pw.write(o1 + "\n");
    }
    
    public void close() {
        pw.close();
    }
}