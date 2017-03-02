/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.Hybrid;

import MatchingAlgorithm.iOneSidedAlgorithm;
import MatchingAlgorithm.Auxiliary.EmptyMatrixException;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import MatchingAlgorithm.Auxiliary.ProbabilisticProbabilityMatrix;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;

/**
 *
 * @author ylo019
 */
public abstract class HybridAlgorithm implements iOneSidedAlgorithm {
    
    private iOneSidedAlgorithm first;
    private iOneSidedAlgorithm second;
    
    protected HybridAlgorithm(iOneSidedAlgorithm first, iOneSidedAlgorithm second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public iProbabilityMatrix solve(PreferenceProfile input, int agents, int objects) {
        ProbabilisticProbabilityMatrix ppm = new ProbabilisticProbabilityMatrix(agents);
        try {
            ppm.addPMatrix(first.solve(input, agents, objects));
            ppm.addPMatrix(second.solve(input, agents, objects));
        } catch (EmptyMatrixException ex) {
            throw new RuntimeException("HybridAlgorithm: solve(PP, int): Empty Matrix Exception");
        }
        return ppm;
    }

    @Override
    public String getName() {
        return "H-" + first.getName() + "+" + second.getName();
    }
    
}
