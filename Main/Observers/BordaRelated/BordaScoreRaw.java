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
public class BordaScoreRaw extends iDoubleResultsCollator {

    @Override
    protected void onEachResult(String key, iProbabilityMatrix value) {
        defaultTable.put(key, defaultTable.getOrDefault(key, 0.0) + value.getBordaCount(pp));
    }
    
    @Override
    protected Double onEachEntry(String key) {
        return getMap().getOrDefault(key, 0.0)/getProfileCount();
    }
    
    @Override
    protected MessageType broadcastType() {
        return MessageType.SUMMARY;
    }
    
    @Override
    protected String getName() {
        return "Util";
    }
    
    @Override
    protected String getSuffix() {
        return " Raw";
    }

    @Override
    protected boolean hasPercentageOutput() {
        return false;
    }
    
}
