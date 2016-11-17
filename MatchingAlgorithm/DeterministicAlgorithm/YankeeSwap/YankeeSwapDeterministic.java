/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.DeterministicAlgorithm.YankeeSwap;

import Main.Observers.System.PostBox;
import MatchingAlgorithm.Auxiliary.Permutation;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import MatchingAlgorithm.Auxiliary.TakeItemEvent;
import MatchingAlgorithm.Auxiliary.iIterator;
import MatchingAlgorithm.Auxiliary.iProfileIterator;
import MatchingAlgorithm.DeterministicAlgorithm.DeterministicAlgorithm;

/**
 *
 * @author ylo019
 */
public class YankeeSwapDeterministic extends DeterministicAlgorithm {

    @Override
    protected int[] solve(Permutation priority, PreferenceProfile input, int agents) {
        iIterator pp = priority.getIterator();
        iProfileIterator ip = input.getIterator();
        int[] obj = new int[agents];
        boolean[] alreadyStolen = new boolean[agents];
        int[] hasTaken = new int[agents];
        while (pp.hasNext()) {
            ip.resetPointers();
            int actingAgent = pp.getNext();
            while (true) {
                int desiredItem = ip.getNext(actingAgent);
                while (alreadyStolen[desiredItem - 1]) {
                    desiredItem = ip.getNext(actingAgent);
                }
                hasTaken[actingAgent - 1] = desiredItem; //takes item
                int temp = obj[desiredItem - 1];
                obj[desiredItem - 1] = actingAgent; //acting agent have the item
                alreadyStolen[desiredItem - 1] = true;
                if (temp > 0) { //someone has it
                    PostBox.broadcast(new TakeItemEvent(actingAgent, desiredItem, temp));
                    actingAgent = temp; //that player has the turn
                } else {
                    PostBox.broadcast(new TakeItemEvent(actingAgent, desiredItem));
                    alreadyStolen = new boolean[agents]; //reset the array
                    break;
                }
            }
        }
        return hasTaken;
    }
    
    @Override
    public String getName() {
        return "Yankee Swap Deterministic";
    }
    
}
