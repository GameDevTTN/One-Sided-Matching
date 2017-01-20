/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers;

import Main.Observers.Auxiliary.dtoPercentageTable;
import Main.Observers.Auxiliary.dtoTable;
import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;
import Pair.Pair;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author ylo019
 */
public abstract class iGenericResultsCollator<T> extends iResultsCollator {
    
    protected TreeMap<String, T> defaultTable = new TreeMap<>();
    protected long profileCount = 0;
    
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
        TreeMap<String, T> output = new TreeMap<>();
        for (String s : getMap().keySet()) {
            output.put(s, onEachEntry(s));
        }
        PostBox.broadcast(broadcastType(), 
                new Pair<>(getName() + getSuffix(), formatOutput(output)));
        profileCount = 0;
        clear();
    }
    
    protected T onEachEntry(String key) {
        return getMap().getOrDefault(key, defaultValue());
    }
    
    protected Map<String, T> getMap() {
        return defaultTable;
    }
    
    protected String getName() {
        return "";
    }
    
    protected String getSuffix() {
        return "";
    }
   
    protected abstract MessageType broadcastType();
    protected abstract T defaultValue();
    protected abstract Object formatOutput(TreeMap<String, T> output);

    @Override
    protected void clear() {
        defaultTable.clear();
    }
    
}