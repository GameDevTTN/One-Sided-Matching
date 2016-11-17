/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers.Proportionality;

import java.util.Map;
import java.util.TreeMap;

import Main.Observers.iResultsCollator;
import Main.Observers.Auxiliary.dtoPercentageTable;
import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;
import Pair.Pair;

/**
 *
 * @author ylo019
 */
public class OptimalProportionalityValuePercentageOfNaN extends iResultsCollator {
    
    private final TreeMap<String, Integer> opvWorstCases = new TreeMap<>();
    private int counter = 0;
    
    @Override
    protected void onEndPreference() {
        PostBox.broadcast(MessageType.SYSTEM, new Pair<>("Computing Worst Case OPV", ""));
        for (Map.Entry<String, iProbabilityMatrix> e : results.entrySet()) {
            opvWorstCases.put(e.getKey(), opvWorstCases.getOrDefault(e.getKey(), 0) + (Double.isNaN(e.getValue().opv(pp)) ? 1 : 0));
        }
        ++counter;
    }
    
    @Override
    protected void onEndSize() {
        TreeMap<String, Double> opvOut = new TreeMap<>();
        for (Map.Entry<String, Integer> e : opvWorstCases.entrySet()) {
            opvOut.put(e.getKey(), ((double)e.getValue())/counter * 100);
        }
        PostBox.broadcast(MessageType.SUMMARY, new Pair<>("Percentage of NaN for Optimal Proportionality Value", new dtoPercentageTable(opvOut)));
        clear();
    }
    
    @Override
    protected void clear() {
        opvWorstCases.clear();
        counter = 0;
    }

}