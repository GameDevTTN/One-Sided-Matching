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
public class IANC extends OrdinalIteratorAdaptor {

    public IANC(int count) {
        super(count);
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

    @Override
    public boolean hasNext() {
        if (profileIndex[0] > 0) {
            return false;
        }
        return true;
    }

    @Override
    protected void updateProfileIndex() {
        for (int i = size-1; i >= 0; i--) {
            if (++profileIndex[i] < profiles.length) {
                for (int j = i; j < size; j++) {
                    profileIndex[j] = profileIndex[i];
                }
                break;
            }
        }
    }
    
    @Override
    public String getName() {
        return "IANC";
    }
}
