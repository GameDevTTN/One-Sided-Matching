/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm;

import MatchingAlgorithm.Auxiliary.InvalidPreferenceException;
import MatchingAlgorithm.Auxiliary.Permutation;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import MatchingAlgorithm.Auxiliary.ProbabilityMatrix;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;

/**
 *
 * @author ylo019
 */
public class Proportional implements iAlgorithm {

    @Override
    public iProbabilityMatrix solve(PreferenceProfile input, int agents) {
        ProbabilityMatrix pm = new ProbabilityMatrix(agents);
        for (int i = 0; i < agents; i++) {
            int[] match = new int[agents];
            for (int j = 0; j < agents; j++) {
                match[j] = (j+i) % agents + 1;
            }
            try {
                pm.addMatching(new Permutation(agents, match));
            } catch (InvalidPreferenceException ex) {
                throw new RuntimeException("Proportional: solve(PreferenceProfile, int): fails to add Matching");
            }
        }
        return pm;
    }

    @Override
    public String getName() {
        return "Prop";
    }
    
}
