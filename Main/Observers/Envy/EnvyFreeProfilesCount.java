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
public class EnvyFreeProfilesCount extends iDoubleResultsCollator {
    
    @Override
    protected void onEachResult(String key, iProbabilityMatrix value) {
        defaultTable.put(key, defaultTable.getOrDefault(key, 0.0) + (value.isEnvyFree(pp) ? 1 : 0));
    }
    
    @Override
    protected boolean hasPercentageOutput() {
        return true;
    }
    
    @Override
    protected Double onEachEntry(String key) {
        return defaultTable.getOrDefault(key, 0.0)/getProfileCount() * 100;
    }
    
    @Override
    public String getName() {
        return "\\%EF";
    }

    @Override
    protected MessageType broadcastType() {
        return MessageType.SUMMARY;
    }
}
