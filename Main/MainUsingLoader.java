/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.File;

import Main.Observers.iResultsCollator;
import Main.Observers.System.IO;
import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import Main.Settings.Settings;
import MatchingAlgorithm.iAlgorithm;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import Pair.Pair;
import PrefLibDataReader.PrefLibLoader;

/**
 *
 * @author ylo019
 */
public class MainUsingLoader  {
    
//    private final Class<? extends iResultsCollator>[] collators;
//    private final Class<? extends iAlgorithm>[] classes;

    public MainUsingLoader() {
//        this.collators = Settings.collators;
//        this.classes = Settings.classes;
    }
    //private final Class[] classes = {};
    public void start() {
        
        IO.getConsole();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
        PrefLibLoader op = PrefLibLoader.load(new File("Simple Test.txt"));
        int casesRemaining = 5;
//        OrdinalPreferenceIterator op = OrdinalPreferenceRandomiser.getRandomiser(10, 300);
        //Settings.init();
        while (casesRemaining > 0) {
            PreferenceProfile next = op.getRandom();
            //print priority
            PostBox.broadcast(MessageType.PREFERENCE, new Pair<>("Preference", next));
            PostBox.broadcast(MessageType.SYSTEM, new Pair<>("Compare", ""));
            --casesRemaining;
        }
        PostBox.broadcast(MessageType.SYSTEM, new Pair<>("Finished", ""));
        IO.getConsole().close();
    }
    
}
