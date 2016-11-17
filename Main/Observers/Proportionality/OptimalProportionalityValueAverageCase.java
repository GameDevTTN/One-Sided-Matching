/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers.Proportionality;

import java.util.ArrayList;
import java.util.List;
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
//results questionable
public class OptimalProportionalityValueAverageCase extends iResultsCollator {
    
    private final TreeMap<String, List<Double>> opvWorstCases = new TreeMap<>();
    
    @Override
    protected void onEndPreference() {
        PostBox.broadcast(MessageType.SYSTEM, new Pair<>("Computing Average Case OPV", ""));
        for (Map.Entry<String, iProbabilityMatrix> e : results.entrySet()) {
            List<Double> opv = opvWorstCases.getOrDefault(e.getKey(), new ArrayList<Double>());
            if (!Double.isNaN(e.getValue().opv(pp))) {
                opv.add(e.getValue().opv(pp));
            }
            opvWorstCases.put(e.getKey(), opv);
        }
    }
    
    @Override
    protected void onEndSize() {
        TreeMap<String, Double> opvAverageAlpha = new TreeMap<>();
        TreeMap<String, Double> opvAverageOneOverAlpha = new TreeMap<>();
        for (Map.Entry<String, List<Double>> e : opvWorstCases.entrySet()) {
            double sumAlpha = 0.0;
            double sumOneOverAlpha = 0.0;
            for (double d : e.getValue()) {
                sumAlpha += d;
                sumOneOverAlpha += 1/d;
            }
            opvAverageAlpha.put(e.getKey(), sumAlpha/e.getValue().size());
            opvAverageOneOverAlpha.put(e.getKey(), e.getValue().size()/sumOneOverAlpha);
        }
        PostBox.broadcast(MessageType.SUMMARY, new Pair<>("Average Case for OPV (averaging alpha)", new dtoTable<>(opvAverageAlpha)));
        PostBox.broadcast(MessageType.SUMMARY, new Pair<>("Average Case for OPV (averaging factor)", new dtoTable<>(opvAverageOneOverAlpha)));
        clear();
    }
    
    @Override
    protected void clear() {
        opvWorstCases.clear();        
    }

}