/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordinalpreferencegenerator;

import java.util.Arrays;

import MatchingAlgorithm.Auxiliary.InvalidPreferenceException;
import MatchingAlgorithm.Auxiliary.Permutation;

/**
 *
 * @author ylo019
 */
public class PartialIterator {
    protected final int size;
    protected final Permutation[] profiles;
    protected final int[] profileIndex;
    
    public static PartialIterator getIterator(int count) {
        if (count > 10 || count <= 1) {
            return null;
        }
        return new PartialIterator(count);
    }
    
    PartialIterator(int count) { //package private
        size = count - 1;
        profiles = StaticFunctions.permutations(size + 1);
        profileIndex = new int[size];
    }
    
    public boolean hasNext() {
        if (profileIndex[0] > 0) {
            return false;
        }
        return true;
    }
    
    protected Permutation[] returnProfile() {
        Permutation[] output = new Permutation[size];
        for (int i = 0; i < size; i++) {
            output[i] = profiles[profileIndex[i]];
        }
        return output;
    }

    public PartialProfileIterator getNext() {
        int[] output = profileIndex.clone();
        do {
            for (int i = size-1; i >= 0; i--) {
                if (++profileIndex[i] < profiles.length) {
                    for (int j = i; j < size; j++) {
                        profileIndex[j] = profileIndex[i];
                    }
                    break;
                }
            }
        } while (!isValid());
        return new PartialProfileIterator(size+1, output);
    }

    protected boolean isValid() {
        if (profileIndex[0] > 0) {
            return true; //there is no more profiles - just halt
        }
        //naive algorithm - probably optimisable
        Permutation[] curr = returnProfile();
        for (Permutation p : curr) {
            Permutation[] newPerm = new Permutation[curr.length];
            for (int q = 0; q < newPerm.length; q++) {
                try {
                    newPerm[q] = curr[q].renaming(p.inverse());
                } catch (InvalidPreferenceException ex) {
                    throw new RuntimeException(ex.getMessage());
                }
            }
            Arrays.sort(newPerm, 0, newPerm.length);
            for (int r = 0; r < curr.length; r++) {
                if (curr[r].compareTo(newPerm[r]) < 0) {
                    break;
                } else if (curr[r].compareTo(newPerm[r]) > 0) {
                    return false;
                }
            }
        }
        return true;
    }
    
    protected int profileLength() {
        return profiles.length;
    }
    
    protected Permutation getProfile(int index) {
        return profiles[index];
    }    
    
}
