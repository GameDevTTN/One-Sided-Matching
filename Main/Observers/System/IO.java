/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers.System;

import Main.Settings.Format;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import Main.Settings.Settings;
import Pair.Pair;

/**
 *
 * @author ylo019
 */
public class IO implements Observer {
    
    private static final int MB_128 = 1024 * 1024 * 128;
    
    private static IO singleton;
    private File output;
    private PrintWriter pw;
    private IO() {
        reset();
    }
    private void reset() {
        System.out.println("starting file " + String.format("%04d",fileIndex));
        output = new File(Settings.PATH + "output" + String.format("%04d",fileIndex) + ".txt");
        try {
            if (!output.exists()) {
                if (output.getParentFile() != null) {
                    output.getParentFile().mkdirs();
                }
                output.createNewFile();
            }
            pw = new PrintWriter(output);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void reset(String path) {
        output = new File(path);
        System.out.println("starting file " + output.getName() + ".txt");
        try {
            if (!output.exists()) {
                if (output.getParentFile() != null) {
                    output.getParentFile().mkdirs();
                }
                output.createNewFile();
            }
            pw = new PrintWriter(output);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    public static IO getConsole() {
        if (singleton == null) {
            singleton = new IO();
            PostBox.listen(singleton, MessageType.PREFERENCE);
            PostBox.listen(singleton, MessageType.ALGORITHM_NAME);
//            PostBox.listen(singleton, MessageType.PROCESS);
//            PostBox.listen(singleton, MessageType.DETAILS);
//            PostBox.listen(singleton, MessageType.TABLE);
            PostBox.listen(singleton, MessageType.OUTPUT);
//            PostBox.listen(singleton, MessageType.COMPARISON);
            PostBox.listen(singleton, MessageType.SUMMARY);
            PostBox.listen(singleton, MessageType.SYSTEM);
            PostBox.listen(singleton, MessageType.PRINT);
            //PostBox.listen(singleton, MessageType.NOTIFICATION);
        } else {
//            singleton.count = 0;
        }
        return singleton;
    }
    
    public static IO getConsole(String path) {
        getConsole();
        singleton.reset(path);
        return singleton;        
    }
//    private int count = 0;
    private int fileIndex = 0;
    @Override
    public void update(Observable o, Object o1) {
        if (output != null && output.length() >= MB_128) { //128mb
            incrementFileIndex();
        }
//        if (((PostBox)o).getSource() == MessageType.PREFERENCE) {
//            if (count >= 5000) {
//                count -= 5000;
//                incrementFileIndex();
//            }
//            ++count;
//        }
        if (o1 instanceof Pair) {
            Pair p = (Pair)o1;
            if (p.getS() instanceof String) {
                if (!p.getS().equals("") && !p.getT().equals(""))
                    pw.write(Format.Format(p.getS()) + ":\n");
                    //System.out.println(Settings.format(p.getS()) + ":");
                if (!p.getT().equals(""))
                    pw.write(Format.Format(p.getT()) + "\n");
                    //System.out.println(Settings.format(p.getT()));
            } else {
                pw.write(Format.Format(p.getS()));
            }
        } else {
            pw.write(o1 + "\n");
        }
    }
    
    private void incrementFileIndex() {
        ++fileIndex;
        close();
        reset();
    }
    
    public void close() {
        pw.close();
    }
}
