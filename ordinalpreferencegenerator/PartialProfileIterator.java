/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordinalpreferencegenerator;

import java.util.Arrays;

import Main.Settings.FormattingObjectTable;
import MatchingAlgorithm.Auxiliary.InvalidPreferenceException;
import MatchingAlgorithm.Auxiliary.Permutation;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;

/**
 *
 * @author ylo019
 */
//not sure if this is needed anywhere
public class PartialProfileIterator extends OrdinalIteratorAdaptor {

    PartialProfileIterator(int count, int[] output) {
        super(count);
        if (profileIndex.length != (output.length + 1)) {
            throw new RuntimeException("PartialProfileIterator constructor: output is not 1 shorter than profileIndex");
        }
        for (int i = 0; i < output.length; i++) {
            profileIndex[i] = output[i];
        }
        profileIndex[profileIndex.length - 1] = 0;
    }
    
    @Override
    public PreferenceProfile getNext() {
        Permutation[] output = returnProfile();
        int i = agents-1;
        if (++profileIndex[i] >= profiles.length) {
            ++profileIndex[0];
        }
        //return the preference profile
        try {
            return new PreferenceProfile(agents, agents, output);
        } catch (InvalidPreferenceException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
    
    @Override
    public String toString() {
        return FormattingObjectTable.OneDimTable(Arrays.copyOfRange(returnProfile(), 0, agents - 1));
    }

    @Override
    public boolean hasNext() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void updateProfileIndex() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected boolean isValid() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getName() {
        return "PartialProfileIterator";
    }
}
