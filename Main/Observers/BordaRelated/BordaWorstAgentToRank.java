/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers.BordaRelated;

import Main.Observers.iDoubleResultsCollator;
import Main.Observers.System.MessageType;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;

/**
 *
 * @author ylo019
 */
public class BordaWorstAgentToRank extends iDoubleResultsCollator {
    
    @Override
    protected void onEachResult(String key, iProbabilityMatrix value) {
        defaultTable.put(key, defaultTable.getOrDefault(key, 0.0) + results.get(key).getMinBordaCount(pp));
    }
    
    @Override
    protected Double onEachEntry(String key) {
        return getMap().getOrDefault(key, 0.0)/getProfileCount();
    }
    
    @Override
    protected String getName() {
        return "Egal";
    }
    
    @Override
    protected String getSuffix() {
        return "";
    }
    
    @Override
    protected boolean hasPercentageOutput() {
        return false;
    }

    @Override
    protected MessageType broadcastType() {
        return MessageType.SUMMARY;
    }
    
}
