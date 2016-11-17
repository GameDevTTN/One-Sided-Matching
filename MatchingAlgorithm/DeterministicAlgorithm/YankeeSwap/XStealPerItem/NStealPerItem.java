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
public class NStealPerItem extends ThreeStealsPerItem {
    
    private int n;
    
    public NStealPerItem(int n) {
        this.n = n;
    }    
    
    @Override
    public String getName() {
        return "Yankee Swap " + stealsPerRound() + "S/I";
    }

    @Override
    protected int stealsPerRound() {
        return n;
    }
    
}
