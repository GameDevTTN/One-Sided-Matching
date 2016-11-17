/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.DeterministicAlgorithm.YankeeSwap.XStealPerRound;

/**
 *
 * @author ylo019
 */
public class NoStealPerRound extends ThreeStealsPerRound {
    
    @Override
    public String getName() {
        return "Yankee Swap 0S/R (RSD)";
    }

    @Override
    protected int stealsPerRound() {
        return 0;
    }    
    
}
