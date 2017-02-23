/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Settings;

import ordinalpreferencegenerator.iOrdinalIterator;
import Main.Observers.System.CsvLog;
import Main.Observers.System.IO;
import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import Pair.Pair;

/**
 *
 * @author ylo019
 */
public class MainBruteForce implements iAppClass {

    public MainBruteForce() {
    }
    
    @Override
    public void start() {
        
        IO.getConsole();
        //CsvLog.getConsole();
        Configurations.getConfigurations().init();
        //Settings.init();
        do {
            PostBox.broadcast(MessageType.PRINT, Configurations.getConfigurations().peekIterator().getName());
            PostBox.broadcast(MessageType.PRINT, "Beginning of Iterator " + Configurations.getConfigurations().peekIterator().getName());
            iOrdinalIterator op = Configurations.getConfigurations().peekIterator();
            int ii = 0;
            while (op.hasNext()) {
                PreferenceProfile next = op.getNext();
                PostBox.broadcast(MessageType.PREFERENCE, new Pair<>(next, true));
                PostBox.broadcast(MessageType.SYSTEM, new Pair<>("End Preference", ""));
                System.out.println(++ii);
            }
            PostBox.broadcast(MessageType.PRINT, "End of size");
            PostBox.broadcast(MessageType.SYSTEM, new Pair<>("End Size",""));
        } while (Configurations.getConfigurations().nextIterator());
            
        PostBox.broadcast(MessageType.PRINT, "End of calculation");
        PostBox.broadcast(MessageType.SYSTEM, new Pair<>("End Calculation", ""));
        IO.getConsole().close();
        //CsvLog.getConsole().close();
    }
    
}
