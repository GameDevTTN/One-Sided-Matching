/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import Main.Settings.Settings;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;
import Pair.Pair;

/**
 *
 * @author ylo019
 */
public class NetSatisfactionComparator extends iResultsCollator {
    
    private final TreeMap<String, List<Double>> comparisons = new TreeMap<>();
    
    public NetSatisfactionComparator() {}

    @Override
    protected void onEndPreference() {
        PostBox.broadcast(MessageType.SYSTEM, new Pair<>("Computing Comparisons", ""));
        pairwiseCompare();
        PostBox.broadcast(MessageType.COMPARISON, new Pair<>("Net Satisfaction", new ComparisonTable(comparisons)));
        clear();
    }
    
    @Override
    protected void clear() {
        comparisons.clear();        
    }
    
    private void pairwiseCompare() {
        if (pp == null) {
            throw new RuntimeException("ResultsComparator: pairwiseCompare(): PreferenceProfile is null");
        }
        for (Entry<String, iProbabilityMatrix> e : results.entrySet()) {
            if (!comparisons.containsKey(e.getKey())) {
                comparisons.put(e.getKey(), new ArrayList<Double>());
            }
            List<Double> l = comparisons.get(e.getKey());
            for (Entry<String, iProbabilityMatrix> f : results.entrySet()) {
                l.add(e.getValue().compare(pp, f.getValue(), pp.size()).difference());
            }
        }
    }

    public class ComparisonTable {

        private TreeMap<String, List<Double>> comparisons;
        
        private ComparisonTable(TreeMap<String, List<Double>> comparisons) {
            this.comparisons = comparisons;
        }
        
        @Override
        public String toString() {
            String out = "";
            for (Entry<String, List<Double>> e : comparisons.entrySet()) {
                if (!out.equals(""))
                    out += "\n";
                for (Double d : e.getValue()) {
                    out += Settings.format(d) + " ";
                }
                out += e.getKey();
            }
            return out;
        }
    }
}
