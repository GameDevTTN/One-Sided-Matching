/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.DeterministicAlgorithm.YankeeSwap.XStealPerRound;

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
//obsolete - not updated to ensure correctness
public class ThreeStealsPerRound extends DeterministicAlgorithm {

    @Override
    public String getName() {
        return "Yankee Swap 3S/R";
    }

    protected int stealsPerRound() {
        return 3;
    }
    
    @Override
    protected int[] solve(Permutation priority, PreferenceProfile input, int agents, int objects) {
        iIterator pp = priority.getIterator();
        iProfileIterator ip = input.getIterator();
        int[] obj = new int[agents];
        int stealsInRound = 0;
        boolean[][] alreadyStolen = new boolean[agents][agents];
        int[] hasTaken = new int[agents];
        while (pp.hasNext()) {
            int actingAgent = pp.getNext();
            if (hasTaken[actingAgent - 1] != 0) {
                continue; //if the agent has a present, go to next player
            }
            while (true) {
                ip.resetPointers();
                int desiredItem = ip.getNext(actingAgent);
                while (alreadyStolen[actingAgent - 1][desiredItem - 1] || (stealsInRound >= stealsPerRound() && obj[desiredItem - 1] != 0)) {
                    desiredItem = ip.getNext(actingAgent);
                }
                hasTaken[actingAgent - 1] = desiredItem; //takes item
                int temp = obj[desiredItem - 1];
                obj[desiredItem - 1] = actingAgent; //acting agent have the item
                if (temp > 0) { //someone has it
                    PostBox.broadcast(new TakeItemEvent(actingAgent, desiredItem, temp));
                    actingAgent = temp; //that player has the turn
                    alreadyStolen[temp - 1][desiredItem - 1] = true; //that player cannot steal back
                    ++stealsInRound; //count that as a steal
                } else {
                    PostBox.broadcast(new TakeItemEvent(actingAgent, desiredItem));
                    alreadyStolen = new boolean[agents][agents]; //reset the array
                    stealsInRound = 0;
                    break;
                }
            }
        }
        return hasTaken;
    }
    
}
