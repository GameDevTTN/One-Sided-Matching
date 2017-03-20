/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.ItemProposingAlgorithms;

import MatchingAlgorithm.Auxiliary.Permutation;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import MatchingAlgorithm.DeterministicAlgorithm.DeterministicAlgorithm;
import UtilityModels.ExponentialModel;
import UtilityModels.iUtilitiesModel;
import java.util.Arrays;

/**
 *
 * @author ylo019
 */
public class BordaAtIncreasingAverage extends DeterministicAlgorithm {
    
    private iUtilitiesModel model = new ExponentialModel(0.0);
    protected boolean increasingAverage = true;

    @Override
    public String getName() {
        return "ItemProposing IncreasingBorda AnonAgent NotAnonItem";
    }

    //current implementation: agents are anonymous, items are not
    @Override
    protected int[] solve(Permutation priority, PreferenceProfile input, int agents, int objects) {
        int[] inversePriority = priority.inverse().getArray();
        int[] out = new int[agents];
        double[] cumulativeCount = new double[objects];
        double[] scores = model.getUtilities(objects);
        for (Permutation p : input.getProfiles()) {
            int[] array = p.getArray();
            for (int i = 0; i < p.size(); i++) {
                if (increasingAverage) {
                    cumulativeCount[array[i] - 1] += scores[i];
                } else {
                    cumulativeCount[array[i] - 1] -= scores[i];
                }
            }
        }
        for (int j = 0; j < objects; j++) {
            int actor = 0;
            for (int k = 0; k < objects; k++) {
                if (actor == 0 || cumulativeCount[k] < cumulativeCount[actor - 1]) {
                    actor = k + 1;
                }
            }
            cumulativeCount[actor - 1] = Double.POSITIVE_INFINITY;
            int agent = 0;
            Permutation[] perms = input.getProfiles();
            for (int k = 0; k < agents; k++) {
                int rankForK = (out[k] > 0 ? Integer.MAX_VALUE : perms[k].inverse().getArray()[actor - 1]);
                int rankForAgent = (agent == 0 ? Integer.MAX_VALUE - 1 : perms[agent - 1].inverse().getArray()[actor - 1]);
                if (rankForK < rankForAgent || (rankForK == rankForAgent && inversePriority[k] < inversePriority[agent - 1])) {
                    agent = k + 1;
                }
            }
            out[agent - 1] = actor;
        }
        return out;
    }
    
}
