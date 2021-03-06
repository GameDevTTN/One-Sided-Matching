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
public class LimitedByItemHeldBy implements iRestriction {
    private int param;
    private int size;
    private int[] counts;
    
    public LimitedByItemHeldBy(int size, int param) { //size is number of items
        counts = new int[size];
        this.size = size;
        this.param = param;
    }

    @Override
    public boolean attemptToTake(int actingAgent, int item, int currentAgent) {
        if (currentAgent == 0) {
            return true;
        }
        return counts[item - 1] <= param;
    }

    @Override
    public void reset() {
        counts = new int[size];
    }

    @Override
    public void take(int actingAgnet, int item, int currentAgent) {
        counts[item - 1]++;
    }
    
    @Override
    public String toString() {
        return "Item changes hands <= " + param;
    }
    
}
