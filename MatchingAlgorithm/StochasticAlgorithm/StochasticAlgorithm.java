/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.StochasticAlgorithm;

import ordinalpreferencegenerator.StaticFunctions;
import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import MatchingAlgorithm.iAlgorithm;
import MatchingAlgorithm.Auxiliary.EmptyMatrixException;
import MatchingAlgorithm.Auxiliary.Permutation;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import MatchingAlgorithm.Auxiliary.ProbabilisticProbabilityMatrix;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;
import Pair.Pair;

/**
 *
 * @author ylo019
 */
public abstract class StochasticAlgorithm implements iAlgorithm {

    @Override
    public iProbabilityMatrix solve(PreferenceProfile input, int agents, int objects) {
        ProbabilisticProbabilityMatrix output = new ProbabilisticProbabilityMatrix(agents);
        Permutation[] priority = StaticFunctions.permutations(agents);
        for (Permutation p : priority) {
            PostBox.broadcast(MessageType.PROCESS, new Pair<>("Priority", p));
            iProbabilityMatrix result = solve(p, input, agents, objects); //my implementation of Permutation.getIterator() always returns PermutationIterator
            try {
                output.addPMatrix(result);
            } catch (EmptyMatrixException ex) {
                throw new RuntimeException("SA: solve(): probability matrix generated is empty");
            }
        }
        return output;
    }

    @Override
    public abstract String getName();
    
    protected abstract iProbabilityMatrix solve(Permutation priority, PreferenceProfile input, int agents, int objects);
}
