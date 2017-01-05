/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Settings;

import java.util.List;

import ordinalpreferencegenerator.iOrdinalIterator;
import Main.MainBruteForce;
import Main.Observers.*;
import Main.Observers.BordaRelated.*;
import Main.Observers.BordaRelated.NashUtility.NashUnweighted;
import Main.Observers.Envy.EnvyFreeProfilesCount;
import Main.Observers.Envy.EnvyPairsCounter;
import Main.Observers.Envy.WeaklyEnvyFreeProfilesCount;
import Main.Observers.Envy.WeaklyEnvyPairsCount;
import Main.Observers.Proportionality.SDProportionalProfileCount;
import Main.Observers.Proportionality.SDProportionalityAgentCount;
import Main.Observers.Proportionality.WeaklyProportional.WeaklySDProportionalProfileCount;
import Main.Observers.Proportionality.WeaklyProportional.WeaklySDProportionalityAgentCount;
import MatchingAlgorithm.*;
import MatchingAlgorithm.DeterministicAlgorithm.*;
import MatchingAlgorithm.DeterministicAlgorithm.YankeeSwap.*;
import MatchingAlgorithm.DeterministicAlgorithm.YankeeSwap.XStealPerItem.NStealPerItem;
import MatchingAlgorithm.DeterministicAlgorithm.YankeeSwap.XStealPerRound.NStealPerRound;
import MatchingAlgorithm.Hybrid.*;
import MatchingAlgorithm.Taxonomy.*;
import ordinalpreferencegenerator.*;
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
            BordaPercentageOfMax.class,
//            BordaWorstAgentToRank.class,
//            NashUnweighted.class,
//            EnvyFreeProfilesCount.class,
//            EnvyPairsCounter.class,
//            WeaklyEnvyFreeProfilesCount.class,
//            WeaklyEnvyPairsCount.class,
//            OrdinalEfficiency.class,
//            RankEfficiency.class,
//            SDProportionalProfileCount.class,
//            SDProportionalityAgentCount.class,
//            WeaklySDProportionalProfileCount.class,
//            WeaklySDProportionalityAgentCount.class,
//            LaTeXTablePrinter.class, //this has to be after everything that produce a MessageType.SUMMARY message. so that it gets the SYSTEM."End Size" message last
            CompareTables.class,
            EquivalentAlgorithm.class,
            BordaScoreRaw.class,
            PluralityScoreRaw.class,
            Timer.class,
//            PreflibFormatWriter.class
    };
    public static final Class<? extends iAlgorithm>[] classesWithIntParam = new Class[] {
//            NStealPerItem.class,
//            NStealPerItemWithGTTC.class,
//            NStealPerRound.class
    };
    public static final Class<? extends iAlgorithm>[] classes = new Class[]{
            //Benchmarks
            HungarianAlgorithmWrapper.class,
            Proportional.class,
            
            //Standard Algorithms
//            ProbabilisticSerialRule.class, 
//            RandomSerialDictatorship.class, 
//            AdaptiveBoston.class, 
//            NaiveBoston.class,
            
            //Yankees
//            YankeeSwapStandard.class, 
//            YankeeSwapStandardWithGTTC.class,
            
            //Hybrids
//            RSDPSR.class,
//            RSDYSGTTC.class,
//            PSRYSGTTC.class,
            
//            //Fixed Order (memory)
//                //Accept first
//                    //stack
//                    MemoryAcceptFirstStack.class, //RSD
//                    //queue
//                    MemoryAcceptFirstQueue.class, //NB
//                    MemoryAcceptFirstQueueHasDelayedKnowledge.class, //AB
//                //Accept last
//                    //stack
//                    MemoryAcceptLastStack.class,
//                    MemoryAcceptLastStackGTTC.class,
//                    //queue
//                    MemoryAcceptLastQueue.class,
//                    MemoryAcceptLastQueueHasDelayedKnowledge.class,
//                    MemoryAcceptLastQueueGTTC.class,
//                    MemoryAcceptLastQueueHasDelayedKnowledgeGTTC.class,
//            //Dynamic Order (memoryless)
//                //Accept first
//                    //stack
//                    MemorylessAcceptFirstStack.class,
//                    MemorylessAcceptFirstStackGTTC.class,
//                    //queue
//                    MemorylessAcceptFirstQueue.class,
//                    MemorylessAcceptFirstQueueHasDelayedKnowledge.class,
//                    MemorylessAcceptFirstQueueGTTC.class,
//                    MemorylessAcceptFirstQueueHasDelayedKnowledgeGTTC.class,
//                //Accept last
//                    //stack
//                    MemorylessAcceptLastStack.class,
//                    MemorylessAcceptLastStackGTTC.class,
//                    //queue
//                    MemorylessAcceptLastQueue.class,
//                    MemorylessAcceptLastQueueGTTC.class,
//                    MemorylessAcceptLastQueueHasDelayedKnowledge.class,
//                    MemorylessAcceptLastQueueHasDelayedKnowledgeGTTC.class
            
            //temporary debug/quick test classes
//            NoMemoryQueueImplTest.class
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
        GenericImplementation.initAll();
        for (Class<? extends iAlgorithm> cl : classesWithIntParam) {
            //for (int i = 1; i <= 5; i++) {
                new AlgorithmObserver(cl, 1).init();
            //}
        }
        
        System.out.println(PATH);
    }
    
    public static final int RUN = (int) (Math.random() * Integer.MAX_VALUE);
    public static final double DOUBLE_PRECISION = Math.pow(10, -12);
    public static final String DOUBLE_OUTPUT = "%3.6f";
    public static final String INTEGER_OUTPUT = "%2d";
    
    //code for null item
    //required to be -1 for Hungarian Algorithm (unless modifying the code in HA)
    public static final int NULL_ITEM = -1;
    
    public static final boolean FIXED_ORDER_FOR_ALGORITHM = true; //set to true for large value of n to only do 1 permutation
    //if FIXED_ORDER... is false, DO NOT TRY ANYTHING bigger m or n (maximum m or n = 12). Otherwise the fact(n) will overflow
    
    //main method location & number of agents/items
    public static final Class APP_CLASS = MainBruteForce.class;
    public static final Class<? extends iOrdinalIterator> ORDINAL_PREFERENCE = Mallows.class;
    public static final double PREF_PARAM = 0.80;
    //M = minimum number of agents
    public static final int M = 40; //5
    //N = maximum number of agents
    public static final int N = 40; //100
    //O = number of objects [O = -1 to set O = current number of agents]
    public static final int O = -1;
    public static final int INCREMENT = 10; //5
    public static final double RUN_CHANCE = 1;
    public static final int PROFILE_COUNT = 10000; //use 10000 for real runs //not relevant for IC/IAC/IANC
    public static final String PATH = "141216/Output2/";//"290716/Mallows10/";
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
