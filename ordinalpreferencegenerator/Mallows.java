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
    private int runs;
    private final int PROFILE_COUNT = Settings.PROFILE_COUNT;
    private Permutation[] out;
    
    Mallows(int count) { //package private
        size = count;
        out = new Permutation[size];
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
        
        for (int i = 0; i < out.length; i++) {
            array.add(i + 1);
        }
        for (int i = 0; i < out.length; i++) {
            List<Integer> listOfInts = new ArrayList<>();
            listOfInts.addAll(array);
            int[] shuffledArray = new int[size];
            int indexCounter = 0;
            while (!listOfInts.isEmpty()) {
                for (int j = 0; j < listOfInts.size(); j++) {
                    double chance;
                    if (Settings.PREF_PARAM == 1.0) {
                        throw new RuntimeException("Not yet supporting Q == 1.0");
                    } else {
                        chance = ((1 - Settings.PREF_PARAM) * Math.pow(Settings.PREF_PARAM, j))/(1 - Math.pow(Settings.PREF_PARAM, listOfInts.size()));
                    }
                    if (Math.random() < chance) {
                        shuffledArray[indexCounter++] = listOfInts.remove(j);
                        break;
                    }
                }
            }
            if (!listOfInts.isEmpty()) {
                throw new RuntimeException("leakage");
            }
//            for (int j = 0; j < listOfInts.size(); j++) {
//                shuffledArray[j] = listOfInts.get(j);
//            }
            try {
                out[i] = new Permutation(size, shuffledArray);
            } catch (InvalidPreferenceException ex) {
                throw new RuntimeException("OrdinalPreferenceRandomiser: getNext(): shuffledArray is not a permutation");
            }
        }
        try {
            return new PreferenceProfile(size, size, out);
        } catch (InvalidPreferenceException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
    
    protected Permutation[] returnProfile() {
        return out.clone();
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
