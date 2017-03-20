/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.Auxiliary.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ylo019
 */
public class RestrictionFactoryAdaptor implements iRestrictionFactory {

    @Override
    public iRestriction[] getRestrictions(int agent, int item) {
        return new iRestriction[0];
    }

    private List<iRestriction> restrictions = new ArrayList<iRestriction>() {
        @Override
        public String toString() {
            String output = "";
            for (iRestriction r : this) {
                output += (r.toString() + " ");
            }
            return output;
        }
    };
    
    public boolean checkRestriction(int actingAgent, int item, int currentAgent) {
        for (iRestriction r : restrictions) {
            if (!r.attemptToTake(actingAgent, item, currentAgent)) {
                return false;
            }
        }
        return true;
    }
    
    public void updateRestriction(int actingAgent, int item, int currentAgent) {
        for (iRestriction r : restrictions) {
            r.take(actingAgent, item, currentAgent);
        }
    }
    
    public void clearRestriction(int agents, int items) {
        restrictions.clear();
        for (iRestriction r : getRestrictions(agents, items)) {
            restrictions.add(r);
        }
    }
    
    @Override
    public String toString() {
        return restrictions.toString();
    }
    
}
