/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.File;

import Main.Observers.System.IO;
import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import Main.Settings.Settings;
import Main.Settings.iAppClass;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import Pair.Pair;
import PrefLibDataReader.PrefLibLoader;

/**
 *
 * @author ylo019
 */
public class MainLoopingDatafile  implements iAppClass {

    public MainLoopingDatafile() {

    }
    //private final Class[] classes = {};
    @Override
    public void start() {
        
        IO.getConsole();
//        Settings.init();
        IO.getConsole(); //??why calling it twice??
        
        for (File e : new File("randomDataPackTest/real/Pack1/").listFiles()) {
            PostBox.broadcast(MessageType.PRINT, e.getName());
            for (File f : e.listFiles()) {
                System.out.println(f.getName());
                PrefLibLoader op = PrefLibLoader.load(f);
                int casesRemaining = 1;
                while (casesRemaining > 0) {
                    PreferenceProfile next = op.readFromStart();
                    //print priority
                    PostBox.broadcast(MessageType.PREFERENCE, new Pair<>(next, true));
                    PostBox.broadcast(MessageType.SYSTEM, new Pair<>("End Preference", ""));
                    --casesRemaining;
                }
            }
        PostBox.broadcast(MessageType.SYSTEM, new Pair<>("End Size", ""));
        }
        PostBox.broadcast(MessageType.SYSTEM, new Pair<>("End Calculation", ""));
        IO.getConsole().close();
    }
    
}
