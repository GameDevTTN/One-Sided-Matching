/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.DeterministicAlgorithm;

import MatchingAlgorithm.Auxiliary.Permutation;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import MatchingAlgorithm.DeterministicAlgorithm.improvementAlgorithms.GTTC;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ylo019
 */
public abstract class GTTCImprovement<S extends DeterministicAlgorithm> extends DeterministicAlgorithm {
    
    private DeterministicAlgorithm inner;
    
    protected GTTCImprovement (Class<S> clazz) {
        try {
            inner = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(GTTCImprovement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected int[] solve(Permutation priority, PreferenceProfile input, int agents) {
        return inner.solve(priority, input, agents);
    }
    
    @Override
    protected Permutation improve(Permutation result, PreferenceProfile input) {
        return GTTC.improve(result, input);
    }
    
    @Override
    public String getName() {
        return inner.getName() + "+GTTC";
    }
}
