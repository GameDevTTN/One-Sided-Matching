/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.DeterministicAlgorithm.YankeeSwap;

import MatchingAlgorithm.DeterministicAlgorithm.YankeeSwap.XStealPerRound.ThreeStealsPerRound;

/**
 *
 * @author ylo019
 */
public class YankeeSwapInfiniteSteals extends ThreeStealsPerRound {
    
    @Override
    public String getName() {
        return "Yankee Swap 9999S (YS Standard)";
    }

    @Override
    protected int stealsPerRound() {
        return 9999;
    }    
    
}
