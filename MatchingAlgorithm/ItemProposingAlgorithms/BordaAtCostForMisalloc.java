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
public class BordaAtCostForMisalloc extends DeterministicAlgorithm {
    
    private iUtilitiesModel model = new ExponentialModel(0.0);

    @Override
    public String getName() {
        return "ItemProposing DecreasingCostForMisalloc AnonAgent NotAnonItem";
    }

    //current implementation: agents are anonymous, items are not
    @Override
    protected int[] solve(Permutation priority, PreferenceProfile input, int agents, int objects) {
        int[] inversePriority = priority.inverse().getArray();
        int[] out = new int[agents];
        double[][] topTwoItems = new double[objects][2];
        double[] scores = model.getUtilities(objects);
        for (Permutation p : input.getProfiles()) {
            int[] array = p.getArray();
            for (int i = 0; i < p.size(); i++) {
                if (scores[i] > topTwoItems[array[i] - 1][1]) {
                    topTwoItems[array[i] - 1][1] = scores[i];
                    if (topTwoItems[array[i] - 1][1] > topTwoItems[array[i] - 1][0]) {
                        topTwoItems[array[i] - 1] = new double[]{topTwoItems[array[i] - 1][1], topTwoItems[array[i] - 1][0]};
                    }
                }
            }
        }
        double[] difference = new double[objects];
        for (int i = 0; i < difference.length; i++) {
            difference[i] = topTwoItems[i][0] - topTwoItems[i][1];
            if (difference[i] < 0) {
                System.out.println("ERROR" + Arrays.toString(topTwoItems[i]));
                System.exit(-1);
            }
        }
        for (int j = 0; j < objects; j++) {
            int actor = 0;
            for (int k = 0; k < objects; k++) {
                if (actor == 0 || difference[k] > difference[actor - 1]) {
                    actor = k + 1;
                }
            }
            difference[actor - 1] = Double.NEGATIVE_INFINITY;
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
