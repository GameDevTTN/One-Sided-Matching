/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.DeterministicAlgorithm;

import ordinalpreferencegenerator.StaticFunctions;
import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import MatchingAlgorithm.iOneSidedAlgorithm;
import MatchingAlgorithm.Auxiliary.InvalidPreferenceException;
import MatchingAlgorithm.Auxiliary.Permutation;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import MatchingAlgorithm.Auxiliary.ProbabilityMatrix;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;
import Pair.Pair;

/**
 *
 * @author ylo019
 */
//Run every permutation for inital order of agents if FIXED_ORDER set to true, otherwise run the numerical order for agent
public abstract class DeterministicAlgorithm implements iOneSidedAlgorithm {
    
    private boolean fixedOrder = false;
    
    public void setFixInitialOrder(boolean fixedOrder) {
        this.fixedOrder = fixedOrder;
    }
    
    public boolean getFixInitialOrder() {
        return fixedOrder;
    }
    
    @Override
    public iProbabilityMatrix solve(PreferenceProfile input, int agents, int objects) {
        ProbabilityMatrix output = new ProbabilityMatrix(agents, objects);
        Permutation[] priority = null;
        //hack code --- //20/01/17 This is now a feature
        if (fixedOrder) {
            priority = new Permutation[1];
            int[] temp = new int[agents];
            for (int i = 0; i < agents; i++) {
                temp[i] = i + 1;
            }
            try {
                priority[0] = new Permutation(agents, agents, temp);
            } catch (InvalidPreferenceException ex) {
                throw new RuntimeException("temp error");
            }
        } else {
            priority = StaticFunctions.permutations(agents);
        }
        //    ----//
        
        for (Permutation p : priority) {
            PostBox.broadcast(MessageType.PROCESS, new Pair<>("Priority", p));
            int[] result = solve(p, input, agents, objects); //my implementation of Permutation.getIterator() always returns PermutationIterator
            try {
                Permutation matching = improve(new Permutation(agents, objects, result), input);
                PostBox.broadcast(MessageType.PROCESS, new Pair<>("Matching", matching));
                output.addMatching(matching);
            } catch (InvalidPreferenceException ex) {
                throw new RuntimeException("DA: solve(): matching generated is invalid");
            }
        }
        return output;
    }
    
    @Override
    public abstract String getName();
    
    protected abstract int[] solve(Permutation priority, PreferenceProfile input, int agents, int objects);

    protected Permutation improve(Permutation result, PreferenceProfile input) {
        return result;
    }
    
}
