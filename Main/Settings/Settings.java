/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Settings;

import java.util.List;

import ordinalpreferencegenerator.iOrdinalIterator;
import Main.MainBruteForce;
import Main.Observers.AlgorithmObserver;
import Main.Observers.LogWriter;
import Main.Observers.PreferenceOrder;
import Main.Observers.PreferencesCounter;
import Main.Observers.Timer;
import Main.Observers.iResultsCollator;
import Main.Observers.BordaRelated.BordaScoreRaw;
import Main.Observers.BordaRelated.PluralityScoreRaw;
import Main.Observers.OrdinalEfficiency;
import Main.Observers.RankEfficiency;
import MatchingAlgorithm.ProbabilisticSerialRule;
import MatchingAlgorithm.Proportional;
import MatchingAlgorithm.iAlgorithm;
import MatchingAlgorithm.DeterministicAlgorithm.AdaptiveBoston;
import MatchingAlgorithm.DeterministicAlgorithm.HungarianAlgorithmWrapper;
import MatchingAlgorithm.DeterministicAlgorithm.NaiveBoston;
import MatchingAlgorithm.DeterministicAlgorithm.RandomSerialDictatorship;
import MatchingAlgorithm.DeterministicAlgorithm.YankeeSwap.YankeeSwapStandard;
import MatchingAlgorithm.DeterministicAlgorithm.YankeeSwap.YankeeSwapStandardWithGTTC;
import MatchingAlgorithm.Hybrid.PSRYSGTTC;
import MatchingAlgorithm.Hybrid.RSDPSR;
import MatchingAlgorithm.Hybrid.RSDYSGTTC;
import ordinalpreferencegenerator.IAC;
import ordinalpreferencegenerator.IANC;
import ordinalpreferencegenerator.IC;
import ordinalpreferencegenerator.Mallows;
//import MatchingAlgorithm.Gurobi.*;

/**
 *
 * @author ylo019
 */
public abstract class Settings {
    
    //algorithms to test
    public static final Class<? extends iResultsCollator>[] collators = new Class[]{
            LogWriter.class,
            PreferencesCounter.class,
            PreferenceOrder.class,
//            BordaPercentageOfMax.class,
//            BordaWorstAgentToRank.class,
//            NashUnweighted.class,
//            EnvyFreeProfilesCount.class,
//            EnvyPairsCounter.class,
//            WeaklyEnvyFreeProfilesCount.class,
//            WeaklyEnvyPairsCount.class,
            OrdinalEfficiency.class,
//            RankEfficiency.class,
//            SDProportionalProfileCount.class,
//            SDProportionalityAgentCount.class,
//            WeaklySDProportionalProfileCount.class,
//            WeaklySDProportionalityAgentCount.class,
//            LaTeXTablePrinter.class, //this has to be after everything that produce a MessageType.SUMMARY message. so that it gets the SYSTEM."End Size" message last
            
            BordaScoreRaw.class,
            PluralityScoreRaw.class,
            Timer.class,
//            PreflibFormatWriter.class
    };
    public static final Class<? extends iAlgorithm>[] classesWithIntParam = new Class[] {
//            NStealPerItem.class,
//            NStealPerItemWithGTTC.class,
    };
    public static final Class<? extends iAlgorithm>[] classes = new Class[]{
//            HungarianAlgorithmWrapper.class,
//            Proportional.class,
//            ProbabilisticSerialRule.class, 
//            RandomSerialDictatorship.class, 
//            AdaptiveBoston.class, 
//            NaiveBoston.class,
            YankeeSwapStandard.class, 
            YankeeSwapStandardWithGTTC.class,
//            RSDPSR.class,
//            RSDYSGTTC.class,
//            PSRYSGTTC.class,
    };
    
    public static void init() {
        for (Class<? extends iResultsCollator> cl : collators) {
            try {
                cl.newInstance().init();
            } catch (InstantiationException | IllegalAccessException ex) {
                return;
            }
        }
//        OrdinalPreferenceIterator op = OrdinalPreferenceRandomiser.getRandomiser(10, 300);
        for (Class<? extends iAlgorithm> cl : classes) {
            new AlgorithmObserver(cl).init();
        }
        for (Class<? extends iAlgorithm> cl : classesWithIntParam) {
            //for (int i = 1; i <= 5; i++) {
                new AlgorithmObserver(cl, 2).init();
            //}
        }
        
        System.out.println(PATH);
    }
    
    public static final int RUN = (int) (Math.random() * Integer.MAX_VALUE);
    public static final double DOUBLE_PRECISION = Math.pow(10, -12);
    public static final String DOUBLE_OUTPUT = "%3.6f";
    public static final String INTEGER_OUTPUT = "%2d";
    
    public static final boolean FIXED_ORDER_FOR_ALGORITHM = false; //set to true for large value of n to only do 1 permutation
    
    //main method location & number of agents/items
    public static final Class APP_CLASS = MainBruteForce.class;
    public static final Class<? extends iOrdinalIterator> ORDINAL_PREFERENCE = IANC.class; //Mallows.class;
    public static final double PREF_PARAM = 0.9999;
    public static final int M = 3; //5
    public static final int N = 4; //100
    public static final int INCREMENT = 1; //5
    public static final double RUN_CHANCE = 1;
    public static final int PROFILE_COUNT = 1000; //use 10000 for real runs
    public static final String PATH = "141116/OneOff/";//"290716/Mallows10/";
    public static final String DATA_SAVE_PATH = null; //"randomDataPackTest/real/Pack1/";
    
    public static String DoubleToString(Double d) {
        return String.format(DOUBLE_OUTPUT, d);
    }
    public static String IntToString(Integer i) {
        return String.format(INTEGER_OUTPUT, i);
    }
    
    public static String ListToString(List l) {
        String s = "";
        for (Object o : l) {
            if (!s.equals(""))
                s += ", ";
            s += format(o);
        }
        return s;
    }
    
    public static String format(Object o) {
        if (o instanceof Double) {
            return DoubleToString((Double)o);
        } else if (o instanceof Integer) {
            return IntToString((Integer)o);
        } else if (o instanceof List) {
            return ListToString((List)o);
        }
        return o.toString();
    }
    
    public static boolean doubleEqual(double a, double b) {
        return Math.abs(a - b) < DOUBLE_PRECISION;
    }
}
