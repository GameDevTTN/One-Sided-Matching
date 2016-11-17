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
public class NStealPerRound extends ThreeStealsPerRound {
    
    private int n;
    
    public NStealPerRound(int n) {
        this.n = n;
    }    
    
    @Override
    public String getName() {
        return "Yankee Swap " + stealsPerRound() + "S/R";
    }

    @Override
    protected int stealsPerRound() {
        return n;
    }
    
}
