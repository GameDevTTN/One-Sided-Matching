/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordinalpreferencegenerator;

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
    private final int PROFILE_COUNT = Settings.PROFILE_COUNT;
    
    Mallows(int count) { //package private
        size = count;
        obj = count;
        runs = PROFILE_COUNT;
    }
    
    Mallows(int agent, int object) {
        size = agent;
        obj = object;
        runs = PROFILE_COUNT;
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
        for (int i = 0; i < size; i++) {
            List<Integer> listOfInts = new ArrayList<>();
            listOfInts.addAll(array);
            int[] shuffledArray = new int[obj];
            int indexCounter = 0;
            while (!listOfInts.isEmpty()) {
                double random = Math.random();
                double chance = 0.0;
                for (int j = 0; j < listOfInts.size(); j++) {
                    if (Settings.PREF_PARAM == 1.0) {
                        throw new RuntimeException("Not yet supporting Q == 1.0");
                    } else {
                        chance += ((1 - Settings.PREF_PARAM) * Math.pow(Settings.PREF_PARAM, j))/(1 - Math.pow(Settings.PREF_PARAM, listOfInts.size()));
                    }
                    if (random < chance) {
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
        return "Mallows";
    }
    
}
