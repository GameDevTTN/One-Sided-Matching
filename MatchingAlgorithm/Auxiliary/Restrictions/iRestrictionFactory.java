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
public interface iRestrictionFactory {
    
    public iRestriction[] getRestrictions(int agent, int item);
    
    public boolean checkRestriction(int actingAgent, int item, int currentAgent);
    public void updateRestriction(int actingAgent, int item, int currentAgent);
    public void clearRestriction(int agents, int items);
}
