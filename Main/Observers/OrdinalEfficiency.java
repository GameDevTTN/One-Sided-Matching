/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers;

import Graph.GraphImpl;
import Main.Observers.System.MessageType;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;

/**
 *
 * @author ylo019
 */
//record the percentage of probability matrix that is SD efficient (interim efficiency) for each algorithms
//if FIXED_ORDER is set to on, SD efficiency === Ex-post efficiency
public class OrdinalEfficiency extends iDoubleResultsCollator {

    @Override
    protected void onEachResult(String key, iProbabilityMatrix value) {
        defaultTable.put(key, defaultTable.getOrDefault(key, 0.0) + (new GraphImpl(value, pp).hasCycles() ? 0 : 1));
    }
    
    @Override
    protected Double onEachEntry(String key) {
        return getMap().getOrDefault(key, 0.0)/getProfileCount() * 100;
    }
    
    @Override
    protected MessageType broadcastType() {
        return MessageType.SUMMARY;
    }
    
    @Override
    protected String getName() {
        return "\\%SD";
    }
    
    @Override
    protected String getSuffix() {
        return "";
    }
    
    @Override
    protected boolean hasPercentageOutput() {
        return true;
    }
    
}
