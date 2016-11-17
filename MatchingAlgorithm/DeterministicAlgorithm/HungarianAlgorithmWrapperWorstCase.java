/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.DeterministicAlgorithm;

import MatchingAlgorithm.Auxiliary.iIterator;

/**
 *
 * @author ylo019
 */
public class HungarianAlgorithmWrapperWorstCase extends HungarianAlgorithmWrapper {
    
    @Override
    protected void applyWeights(double[] pref, iIterator iter) {
        int weight = pref.length;
        while (iter.hasNext()) {
            pref[iter.getNext() - 1] = weight--; //the Hungarian Algorithm look for min cost - I need a matching that generate max borda
        }
    }

    @Override
    public String getName() {
        return "HA Worst";
    }
    
}
