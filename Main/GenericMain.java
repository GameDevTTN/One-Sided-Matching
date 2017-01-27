package Main;


import Main.Settings.MainBruteForce;
import Main.Observers.AlgorithmObserver;
import Main.Observers.CompareTables;

import Main.Observers.System.*;
import Main.Observers.*;
import Main.Observers.BordaRelated.*;
import Main.Settings.Configurations;
import MatchingAlgorithm.DeterministicAlgorithm.*;
import MatchingAlgorithm.*;
import MatchingAlgorithm.Taxonomy.GenericImplementation;
import java.util.ArrayList;
import ordinalpreferencegenerator.*;

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

    private static void modifySettings() {
        Configurations c = Configurations.getConfigurations();
        c.setAlgorithms(getAlgorithms());
        c.setResultCollator(getResultCollator());
        c.setPreferenceIterator(getPreferenceProfiles());
        c.setOutput(getOutput());
    }
    
    private static MessageType[] getOutput() {
        return new MessageType[]{MessageType.PREFERENCE, 
            MessageType.ALGORITHM_NAME, 
            //MessageType.DETAILS,
            MessageType.OUTPUT, 
            MessageType.SUMMARY, 
            MessageType.SYSTEM, 
            MessageType.PRINT};
//                    PostBox.listen(singleton, MessageType.PREFERENCE);
//            PostBox.listen(singleton, MessageType.ALGORITHM_NAME);
////            PostBox.listen(singleton, MessageType.PROCESS);
////            PostBox.listen(singleton, MessageType.DETAILS);
////            PostBox.listen(singleton, MessageType.TABLE);
//            PostBox.listen(singleton, MessageType.OUTPUT);
////            PostBox.listen(singleton, MessageType.COMPARISON);
//            PostBox.listen(singleton, MessageType.SUMMARY);
//            PostBox.listen(singleton, MessageType.SYSTEM);
//            PostBox.listen(singleton, MessageType.PRINT);
//            //PostBox.listen(singleton, MessageType.NOTIFICATION);
    }
    
    private static iOrdinalIterator[] getPreferenceProfiles() {
        return new iOrdinalIterator[]{new Mallows(10000, 30, 30, 0.9f)/*new TruncatedIC(4, 4), new IC(4, 4)/*new ICRandom(100,31,31),*/ /*new Mallows(1000, 10, 10, 0.65d)*/};
    }
    //at for n = 10, at about 0.65 Mallows, NB out-performs YS
    
    
    private static AlgorithmObserver[] getAlgorithms() {
        ArrayList<AlgorithmObserver> out = new ArrayList<>();
        out.add(new AlgorithmObserver(HungarianAlgorithmWrapper.class));
        out.add(new AlgorithmObserver(Proportional.class));
        boolean fixOrder = true;
        out.addAll(GenericImplementation.fetchAll(fixOrder));
        AlgorithmObserver[] arr = new AlgorithmObserver[0];
        arr = out.toArray(arr);
        return arr;
    }
    
    private static iResultsCollator[] getResultCollator() {
        return new iResultsCollator[]{new LogWriter(), new PreferencesCounter(), new PreferenceOrder(),
            new CompareTables(), new EquivalentAlgorithm(), new BordaScoreRaw(), new BordaWorstAgentToRank(), new BordaOrderBias(), new PluralityScoreRaw(), new Timer()};
    }
    
    private GenericMain() {}
    
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        modifySettings();
        MainBruteForce mbf = new MainBruteForce();
        mbf.start();
        PostBox.broadcast(MessageType.PRINT, "Time taken: " + (System.currentTimeMillis() - start)/1000 + "s");
    }
    
}
