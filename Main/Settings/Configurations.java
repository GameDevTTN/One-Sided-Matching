/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Settings;

import Main.Observers.OneSidedAlgorithmObserver;
import Main.Observers.System.MessageType;
import Main.Observers.iAlgorithmObserver;
import Main.Observers.iResultsCollator;
import java.util.ArrayList;
import java.util.List;
import ordinalpreferencegenerator.Mallows;
import ordinalpreferencegenerator.iOrdinalIterator;

/**
 *
 * @author ylo019
 */
public class Configurations {
    
    private static final Configurations singleton = new Configurations();
    //public static final Class<? extends iOrdinalIterator> ORDINAL_PREFERENCE = Mallows.class;
    //public static final int PROFILE_COUNT = 10000; //use 10000 for real runs //not relevant for IC/IAC/IANC

    
    private boolean allSet = false;
    private iResultsCollator[] rc = null;
    private iAlgorithmObserver[] ao = null;
    private iOrdinalIterator[] oi = null;
    private MessageType[] mt = null;
    private int index = 0;
//    //params
//    private int increment = 10; //5
//    //M = minimum number of agents
//    private int M = 3; //5
//    //N = maximum number of agents
//    private int N = 4; //100
//    //O = number of objects [O = -1 to set O = current number of agents]
//    private int O = -1;
//    public static final double RUN_CHANCE = 1;
    //public static boolean FIXED_ORDER_FOR_ALGORITHM = false; //set to true for large value of n to only do 1 permutation
    //refactored to let class DeterministicAlgorithm handles it internally
    //if FIXED_ORDER... is false, DO NOT TRY ANYTHING bigger m or n (maximum m or n = 12). Otherwise the fact(n) will overflow
//    public static final double PREF_PARAM = 0.8;
    
    public static Configurations getConfigurations() {
        return singleton;
    }
    
    public List<String> getAlgorithmNames() {
        List<String> out = new ArrayList<>();
        for (iAlgorithmObserver a : ao) {
            if (a != null) {
                out.add(a.getName());
            }
        }
        return out;
    }
    
    public void setResultCollator(iResultsCollator[] rc) {
        if (!allSet && rc != null)
            this.rc = rc;
    }
    
    public void setAlgorithms(iAlgorithmObserver[] ao) {
        if (!allSet && ao != null)
            this.ao = ao;
    }
    
    public void setPreferenceIterator(iOrdinalIterator[] oi) {
        if (!allSet && oi != null)
            this.oi = oi;
    }
    
    public iOrdinalIterator peekIterator() {
        return oi[index];
    }
    
    public boolean nextIterator() {
        if (index + 1 < oi.length) {
            ++index;
            return true;
        }
        return false;
    }
    
    public void setOutput(MessageType[] mt) {
        if (!allSet && mt != null)
            this.mt = mt;
    }
    
    public MessageType[] getOutput() {
        return mt;
    }
    
//    iOrdinalIterator getPreferenceIterator() {
//        return oi;
//    }
    
//    public void setRunParam(int M, int N, int increment, int O) {
//        this.M = M;
//        this.N = N;
//        this.O = O;
//        this.increment = increment;
//    }
//    
//    public int[] getParams() {
//        return new int[]{M, N, increment, O};
//    }
    
    public boolean init() {
        if (!allVariablesSet() || allSet) {
            return false;
        }
        for (iResultsCollator r : rc) {
            if (r != null)
                r.init();
        }
        for (iAlgorithmObserver a : ao) {
            if (a != null)
                a.init();
        }
        allSet = true;
        return true;
    }
    
    private boolean allVariablesSet() {
        return ((rc!=null) && (ao!=null) &&(oi!=null) &&(mt!=null));
    }

    public String getPreferenceDescription() {
        return peekIterator().getName();
    }
    
}
