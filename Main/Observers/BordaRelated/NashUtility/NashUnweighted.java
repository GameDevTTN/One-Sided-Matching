/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers.BordaRelated.NashUtility;

import Main.Observers.BordaRelated.BordaPercentageOfMax;
import Main.Observers.System.MessageType;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;

/**
 *
 * @author ylo019
 */
public class NashUnweighted extends BordaPercentageOfMax {
    
    
    @Override
    protected void beforeResults() {
        //do nothing
    }

    @Override
    protected void onEachResult(String key, iProbabilityMatrix value) {
        defaultTable.put(key, defaultTable.getOrDefault(key, 0.0) + value.getNashUtility(pp));
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
        return "Nash";
    }
    
    @Override
    protected String getSuffix() {
        return "";
    }
    
    @Override
    protected boolean hasPercentageOutput() {
        return false;
    }
}
