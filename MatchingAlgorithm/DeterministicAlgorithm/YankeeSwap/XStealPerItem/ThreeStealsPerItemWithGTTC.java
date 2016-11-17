/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.DeterministicAlgorithm.YankeeSwap.XStealPerItem;

import MatchingAlgorithm.Auxiliary.Permutation;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import MatchingAlgorithm.DeterministicAlgorithm.improvementAlgorithms.GTTC;

/**
 *
 * @author ylo019
 */
public class ThreeStealsPerItemWithGTTC extends ThreeStealsPerItem {
    
    @Override
    protected Permutation improve(Permutation result, PreferenceProfile input) {
        return GTTC.improve(result, input);
    }
        
    @Override
    public String getName() {
        return "Yankee Swap 3S/I w GTTC";
    }    
}
