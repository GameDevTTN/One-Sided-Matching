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
    
    public LimitedByTotalSteals(int param) {
        
    }

    @Override
    public boolean attemptToTake(int actingAgent, int item, int currentAgent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void take(int actingAgnet, int item, int currentAgent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
