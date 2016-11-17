/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.DeterministicAlgorithm.YankeeSwap.XStealPerAgent;

/**
 *
 * @author ylo019
 */
public class NoStealPerAgent extends ThreeStealsPerAgent {
    
    @Override
    public String getName() {
        return "Yankee Swap 0S/A (RSD)";
    }

    @Override
    protected int stealsPerRound() {
        return 0;
    }   
    
}
