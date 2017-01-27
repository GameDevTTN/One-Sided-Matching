/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.Auxiliary.Restrictions;

/**
 *
 * @author ylo019
 */
public class LimitedByAgentProposal implements iRestriction {
    
    private int param;
    private int size;
    private int[] counts;
    
    public LimitedByAgentProposal(int size, int param) {
        counts = new int[size];
        this.size = size;
        this.param = param;
    }

    @Override
    public boolean attemptToTake(int actingAgent, int item, int currentAgent) { //currentAgent is never 0;
        if (currentAgent == 0) {
            return true;
        }
        return counts[currentAgent - 1] < param;
    }

    @Override
    public void reset() {
        counts = new int[size];
    }

    @Override
    public void take(int actingAgnet, int item, int currentAgent) {
        if (currentAgent > 0) {
            counts[currentAgent - 1]++;
        }
    }
    
}
