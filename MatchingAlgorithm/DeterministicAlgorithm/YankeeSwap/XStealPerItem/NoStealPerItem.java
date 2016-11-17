/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.DeterministicAlgorithm.YankeeSwap.XStealPerItem;

/**
 *
 * @author ylo019
 */
public class NoStealPerItem extends ThreeStealsPerItem {
    
    @Override
    public String getName() {
        return "Yankee Swap 0S/I (RSD)";
    }

    @Override
    protected int stealsPerRound() {
        return 0;
    }   
    
}
