/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordinalpreferencegenerator;

import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import MatchingAlgorithm.Auxiliary.iProfileIterator;

/**
 *
 * @author ylo019
 */
public class NotAllUniqueFirstChoice {
    
    public static boolean check(PreferenceProfile pp) {
        iProfileIterator ipi = pp.getIterator();
        boolean[] found = new boolean[pp.size()];
        for (int i = 0; i < pp.size(); i++) {
            int firstChoiceObj = ipi.getNext(i+1) - 1;
            if (found[firstChoiceObj]) {
                return true;
            } else {
                found[firstChoiceObj] = true;
            }
        }
        return false;
    }
    
}
