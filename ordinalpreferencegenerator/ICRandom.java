/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordinalpreferencegenerator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import Main.Settings.Settings;
import MatchingAlgorithm.Auxiliary.InvalidPreferenceException;
import MatchingAlgorithm.Auxiliary.Permutation;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;

/**
 *
 * @author ylo019
 */
public class ICRandom implements iOrdinalIterator {
    
    protected final int size;
    protected final int objectSize;
    private int runs;
    private final int PROFILE_COUNT = Settings.PROFILE_COUNT;
    
    ICRandom(int count, int objects) { //package private
        size = count;
        this.objectSize = objects;
        runs = PROFILE_COUNT;
    }
    
    @Override
    public boolean hasNext() {
        return runs > 0;
    }
    @Override
    public PreferenceProfile getNext() {
        if (!hasNext()) {
            throw new RuntimeException("OrdinalPreferenceRandomiser: getNext(): end of profiles");
        }
        --runs;
        Permutation[] out = new Permutation[size];
        Integer[] array = new Integer[size];
        for (int i = 0; i < out.length; i++) {
            array[i] = i+1;
        }
        for (int i = 0; i < out.length; i++) {
            List<Integer> listOfInts = Arrays.asList(array);
            Collections.shuffle(listOfInts);
            int[] shuffledArray = new int[size];
            for (int j = 0; j < listOfInts.size(); j++) {
                shuffledArray[j] = listOfInts.get(j);
            }
            try {
                out[i] = new Permutation(size, objectSize, shuffledArray);
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
    
    @Override
    public int profileLength() {
        return size;
    }
    
    @Override
    public String getName() {
        return "IC Random";
    }
}
