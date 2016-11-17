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
public class NStealPerAgent extends ThreeStealsPerAgent {
    
    private int n;
    
    public NStealPerAgent(int n) {
        this.n = n;
    }
    
    @Override
    public String getName() {
        return "Yankee Swap " + stealsPerRound() + "S/A";
    }

    @Override
    protected int stealsPerRound() {
        return n;
    }
    
}
