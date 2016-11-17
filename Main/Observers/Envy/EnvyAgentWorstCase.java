/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers.Envy;

import Main.Observers.iDoubleResultsCollator;
import Main.Observers.System.MessageType;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;

/**
 *
 * @author ylo019
 */
public class EnvyAgentWorstCase extends iDoubleResultsCollator {
    
    @Override
    protected void onEachResult(String key, iProbabilityMatrix value) {
        defaultTable.put(key, Math.max(defaultTable.getOrDefault(key, 0.0), value.numberOfEnviousAgents(pp)));
    }
    
    @Override
    protected Double onEachEntry(String key) {
        return getMap().get(key);
    }
    
    @Override
    protected MessageType broadcastType() {
        return MessageType.SUMMARY;
    }
    
    @Override
    public String getName() {
        return "Worst Case for # of Envious Agents";
    }
    
    @Override
    protected boolean hasPercentageOutput() {
        return false;
    }
}
