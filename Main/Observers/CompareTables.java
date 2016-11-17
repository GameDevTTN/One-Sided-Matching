/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
public class CompareTables extends iResultsCollator {
    
    private final TreeMap<String, List<Boolean>> comparisons = new TreeMap<>();
    
    public CompareTables() {}
    
    @Override
    protected void onEndPreference() {
        PostBox.broadcast(MessageType.SYSTEM, new Pair<>("Computing Comparisons", ""));
        pairwiseCompare();
        PostBox.broadcast(MessageType.PROCESS, new Pair<>("Comparing Output", new CompareValuesTable(comparisons)));
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
        for (Map.Entry<String, iProbabilityMatrix> e : results.entrySet()) {
            if (!comparisons.containsKey(e.getKey())) {
                comparisons.put(e.getKey(), new ArrayList<Boolean>());
            }
            List<Boolean> l = comparisons.get(e.getKey());
            for (Map.Entry<String, iProbabilityMatrix> f : results.entrySet()) {
                l.add(e != f && e.getValue().equals(f.getValue()));
            }
        }
    }    
    
    public class CompareValuesTable {

        private TreeMap<String, List<Boolean>> comparisons;
        
        private CompareValuesTable(TreeMap<String, List<Boolean>> comparisons) {
            this.comparisons = comparisons;
        }
        
        @Override
        public String toString() {
            String out = "";
            for (Map.Entry<String, List<Boolean>> e : comparisons.entrySet()) {
                if (!out.equals(""))
                    out += "\n";
                for (Boolean d : e.getValue()) {
                    out += ( d ? Settings.format(d): "    ") + ", ";
                }
                out += e.getKey();
            }
            return out;
        }

        public CompareValuesTable and(CompareValuesTable otherTable) {
            TreeMap<String, List<Boolean>> out = new TreeMap<>();
            for (Entry<String, List<Boolean>> e : comparisons.entrySet()) {
                List<Boolean> self = e.getValue();
                List<Boolean> other = otherTable.comparisons.get(e.getKey());
                List<Boolean> and = new ArrayList<>();
                if (other == null || self.size() != other.size()) {
                    System.err.println(other);
                    System.err.println(e.getKey());
                    throw new RuntimeException("CompareValuesTable: and(CVT): otherTable doesn't have key or the size is not the same");
                }
                for (int i = 0; i < self.size(); i++) {
                    and.add(self.get(i) && other.get(i));
                }
                out.put(e.getKey(), and);
            }
            return new CompareValuesTable(out);
        }
    }        
}
