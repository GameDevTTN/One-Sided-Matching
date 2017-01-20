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
 * Currently not doing anything
 * @author ylo019
 */
public class RankEfficiency extends iDoubleResultsCollator {

    @Override
    protected void onEachResult(String key, iProbabilityMatrix value) {
        //defaultTable.put(key, defaultTable.getOrDefault(key, 0.0) + (new GraphImpl(value, pp).hasCycles() ? 0 : 1));
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
        return "\\%RankEff";
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
