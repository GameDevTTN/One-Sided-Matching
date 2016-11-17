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
import MatchingAlgorithm.Auxiliary.iProfileIterator;
import Pair.Pair;

/**
 *
 * @author ylo019
 */
public class LeximinCollator extends iResultsCollator {
    
    private final TreeMap<String, List<Double>> prefCount = new TreeMap<>(); //probably technically LinkedList will be better, but ArrayList should be adequate, number of algorithm will not be too huge
    
    @Override
    protected void onEndPreference() {
        PostBox.broadcast(MessageType.SYSTEM, new Pair<>("Computing Comparisons", ""));
        pairwiseCompare();
        PostBox.broadcast(MessageType.COMPARISON, new Pair<>("Leximin Comparator", new LeximinOrder(prefCount)));
        clear();
    }

    @Override
    protected void clear() {
        prefCount.clear();
    }

    private void pairwiseCompare() {
        for (Entry<String, iProbabilityMatrix> e : results.entrySet()) {
            iProfileIterator iter = pp.getIterator();
            iProbabilityMatrix matrix = e.getValue();
            List<Double> scores = new ArrayList<>();
            for (int i = 0; i < matrix.size(); i++) {
                double d = 0;
                for (int j = 0; j < matrix.size(); j++) {
                    d += matrix.read(j+1, iter.getNext(j+1));
                }
                scores.add(d);
            }
            prefCount.put(e.getKey(), scores);
        }
    }
    
    public class LeximinOrder {
        
        private Map<String, List<Double>> prefCount = new TreeMap<>();
        private List<String> entries = new ArrayList<>();

        private LeximinOrder(Map<String, List<Double>> prefCount) {
            this.prefCount.putAll(prefCount);
            entries.addAll(prefCount.keySet());
            entries.sort(null); //sort lexicographically on the string
            selectionSort(); //sort entries using selection sort
        }
        
        @Override
        public String toString() {
            String out = "";
            String last = "";
            for (String s : entries) {
                if (!last.equals("")) {
                    if (lessThan(prefCount.get(last), prefCount.get(s))) {
                        out += " < ";
                    } else {
                        out += " = ";
                    }
                }
                out += s;
                last = s;
            }
            return out;
        }

        private void selectionSort() {
            for (int i = 0; i < entries.size() - 1; i++) {
                int minIndex = i;
                for (int j = i + 1; j < entries.size(); j++) {
                    if (lessThan(prefCount.get(entries.get(j)), prefCount.get(entries.get(minIndex)))) {
                        minIndex = j;
                    }
                }
                swap(i, minIndex);
            }
        }

        private boolean lessThan(List<Double> first, List<Double> second) {
            if (first.size() != second.size()) {
                return false;
            }
            int index = first.size() - 1;
            while (index >= 0) {
                if (!Settings.doubleEqual(first.get(index), second.get(index)) && first.get(index) < second.get(index)) {
                    return true;
                }
                if (!Settings.doubleEqual(first.get(index), second.get(index)) && first.get(index) > second.get(index)) {
                    return false;
                }
                --index;
            }
            return false;
        }

        private void swap(int i, int minIndex) {
            String temp = entries.get(i);
            entries.set(i, entries.get(minIndex));
            entries.set(minIndex, temp);
        }
        
    }
}
