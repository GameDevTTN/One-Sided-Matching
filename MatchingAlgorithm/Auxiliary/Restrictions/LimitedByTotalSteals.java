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
public class LimitedByTotalSteals implements iRestriction {
    
    private int param;
    private int counts;
    
    public LimitedByTotalSteals(int param) {
        counts = 0;
        this.param = param;
    }

    @Override
    public boolean attemptToTake(int actingAgent, int item, int currentAgent) {
        if (currentAgent == 0) {
            return true;
        }
        return counts < param;
    }

    @Override
    public void reset() {
        counts = 0;
    }

    @Override
    public void take(int actingAgnet, int item, int currentAgent) {
        if(currentAgent > 0) {
            counts++;
        }
    }
    
    @Override
    public String toString() {
        return "Total Steal <= " + param;
    }
    
}
