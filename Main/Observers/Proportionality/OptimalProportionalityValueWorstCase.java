/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers.Proportionality;

import java.util.Map;
import java.util.TreeMap;

import Main.Observers.iResultsCollator;
import Main.Observers.Auxiliary.dtoTable;
import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;
import Pair.Pair;

/**
 *
 * @author ylo019
 */
public class OptimalProportionalityValueWorstCase extends iResultsCollator {
    
    private final TreeMap<String, Double> opvWorstCases = new TreeMap<>();
    
    @Override
    protected void onEndPreference() {
        PostBox.broadcast(MessageType.SYSTEM, new Pair<>("Computing Worst Case OPV", ""));
        for (Map.Entry<String, iProbabilityMatrix> e : results.entrySet()) {
            opvWorstCases.put(e.getKey(), Math.min(opvWorstCases.getOrDefault(e.getKey(), Double.POSITIVE_INFINITY), e.getValue().opv(pp)));
        }
    }
    
    @Override
    protected void onEndSize() {
        TreeMap<String, Double> opvOut = new TreeMap<>();
        for (Map.Entry<String, Double> e : opvWorstCases.entrySet()) {
            opvOut.put(e.getKey(), (e.getValue()));
        }
        PostBox.broadcast(MessageType.SUMMARY, new Pair<>("Worst Case for Optimal Proportionality Value", new dtoTable<>(opvOut)));
        clear();
    }
    
    @Override
    protected void clear() {
        opvWorstCases.clear();        
    }

}