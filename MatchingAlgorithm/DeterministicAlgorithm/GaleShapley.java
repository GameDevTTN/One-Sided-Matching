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
//One sided Gale Shapley with uniform item preference === initial ordering of agents
//Same as RSD.
//obsolete - check code before using (only updated to allow compilation - no assurances of correctness or not throwing exceptions)
public class GaleShapley extends DeterministicAlgorithm {

    @Override
    public String getName() {
        return "Deferred Acceptance";
    }

    @Override
    protected int[] solve(Permutation priority, PreferenceProfile input, int agents, int objects) {
        iIterator pp = priority.getIterator();
        iProfileIterator ip = input.getIterator();
        int[] obj = new int[agents];
        int[] hasTaken = new int[agents];
        boolean hasUnpickedAgent = true;
        do { //rounds
            pp.resetPointer();
            while (pp.hasNext()) { //each agent
                int actingAgent = pp.getNext();
                if (hasTaken[actingAgent - 1] > 0) {
                    continue;
                }
                int desiredObject = ip.getNext(actingAgent);
                if (obj[desiredObject - 1] > 0) {
                    if (priority.isBefore(actingAgent, obj[desiredObject - 1])) {
                        int temp = obj[desiredObject - 1];
                        hasTaken[temp - 1] = 0;
                        hasTaken[actingAgent - 1] = desiredObject;
                        obj[desiredObject - 1] = actingAgent;
                        PostBox.broadcast(new TakeItemEvent(actingAgent, desiredObject, temp));
                    } else {
                        PostBox.broadcast(new TakeItemEvent(actingAgent, desiredObject, false));
                    }
                } else {
                    hasTaken[actingAgent - 1] = desiredObject;
                    obj[desiredObject - 1] = actingAgent;
                    PostBox.broadcast(new TakeItemEvent(actingAgent, desiredObject));
                }
            }
            hasUnpickedAgent = false;
            for (int i : hasTaken) {
                if (i == 0) {
                    hasUnpickedAgent = true;
                }
            }
        } while (hasUnpickedAgent);
        return hasTaken;
    }
    
}
