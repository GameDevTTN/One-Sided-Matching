/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm;

import Main.Settings.Settings;
import MatchingAlgorithm.Auxiliary.InvalidPreferenceException;
import MatchingAlgorithm.Auxiliary.Permutation;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import MatchingAlgorithm.Auxiliary.ProbabilityMatrix;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;
import java.util.Arrays;

/**
 *
 * @author ylo019
 */
public class Proportional implements iAlgorithm {
    
    @Override
    public iProbabilityMatrix solve(PreferenceProfile input, int agents, int objects) {
        ProbabilityMatrix pm = new ProbabilityMatrix(agents, objects);
        for (int i = 0; i < agents; i++) {
            int[] match = new int[agents];
            for (int j = 0; j < agents; j++) {
                int val = (j+i) % agents + 1;
                match[j] = (val <= objects ? val : Settings.NULL_ITEM);
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
