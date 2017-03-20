/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.Auxiliary.Restrictions;

import Main.Settings.Format;

/**
 *
 * @author ylo019
 */
public class RandomRestriction implements iRestriction {
    
    private double param; //probability to break engagement
    
    public RandomRestriction(double param) {
        this.param = param;
    }

    @Override
    public boolean attemptToTake(int actingAgent, int item, int currentAgent) {
        if(currentAgent == 0) {
            return true;
        }
        return (Math.random() > param);
    }

    @Override
    public void take(int actingAgnet, int item, int currentAgent) {
        return;
    }

    @Override
    public void reset() {
        return;
    }
    
    @Override
    public String toString() {
        return "Random " + Format.DoubleToString(param);
    }
    
}
