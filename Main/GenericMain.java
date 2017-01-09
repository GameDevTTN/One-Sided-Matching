package Main;


import Main.Settings.MainBruteForce;
import Main.Observers.AlgorithmObserver;
import Main.Observers.CompareTables;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

import Main.Observers.System.*;
import Main.Observers.*;
import Main.Observers.BordaRelated.*;
import Main.Settings.Configurations;
import Main.Settings.Settings;
import Main.Settings.iAppClass;
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
        c.setPreferenceIterator(new iOrdinalIterator[]{new IANC(3,3), new IAC(3,3)});
    }
    
    private static AlgorithmObserver[] getAlgorithms() {
        ArrayList<AlgorithmObserver> out = new ArrayList<>();
        out.add(new AlgorithmObserver(HungarianAlgorithmWrapper.class));
        out.add(new AlgorithmObserver(Proportional.class));
        out.addAll(GenericImplementation.fetchAll());
        AlgorithmObserver[] arr = new AlgorithmObserver[0];
        arr = out.toArray(arr);
        return arr;
    }
    
    private static iResultsCollator[] getResultCollator() {
        return new iResultsCollator[]{new LogWriter(), new PreferencesCounter(), new PreferenceOrder(), new BordaPercentageOfMax(),
            new CompareTables(), new EquivalentAlgorithm(), new BordaScoreRaw(), new PluralityScoreRaw(), new Timer()};
    }
    
    private GenericMain() {}
    
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        modifySettings();
        MainBruteForce mbf = new MainBruteForce();
        mbf.start();mbf.start();
        PostBox.broadcast(MessageType.PRINT, "Time taken: " + (System.currentTimeMillis() - start)/1000 + "s");
    }
    
}
