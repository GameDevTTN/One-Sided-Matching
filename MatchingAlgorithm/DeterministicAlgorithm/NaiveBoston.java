/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.DeterministicAlgorithm;

import Main.Observers.System.PostBox;
import MatchingAlgorithm.Auxiliary.Permutation;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import MatchingAlgorithm.Auxiliary.TakeItemEvent;
import MatchingAlgorithm.Auxiliary.iIterator;
import MatchingAlgorithm.Auxiliary.iProfileIterator;

/**
 *
 * @author ylo019
 */
public class NaiveBoston extends DeterministicAlgorithm {
    
    public NaiveBoston() {}

    @Override
    protected int[] solve(Permutation priority, PreferenceProfile input, int agents) {
        iIterator pp = priority.getIterator();
        iProfileIterator ip = input.getIterator();
        boolean[] obj = new boolean[agents];
        int[] hasTaken = new int[agents];
        for (int i = 0; i < agents; i++) { //rounds
            pp.resetPointer();
            while (pp.hasNext()) { //each agent
                int actingAgent = pp.getNext();
                if (hasTaken[actingAgent - 1] > 0) {
                    continue;
                }
                int desiredObject = ip.getNext(actingAgent);
                if (obj[desiredObject - 1]) {
                    PostBox.broadcast(new TakeItemEvent(actingAgent, desiredObject, false));
                } else {
                    hasTaken[actingAgent - 1] = desiredObject;
                    obj[desiredObject - 1] = true;
                    PostBox.broadcast(new TakeItemEvent(actingAgent, desiredObject));
                }
            }
        }
        return hasTaken;
    }

    @Override
    public String getName() {
        return "NB";
    }
    
}
