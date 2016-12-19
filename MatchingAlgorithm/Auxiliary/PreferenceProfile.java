/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.Auxiliary;

import java.util.Arrays;

import Main.Settings.FormattingObjectTable;
import Main.Settings.Settings;

/**
 *
 * @author ylo019
 */
public class PreferenceProfile {
    
    private final int agents;
    private final int objects;
    private Permutation[] preferences;
    
    public PreferenceProfile(int agent, int object, Permutation[] preferences) throws InvalidPreferenceException {
        agents = agent;
        objects = object;
        if (!isValid(preferences)) {
            throw new InvalidPreferenceException("PreferenceProfile(): at least one of the preference is invalid");
        }
        this.preferences = new Permutation[preferences.length];
        System.arraycopy(preferences, 0, this.preferences, 0, preferences.length);
    }

    private boolean isValid(Permutation[] preferences) {
        if (preferences == null || preferences.length != agents) {
            return false;
        }
        for (Permutation p : preferences) {
            if (p.size() != objects) {
                return false;
            }
        }
        return true;
    }
    
    public int size() {
        return agents;
    }
    
    public int objectSize() {
        return objects;
    }

    @Override
    public String toString() {
        return FormattingObjectTable.OneDimTable(preferences);
    }

    public iProfileIterator getIterator() {
        return new PreferenceProfileIterator();
    }
    
    public boolean stochasticDominates(int agent, double[] has, double[] other) { //or the same
        PartialComparatorResult pcr = PreferencesComparatorFactory.buildDoubleArrayComparator(preferences[agent - 1].getIterator()).compare(has, other);
//        if (agent <= 0 || agent > agents) {
//            throw new RuntimeException("doesNotEnvy(int agent, double[] has, double[] other): agent is out of bound");
//        }
//        double hasSum = 0;
//        double otherSum = 0;
//        iIterator prefIterator = preferences[agent - 1].getIterator();
//        while (prefIterator.hasNext()) {
//            int obj = prefIterator.getNext() - 1;
//            hasSum += has[obj];
//            otherSum += other[obj];
//            if (!Settings.doubleEqual(hasSum, otherSum) && otherSum > hasSum) {
//                return false;
//            }
//        }
//        return true;
        return (pcr == PartialComparatorResult.GREATER || pcr == PartialComparatorResult.EQUAL);
    }
    
    
    public boolean hasStrongSdEnvy(int agent, double[] has, double[] other) {
        PartialComparatorResult pcr = PreferencesComparatorFactory.buildDoubleArrayComparator(preferences[agent - 1].getIterator()).compare(has, other);
        return (pcr == PartialComparatorResult.INCOMPARABLE || pcr == PartialComparatorResult.LESS); //own allocation is not better or the same
    }
    
    public boolean hasWeakSdEnvy(int agent, double[] has, double[] other) {
        PartialComparatorResult pcr = PreferencesComparatorFactory.buildDoubleArrayComparator(preferences[agent - 1].getIterator()).compare(has, other);
        return pcr == PartialComparatorResult.LESS; //other's allocation is strictly better
    }
    
    public Double optimalProportionalityValue(int agent, double[] has) {
        double OPV = Double.POSITIVE_INFINITY;
        iIterator iter = preferences[agent - 1].getIterator();
        if (Settings.doubleEqual(has[iter.getNext() - 1], 0.0)) {
            return Double.NaN;
        }
        iter.resetPointer();
        int count = 0;
        double sum = 0;
        while (iter.hasNext()) {
            sum += has[iter.getNext() - 1];
            OPV = Math.min(OPV, ++count/sum);
        }
        return OPV;
    }
    
    public boolean doesSDPrefer(int agent, double[] has, double[] other) {
        PartialComparatorResult pcr = PreferencesComparatorFactory.buildDoubleArrayComparator(preferences[agent - 1].getIterator()).compare(has, other);
        return (pcr == PartialComparatorResult.GREATER || pcr == PartialComparatorResult.EQUAL);
//        if (agent <= 0 || agent > agents) {
//            throw new RuntimeException("doesSDPrefer(int agent, double[] has, double[] other): agent is out of bound");
//        }
//        double hasSum = 0;
//        double otherSum = 0;
//        boolean hasBetter = true; //doesn't matter if hasBetter, equal is fine
//        iIterator prefIterator = preferences[agent - 1].getIterator();
//        while (prefIterator.hasNext()) {
//            int obj = prefIterator.getNext() - 1;
//            hasSum += has[obj];
//            otherSum += other[obj];
//            if (!Settings.doubleEqual(hasSum, otherSum) && otherSum > hasSum) {
//                return false;
//            }
//        }
//        return hasBetter;
    }
    
    public boolean doesStrictSDPrefer(int agent, double[] has, double[] other) {
        PartialComparatorResult pcr = PreferencesComparatorFactory.buildDoubleArrayComparator(preferences[agent - 1].getIterator()).compare(has, other);
        return (pcr == PartialComparatorResult.GREATER);
//        if (agent <= 0 || agent > agents) {
//            throw new RuntimeException("doesSDPrefer(int agent, double[] has, double[] other): agent is out of bound");
//        }
//        double hasSum = 0;
//        double otherSum = 0;
//        boolean hasBetter = false;
//        iIterator prefIterator = preferences[agent - 1].getIterator();
//        while (prefIterator.hasNext()) {
//            int obj = prefIterator.getNext() - 1;
//            hasSum += has[obj];
//            otherSum += other[obj];
//            if (!Settings.doubleEqual(hasSum, otherSum) && hasSum > otherSum) {
//                hasBetter = true;
//            } else if (!Settings.doubleEqual(hasSum, otherSum) && otherSum > hasSum) {
//                return false;
//            }
//        }
//        return hasBetter;
    }

    public Permutation[] getProfiles() {
        Permutation[] out = new Permutation[preferences.length];
        for (int i = 0; i < preferences.length; ++i) {
            try {
                out[i] = new Permutation(preferences[i]);
            } catch (InvalidPreferenceException ex) {
                throw new RuntimeException("PreferenceProfile: getProfiles(): error cloning");
            }
        }
        return out;
    }
    
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof PreferenceProfile) || ((PreferenceProfile)other).size() != size()) {
            return false;
        }
        PreferenceProfile others = (PreferenceProfile)other;
        for (int i = 0; i < size(); ++i) {
            if (!preferences[i].equals(others.preferences[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.agents;
        hash = 89 * hash + Arrays.deepHashCode(this.preferences);
        return hash;
    }
    
    public class PreferenceProfileIterator implements iProfileIterator {
        
        iIterator[] pointers;
        
        public PreferenceProfileIterator() {
            pointers = new iIterator[size()];
            for (int i = 0; i < size(); i++) {
                pointers[i] = preferences[i].getIterator();
            }
        }
        
        @Override
        public void resetPointers() {
            for (iIterator p : pointers) {
                p.resetPointer();
            }
        }

        @Override
        public boolean hasNext(int agent) {
            if (agent <= 0 || agent > agents) {
                throw new RuntimeException("hasNext(int agent): agent is out of bound");
            }
            return pointers[agent - 1].hasNext();
        }

        @Override
        public int getNext(int agent) { //param: agent number from 1 to size
            if (agent <= 0 || agent > agents) {
                throw new RuntimeException("getNext(int agent): agent is out of bound");
            }
            return pointers[agent - 1].getNext();
        }

        @Override
        public final int size() {
            return PreferenceProfile.this.size();
        }
    }
}
