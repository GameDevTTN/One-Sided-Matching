package Main;


import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import Main.Settings.Settings;
import Main.Settings.iAppClass;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ylo019
 */
public final class GenericMain {
    
    private GenericMain() {}
    
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        try {
            ((iAppClass)Settings.APP_CLASS.getConstructor().newInstance()).start();
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(GenericMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        PostBox.broadcast(MessageType.PRINT, "Time taken: " + (System.currentTimeMillis() - start)/1000 + "s");
    }
    
}
