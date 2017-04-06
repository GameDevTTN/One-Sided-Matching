/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordinalpreferencegenerator;

import Main.Settings.Configurations;
import Main.Settings.Format;
import Main.Settings.Settings;
import MatchingAlgorithm.Auxiliary.InvalidPreferenceException;
import MatchingAlgorithm.Auxiliary.Permutation;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class Mallows implements iOrdinalIterator {

    protected final int size;
    protected final int obj;
    private int runs;
    //private final int PROFILE_COUNT;// = Configurations.PROFILE_COUNT;
    private final double PREF_PARAM;
    
    public Mallows(int run, int count) {
        this(run, count, 0.99d);
    }
    
    public Mallows(int run, int agent, int object) {
        this(run, agent, object, 0.99d);
    }
    
    public Mallows(int run, int count, double param) { //package private
        this(run, count, count, param);
    }
    
    public Mallows(int run, int agent, int object, double param) {
        size = agent;
        obj = object;
        runs = run;
        if (param < 0.0d || param >= 1.0d) {
            param = 0.99d;
        }
        PREF_PARAM = param;
    }
    
    @Override
    public boolean hasNext() {
        return runs > 0;
    }
    @Override
    public PreferenceProfile getNext() {
        if (!hasNext()) {
            throw new RuntimeException("Mallows: getNext(): end of profiles");
        }
        --runs;
        Permutation[] out = new Permutation[size];
        //edit code below to make it generate Mallows distribution
        List<Integer> array = new ArrayList<>();
        
        for (int i = 0; i < obj; i++) {
            array.add(i + 1);
        }
        Collections.shuffle(array);
        for (int i = 0; i < size; i++) {
            List<Integer> listOfInts = new ArrayList<>();
            listOfInts.addAll(array);
            int[] shuffledArray = new int[obj];
            int indexCounter = 0;
            while (!listOfInts.isEmpty()) {
                double random = Math.random();
                double chance = 0.0;
                for (int j = 0; j < listOfInts.size(); j++) {
                    if (PREF_PARAM == 1.0) {
                        chance += (1.0/listOfInts.size());
                    } else {
                        chance += ((1 - PREF_PARAM) * Math.pow(PREF_PARAM, j))/(1 - Math.pow(PREF_PARAM, listOfInts.size()));
                    }
                    if (random < chance || j == (listOfInts.size() - 1)) {
                        shuffledArray[indexCounter++] = listOfInts.remove(j);
                        break;
                    }
                }
            }

            try {
                out[i] = new Permutation(obj, obj, shuffledArray);
            } catch (InvalidPreferenceException ex) {
                throw new RuntimeException("OrdinalPreferenceRandomiser: getNext(): shuffledArray is not a permutation");
            }
        }
        try {
            return new PreferenceProfile(size, obj, out);
        } catch (InvalidPreferenceException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
    
    protected Permutation[] returnProfile() {
        return null;//out.clone();
    }

    @Override
    public int profileLength() {
        return size;
    }
    
    @Override
    public String getName() {
        return "Mallows " + Format.DoubleToString(PREF_PARAM);
    }
    
}
