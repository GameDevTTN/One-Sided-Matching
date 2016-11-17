/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers.BordaRelated;

import java.util.Map;

import Main.Observers.iDoubleResultsCollator;
import Main.Observers.System.MessageType;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;

/**
 *
 * @author ylo019
 */
public class BordaWorstScoreAsPercentageOfMax extends iDoubleResultsCollator {

    private double max = 0;
    
    @Override
    protected void onEndPreference() {
        beforeResults();
        for (Map.Entry<String, iProbabilityMatrix> e : results.entrySet()) {
            onEachResult(e.getKey(), e.getValue());
        }
    }

    @Override
    protected void beforeResults() {
        max = 0;
        for (iProbabilityMatrix d : results.values()) {
            max = Math.max(max, d.getBordaCount(pp));
        }
    }
    
    @Override
    protected void onEachResult(String key, iProbabilityMatrix value) {
        defaultTable.put(key, Math.min(defaultTable.getOrDefault(key, Double.MAX_VALUE), results.get(key).getBordaCount(pp)/max * 100));
    }
    
    @Override
    protected String getName() {
        return "Lowest Borda Score";
    }
    
    @Override
    protected String getSuffix() {
        return " as % of Max";
    }
    
    @Override
    protected boolean hasPercentageOutput() {
        return true;
    }

    @Override
    protected MessageType broadcastType() {
        return MessageType.SUMMARY;
    }
    
}
