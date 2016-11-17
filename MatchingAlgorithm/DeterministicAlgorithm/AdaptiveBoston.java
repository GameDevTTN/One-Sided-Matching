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
public class AdaptiveBoston extends DeterministicAlgorithm {
    
    public AdaptiveBoston() {}
    
    @Override
    protected int[] solve(Permutation priority, PreferenceProfile input, int agents) {
        iIterator pp = priority.getIterator();
        iProfileIterator ip = input.getIterator();
        boolean[] obj = new boolean[agents];
        boolean[] justGone = new boolean[agents];
        int[] hasTaken = new int[agents];
        for (int i = 0; i < agents; i++) { //rounds
            pp.resetPointer();
            while (pp.hasNext()) { //each agent
                int actingAgent;
                actingAgent = pp.getNext();
                if (hasTaken[actingAgent - 1] == 0 && ip.hasNext(actingAgent)) { //technically redundant, but useful for incomplete preferences
                    int desiredObject = ip.getNext(actingAgent);
                    while (obj[desiredObject - 1]) {
                        if (!ip.hasNext(actingAgent)) {
                            throw new RuntimeException("AB: solve(): actingAgent has no more preferences");
                        }
                        desiredObject = ip.getNext(actingAgent);
                    }
                    if (!justGone[desiredObject - 1]) {
                        hasTaken[actingAgent - 1] = desiredObject;
                        justGone[desiredObject - 1] = true;
                        PostBox.broadcast(new TakeItemEvent(actingAgent, desiredObject));
                    } else {
                        PostBox.broadcast(new TakeItemEvent(actingAgent, desiredObject, false));
                    }
                }
            }
            System.arraycopy(justGone, 0, obj, 0, obj.length);
        }
        return hasTaken;
    }

    @Override
    public String getName() {
        return "AB";
    }
    
}
