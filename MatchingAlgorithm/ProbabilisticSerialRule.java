/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm;

import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import Main.Settings.Format;
import Main.Settings.Settings;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import MatchingAlgorithm.Auxiliary.ProbabilisticProbabilityMatrix;
import MatchingAlgorithm.Auxiliary.TakeItemEvent;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;
import MatchingAlgorithm.Auxiliary.iProfileIterator;
import Pair.Pair;

/**
 *
 * @author ylo019
 */
public class ProbabilisticSerialRule implements iOneSidedAlgorithm {
    
    @Override
    public iProbabilityMatrix solve(PreferenceProfile input, int agents, int objects) {
        ProbabilisticProbabilityMatrix output = new ProbabilisticProbabilityMatrix(agents, objects);
        iProfileIterator ip = input.getIterator();
        double time = 0;
        double[] obj = new double[objects];
        int[] agentsCake = new int[agents];
        while (!Format.DoubleEqual(((double)objects)/agents, time) && time <= ((double)objects)/agents) {
            //each agent choose a cake
            int[] agentsOnCake = new int[agents];
            for (int i = 1; i <= input.size(); i++) {
                int desiredItem = agentsCake[i - 1];
                while (desiredItem == 0 || Format.DoubleEqual(1, obj[desiredItem - 1])) {
                    desiredItem = ip.getNext(i); //this shouldn't crash, but it may
                }
                ++agentsOnCake[desiredItem - 1];
                agentsCake[i - 1] = desiredItem;
                PostBox.broadcast(new TakeItemEvent(i, desiredItem));
            }
            //split the smallest share
            double min = 1;
            for (int i = 0; i < agents; i++) {
                if (agentsOnCake[i] <= 0)
                    continue;
                min = Math.min(min, (1 - obj[i])/agentsOnCake[i]);
            }
            time += min;
            for (int i = 0; i < agents; i++) {
                obj[i] += agentsOnCake[i] * min;
            }
            PostBox.broadcast(MessageType.PROCESS, new Pair<>("",Format.Format(min) + " time has passed. New time is " + Format.Format(time)));
            output.addMatching(agentsCake, min);
        }
        return output;
    }

    @Override
    public String getName() {
        return "PSR";
    }
    
}
