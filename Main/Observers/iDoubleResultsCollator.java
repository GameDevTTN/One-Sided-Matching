/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers;

import java.util.Map;
import java.util.TreeMap;

import Main.Observers.Auxiliary.dtoPercentageTable;
import Main.Observers.Auxiliary.dtoTable;
import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;
import Pair.Pair;

/**
 *
 * @author ylo019
 */
public abstract class iDoubleResultsCollator extends iResultsCollator {
    
    protected TreeMap<String, Double> defaultTable = new TreeMap<>();
    private long profileCount = 0;
    
    protected long getProfileCount() {
        return profileCount;
    }
    
    @Override
    protected void onEndPreference() {
        ++profileCount;
        beforeResults();
        for (Map.Entry<String, iProbabilityMatrix> e : results.entrySet()) {
            onEachResult(e.getKey(), e.getValue());
        }
    }

    protected void beforeResults() {}
    
    protected void onEachResult(String key, iProbabilityMatrix value) {}
    
    @Override
    protected void onEndSize() {
        TreeMap<String, Double> output = new TreeMap<>();
        for (String s : getMap().keySet()) {
            output.put(s, onEachEntry(s));
        }
        PostBox.broadcast(broadcastType(), 
                new Pair<>(getName() + getSuffix(), (hasPercentageOutput() ?
                    new dtoPercentageTable(output) : new dtoTable<>(output))));
        profileCount = 0;
        clear();
    }
    
    protected Double onEachEntry(String key) {
        return getMap().getOrDefault(key, 0.0);
    }
    
    protected Map<String, Double> getMap() {
        return defaultTable;
    }
    
    protected String getName() {
        return "";
    }
    
    protected String getSuffix() {
        return "";
    }
    
    protected boolean hasPercentageOutput() {
        return false;
    }
    
    protected abstract MessageType broadcastType();

    @Override
    protected void clear() {
        defaultTable.clear();
    }
    
}
