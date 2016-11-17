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
public class YankeeSwapStandard extends DeterministicAlgorithm {
    
   @Override
    protected int[] solve(Permutation priority, PreferenceProfile input, int agents) {
        iIterator pp = priority.getIterator();
        iProfileIterator ip = input.getIterator();
        int[] obj = new int[agents];
        boolean[][] alreadyStolen = new boolean[agents][agents];
        int[] hasTaken = new int[agents];
        while (pp.hasNext()) {
            int actingAgent = pp.getNext();
            while (true) {
                ip.resetPointers();
                int desiredItem = ip.getNext(actingAgent);
                while (alreadyStolen[actingAgent - 1][desiredItem - 1]) {
                    desiredItem = ip.getNext(actingAgent);
                }
                hasTaken[actingAgent - 1] = desiredItem; //takes item
                int temp = obj[desiredItem - 1];
                obj[desiredItem - 1] = actingAgent; //acting agent have the item
                if (temp > 0) { //someone has it
                    PostBox.broadcast(new TakeItemEvent(actingAgent, desiredItem, temp));
                    actingAgent = temp; //that player has the turn
                    alreadyStolen[temp - 1][desiredItem - 1] = true; //that player cannot steal back
                } else {
                    PostBox.broadcast(new TakeItemEvent(actingAgent, desiredItem));
                    alreadyStolen = new boolean[agents][agents]; //reset the array
                    break;
                }
            }
        }
        return hasTaken;
    }
    
    @Override
    public String getName() {
        return "YSS";
    }    
}
