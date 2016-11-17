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
public class RandomSerialDictatorship extends DeterministicAlgorithm {
    
    public RandomSerialDictatorship() {}

    @Override
    protected int[] solve(Permutation priority, PreferenceProfile input, int agents) {
        iIterator pp = priority.getIterator();
        iProfileIterator ip = input.getIterator();
        boolean[] obj = new boolean[agents];
        int[] hasTaken = new int[agents];
        pp.resetPointer();
        ip.resetPointers();
        while(pp.hasNext()) {
            int actingAgent = pp.getNext();
            while(ip.hasNext(actingAgent)) {
                int desiredObj = ip.getNext(actingAgent);
                if (!obj[desiredObj - 1]) {
                    obj[desiredObj - 1] = true;
                    hasTaken[actingAgent - 1] = desiredObj;
                    PostBox.broadcast(new TakeItemEvent(actingAgent, desiredObj));
                    break;
                }
            }
        }
        return hasTaken;
    }

    @Override
    public String getName() {
        return "RSD";
    }
    
}
