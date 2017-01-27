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
public interface iRestriction {
    
    public boolean attemptToTake(int actingAgent, int item, int currentAgent);
    
    public void take(int actingAgnet, int item, int currentAgent);
    
    public void reset();
    
}
