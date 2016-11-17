/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers.BordaRelated;

import java.util.Map.Entry;
import java.util.TreeMap;

import Main.Observers.iResultsCollator;
import Main.Observers.Auxiliary.dtoPercentageTable;
import Main.Observers.Auxiliary.dtoTable;
import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;
import Pair.Pair;

/**
 *
 * @author ylo019
 */
public class BordaTableCollator extends iResultsCollator {
    
    private final TreeMap<String, Double> table = new TreeMap<>();

    @Override
    protected void onEndPreference() {
        for (Entry<String, iProbabilityMatrix> e : results.entrySet()) {
            table.put(e.getKey(), score(e.getValue(), pp));
        }
        PostBox.broadcast(MessageType.COMPARISON, new Pair<>(getName(), (isPercentage() ? new dtoPercentageTable(table) : new dtoTable(table))));            
        clear();
    }
    
    protected double score(iProbabilityMatrix ipm, PreferenceProfile pp) {
        return ipm.getBordaCount(pp);
    }   
    
    protected String getName() {
        return "Borda Table";
    }
    
    protected boolean isPercentage() {
        return false;
    }

    @Override
    protected void clear() {
        table.clear();
    }
    
}
