package Main;


import Main.Settings.MainBruteForce;
import Main.Observers.OneSidedAlgorithmObserver;
import Main.Observers.CompareTables;

import Main.Observers.System.*;
import Main.Observers.*;
import Main.Observers.BordaRelated.*;
import Main.Observers.Envy.*;
import Main.Observers.Proportionality.*;
import Main.Observers.UtilitiesRelated.CustomUtilityModelRaw;
import Main.Observers.UtilitiesRelated.ExponentialUtilityPercentageOfMax;
import Main.Observers.UtilitiesRelated.ExponentialUtilityRaw;
import Main.Settings.Configurations;
import MatchingAlgorithm.DeterministicAlgorithm.*;
import MatchingAlgorithm.*;
import MatchingAlgorithm.Auxiliary.Restrictions.RandomRestriction;
import MatchingAlgorithm.Auxiliary.Restrictions.RestrictionFactoryAdaptor;
import MatchingAlgorithm.Auxiliary.Restrictions.iRestriction;
import MatchingAlgorithm.Auxiliary.Restrictions.iRestrictionFactory;
import MatchingAlgorithm.ItemProposingAlgorithms.BordaAtCostForMisalloc;
import MatchingAlgorithm.ItemProposingAlgorithms.BordaAtDecreasingAverage;
import MatchingAlgorithm.ItemProposingAlgorithms.BordaAtIncreasingAverage;
import MatchingAlgorithm.Taxonomy.GenericImplementation;
import UtilityModels.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        return new MessageType[]{
//            MessageType.PREFERENCE, 
//            MessageType.ALGORITHM_NAME, 
//            MessageType.DETAILS,
//            MessageType.OUTPUT, 
            MessageType.SUMMARY, 
            MessageType.SYSTEM, 
            MessageType.PRINT};

    }
    
    private static iOrdinalIterator[] getPreferenceProfiles() {
//        return new iOrdinalIterator[]{new Mallows(10000, 20, 20, 0.2f)}; //no
        return new iOrdinalIterator[]{new IC(3, 3), new ICRandom(100000, 4, 4), new ICRandom(100000, 5, 5), new ICRandom(50000, 6, 6), new ICRandom(50000, 7, 7), new ICRandom(50000, 8, 8), new ICRandom(50000, 9, 9)};
//        return new iOrdinalIterator[]{new Mallows(100000, 4, 4, 0.2f), new Mallows(100000, 5, 5, 0.2f), new Mallows(50000, 6, 6, 0.2f), new Mallows(50000, 7, 7, 0.2f)};
//        return new iOrdinalIterator[]{new IC(3, 3), new ICRandom(10000, 4, 4), new ICRandom(10000, 5, 5)}; //no
//        return new iOrdinalIterator[]{new ICRandom(50000, 10), new ICRandom(50000, 15), new ICRandom(50000, 20), new ICRandom(50000, 25), new ICRandom(50000, 30), new ICRandom(50000, 35), new ICRandom(50000, 40), new ICRandom(50000, 45), new ICRandom(50000, 50)};
//        return new iOrdinalIterator[]{new Mallows(100000, 5, 5, 0f),new Mallows(100000, 5, 5, 0.1f),new Mallows(100000, 5, 5, 0.2f), new Mallows(100000, 5, 5, 0.3f), new Mallows(100000, 5, 5, 0.4f),
//                                    new Mallows(100000, 5, 5, 0.5f), new Mallows(100000, 5, 5, 0.6f), new Mallows(100000, 5, 5, 0.7f), new Mallows(100000, 5, 5, 0.8f), new Mallows(100000, 5, 5, 0.9f),
//                                    new Mallows(100000, 10, 10, 0f),new Mallows(100000, 10, 10, 0.1f),new Mallows(100000, 10, 10, 0.2f), new Mallows(100000, 10, 10, 0.3f), new Mallows(100000, 10, 10, 0.4f),
//                                    new Mallows(100000, 10, 10, 0.5f), new Mallows(100000, 10, 10, 0.6f), new Mallows(100000, 10, 10, 0.7f), new Mallows(100000, 10, 10, 0.8f), new Mallows(100000, 10, 10, 0.9f),
//                                    new Mallows(100000, 15, 15, 0f),new Mallows(100000, 15, 15, 0.1f),new Mallows(100000, 15, 15, 0.2f), new Mallows(100000, 15, 15, 0.3f), new Mallows(100000, 15, 15, 0.4f),
//                                    new Mallows(100000, 15, 15, 0.5f), new Mallows(100000, 15, 15, 0.6f), new Mallows(100000, 15, 15, 0.7f), new Mallows(100000, 15, 15, 0.8f), new Mallows(100000, 15, 15, 0.9f),
//                                    new Mallows(100000, 20, 20, 0f),new Mallows(100000, 20, 20, 0.1f),new Mallows(100000, 20, 20, 0.2f), new Mallows(100000, 20, 20, 0.3f), new Mallows(100000, 20, 20, 0.4f),
//                                    new Mallows(100000, 20, 20, 0.5f), new Mallows(100000, 20, 20, 0.6f), new Mallows(100000, 20, 20, 0.7f), new Mallows(100000, 20, 20, 0.8f), new Mallows(100000, 20, 20, 0.9f)};
//        return new iOrdinalIterator[]{new Mallows(10000, 10, 10, 0f),new Mallows(10000, 10, 10, 0.1f),new Mallows(10000, 10, 10, 0.2f), new Mallows(10000, 10, 10, 0.3f), new Mallows(10000, 10, 10, 0.4f),
//                                    new Mallows(10000, 10, 10, 0.5f), new Mallows(10000, 10, 10, 0.6f), new Mallows(10000, 10, 10, 0.7f), new Mallows(10000, 10, 10, 0.8f), new Mallows(10000, 10, 10, 0.9f)}; //no
    }
    //at for n = 10, at about 0.65 Mallows, NB out-performs YS
    
    
    private static OneSidedAlgorithmObserver[] getAlgorithms() {
        ArrayList<OneSidedAlgorithmObserver> out = new ArrayList<>();
        for (double d : getExponentialParams()) {
            out.add(new OneSidedAlgorithmObserver(new HungarianAlgorithmWrapper(new ExponentialModel(d))));
        }
        out.add(new OneSidedAlgorithmObserver(new HungarianAlgorithmWrapper(new AntiPluralityModel())));
        out.add(new OneSidedAlgorithmObserver(new Proportional()));
        out.add(new OneSidedAlgorithmObserver(new ProbabilisticSerialRule()));
        //out.add(new AlgorithmObserver(AdaptiveNoMemoryAcceptFirstQueue.class));
        AdaptiveBoston ab = new AdaptiveBoston();
        boolean fixOrder = true;
        ab.setFixInitialOrder(fixOrder);
        //out.add(new OneSidedAlgorithmObserver(ab));
        BordaAtIncreasingAverage baia = new BordaAtIncreasingAverage();
        baia.setFixInitialOrder(fixOrder);
        out.add(new OneSidedAlgorithmObserver(baia));
        out.add(new OneSidedAlgorithmObserver(new GTTCImprovement(baia)));
        BordaAtDecreasingAverage bada = new BordaAtDecreasingAverage();
        bada.setFixInitialOrder(fixOrder);
        out.add(new OneSidedAlgorithmObserver(bada));
        out.add(new OneSidedAlgorithmObserver(new GTTCImprovement(bada)));
        BordaAtCostForMisalloc bacfm = new BordaAtCostForMisalloc();
        bacfm.setFixInitialOrder(fixOrder);
        out.add(new OneSidedAlgorithmObserver(bacfm));
        out.add(new OneSidedAlgorithmObserver(new GTTCImprovement(bacfm)));
        out.addAll(fetchAll(fixOrder));
        OneSidedAlgorithmObserver[] arr = new OneSidedAlgorithmObserver[0];
        arr = out.toArray(arr);
        return arr;
    }
    
    private static iResultsCollator[] getResultCollator() {
        List<iResultsCollator> list = new ArrayList<>();
        list.addAll(Arrays.asList(new iResultsCollator[]{new LogWriter(), new PreferencesCounter(), new PreferenceOrder(),
            new CompareTables(), new EquivalentAlgorithm(), 
            new CustomUtilityModelRaw(new PluralityModel()),
            new CustomUtilityModelRaw(new AntiPluralityModel())}));
        for (double d : getExponentialParams()) {
            list.add(new ExponentialUtilityRaw(d));
            list.add(new ExponentialUtilityPercentageOfMax(d));
        }
        list.addAll(Arrays.asList(new iResultsCollator[]{new BordaScoreRaw(), new BordaWorstAgentToRank(), new BordaOrderBias(), new PluralityScoreRaw(),
            new EnvyAgentCount(), new EnvyFreeProfilesCount(), new EnvyPairsCounter(),
            new WeaklyEnvyAgentCount(), new WeaklyEnvyFreeProfilesCount(), new WeaklyEnvyPairsCount(),
            new OrdinalEfficiency(),
            //new SDProportionalSummary(), new SDProportionalityChecker(), new SDProportionalityAgentCount(),
            new LaTeXTablePrinter(), new Timer()}));
        iResultsCollator[] array = new iResultsCollator[0];
        array = list.toArray(array);
        return array;
    }
    
    private static double[] getExponentialParams() {
        return new double[]{0.0, 10.0}; //return new double[]{-1.0, -0.75, -0.5, -0.25, 0.0, 0.5, 1.0, 1.5, 2.0};
    }

    public static List<OneSidedAlgorithmObserver> fetchAll(boolean fixedOrder) {
        List<OneSidedAlgorithmObserver> out = new ArrayList<>();
        for (int i = 0; i < Math.pow(2, 5); i++) {
            Boolean[] params = new Boolean[5];
            for (int j = 0; j < params.length; j++) {
                params[j] = (i / ((int) Math.pow(2, j))) % 2 == 0;
            }
            if (params[3] == true || params[4] == true) {
                continue;
            }
            GenericImplementation gi = new GenericImplementation(params[0], params[1], params[2], params[3], params[4]);
            gi.setFixInitialOrder(fixedOrder);
            out.add(new OneSidedAlgorithmObserver(gi));
            if (params[1] == false) {
                out.add(new OneSidedAlgorithmObserver(new GTTCImprovement(gi)));
            }
//            for (int k = 0; k < 3; k++) {
//                final int val = k;
//                iRestrictionFactory factory = new RestrictionFactoryAdaptor() {
//                    @Override
//                    public iRestriction[] getRestrictions(int agent, int item) {
//                        return new iRestriction[]{new RandomRestriction((val == 3 ? 1.0 : 1.0/3 * val))};
//                    }
//                };
//                gi = new GenericImplementation(params[0], params[1], params[2], params[3], params[4], factory);
//                out.add(new OneSidedAlgorithmObserver(new GTTCImprovement(gi)));
//            }
        }
        return out;
    }
    //improved with GTTC
    
    private GenericMain() {}
    
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        modifySettings();
        MainBruteForce mbf = new MainBruteForce();
        mbf.start();
        PostBox.broadcast(MessageType.PRINT, "Time taken: " + (System.currentTimeMillis() - start)/1000 + "s");
    }
    
}
