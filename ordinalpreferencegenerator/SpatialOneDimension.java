/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordinalpreferencegenerator;

import Main.Settings.Configurations;
import Main.Settings.Settings;
import MatchingAlgorithm.Auxiliary.InvalidPreferenceException;
import MatchingAlgorithm.Auxiliary.Permutation;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
//obsolete class
public class SpatialOneDimension implements iOrdinalIterator{

    protected final int size;
    private int runs;
    private final int PROFILE_COUNT = Configurations.PROFILE_COUNT;
    private Permutation[] out;
    
    private final double PREF_PARAM;
    
    SpatialOneDimension(int count) { //package private
        size = count;
        out = new Permutation[size];
        runs = PROFILE_COUNT;
        PREF_PARAM = 0.99d;
        
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
                    if (PREF_PARAM == 1.0) {
                        throw new RuntimeException("Not yet supporting Q == 1.0");
                    } else { //this is the wrong mallows - need fix
                        chance = ((1 - PREF_PARAM) * Math.pow(PREF_PARAM, j))/(1 - Math.pow(PREF_PARAM, listOfInts.size()));
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
                out[i] = new Permutation(size, size, shuffledArray);
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
        return "Spatial 1D";
    }
    
}
