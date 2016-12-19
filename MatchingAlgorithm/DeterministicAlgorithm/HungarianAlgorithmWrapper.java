/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.DeterministicAlgorithm;

import MatchingAlgorithm.iAlgorithm;
import MatchingAlgorithm.Auxiliary.InvalidPreferenceException;
import MatchingAlgorithm.Auxiliary.Permutation;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import MatchingAlgorithm.Auxiliary.ProbabilityMatrix;
import MatchingAlgorithm.Auxiliary.iIterator;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;
import blogspot.software_and_algorithms.stern_library.optimization.HungarianAlgorithm;
import java.util.Arrays;

/**
 *
 * @author ylo019
 */

//this class uses the code from class blogspot.software_and_algorithms.stern_library.optimization written by Kevin L.Stern
public class HungarianAlgorithmWrapper implements iAlgorithm {

    @Override
    public iProbabilityMatrix solve(PreferenceProfile input, int agents, int objects) {
        double[][] preferences = new double[agents][objects];
        Permutation[] profiles = input.getProfiles();
        for (int i = 0; i < agents; i++) {
            iIterator iter = profiles[i].getIterator();
            applyWeights(preferences[i], iter);
        }
        ProbabilityMatrix pm = new ProbabilityMatrix(agents, objects);
        try {
            pm.addMatching(new Permutation(agents, objects, new HungarianAlgorithm(preferences).execute()));
        } catch (InvalidPreferenceException ex) {
            throw new RuntimeException("HungarianAlgorithmWrapper: solve(): matching generated is invalid");
        }
        return pm;
    }
    
    protected void applyWeights(double[] pref, iIterator iter) {
        int weight = 0;
        while (iter.hasNext()) {
            pref[iter.getNext() - 1] = weight++; //the Hungarian Algorithm look for min cost - I need a matching that generate max borda
        }
    }

    @Override
    public String getName() {
        return "HA";
    }
    
}
