/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.StochasticAlgorithm;

import ordinalpreferencegenerator.StaticFunctions;
import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import Main.Settings.Settings;
import MatchingAlgorithm.Auxiliary.InvalidPreferenceException;
import MatchingAlgorithm.Auxiliary.Permutation;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import MatchingAlgorithm.Auxiliary.ProbabilityMatrix;
import MatchingAlgorithm.Auxiliary.TakeItemEvent;
import MatchingAlgorithm.Auxiliary.iIterator;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;
import MatchingAlgorithm.Auxiliary.iProfileIterator;
import Pair.Pair;

/**
 *
 * @author ylo019
 */
public class YankeeSwapStochastic extends StochasticAlgorithm {
    
    protected double k = 0.0;
    
    public YankeeSwapStochastic(double newK) {
        k = newK;
    }
    
    @Override
    protected iProbabilityMatrix solve(Permutation priority, PreferenceProfile input, int agents) {
        ProbabilityMatrix output = new ProbabilityMatrix(agents);
        Permutation[] presentOrder = StaticFunctions.permutations(agents);
        for (Permutation p : presentOrder) {
            PostBox.broadcast(MessageType.PROCESS, new Pair<>("Present Order", p));
            int[] result = solveFixedPresentOrder(priority, p, input, agents); //my implementation of Permutation.getIterator() always returns PermutationIterator
            try {
                output.addMatching(new Permutation(agents, result));
            } catch (InvalidPreferenceException ex) {
                throw new RuntimeException("YSS: solve(): matching generated is invalid");
            }
        }
        return output;
    }
    
    private int[] solveFixedPresentOrder(Permutation priority, Permutation presentOrder, PreferenceProfile input, int agents) {
        iIterator pp = priority.getIterator();
        iIterator prp = presentOrder.getIterator();
        iProfileIterator ip = input.getIterator();
        int[] obj = new int[agents]; //location of objects
        boolean[] alreadyStolen = new boolean[agents]; //marker for already stolen object
        int[] hasTaken = new int[agents]; //the item in the hands of each agent
        while (pp.hasNext()) {
            ip.resetPointers();
            int actingAgent = pp.getNext();
            while (true) {
                int itemBetterThanBest = 0;
                int itemWorseThanBest = 0;
                int desiredItem = 0;
                while (ip.hasNext(actingAgent)) { //scan the actingAgent's preference to use rule to decide whether to open
                    int itemInvestigating = ip.getNext(actingAgent);
                    if (desiredItem == 0) { //no target yet
                        if (obj[itemInvestigating - 1] == 0) {
                            //unopened
                            ++itemBetterThanBest;
                        } else {
                            //opened
                            if (!alreadyStolen[itemInvestigating - 1]) {
                                desiredItem = itemInvestigating;
                            }
                        }
                    } else { //already has a target
                        if (obj[itemInvestigating - 1] == 0) {
                            //unopened
                            ++itemWorseThanBest;
                        }
                    }
                }
                //apply rule
                if (rule(desiredItem, itemBetterThanBest, itemWorseThanBest)) {
                    //open
                    int receivedItem = prp.getNext();
                    while (obj[receivedItem - 1] > 0) { //someone has the new present
                        receivedItem = prp.getNext();
                    }
                    hasTaken[actingAgent - 1] = receivedItem;
                    obj[receivedItem - 1] = actingAgent;
                    PostBox.broadcast(new TakeItemEvent(actingAgent, receivedItem));
                    alreadyStolen = new boolean[agents]; //reset the array
                    break; //breaks the while(true)
                } else {
                    //steal
                    hasTaken[actingAgent - 1] = desiredItem;
                    int temp = obj[desiredItem - 1]; //last owner
                    obj[desiredItem - 1] = actingAgent;
                    alreadyStolen[desiredItem - 1] = true;
                    PostBox.broadcast(new TakeItemEvent(actingAgent, desiredItem, temp));
                    actingAgent = temp;
                }
            }
        }
        return hasTaken;
    }
    
    @Override
    public String getName() {
        return "Yankee Swap Stochastic: " + Settings.format(k);
    }

    private boolean rule(int desiredItem, int itemBetterThanBest, int itemWorseThanBest) {
        if (desiredItem == 0) {
            return true; //open if nothing stealable
        }
        if ((double)itemBetterThanBest/(itemBetterThanBest + itemWorseThanBest) > k) {
            return true; //if any unopened present is better than best steal - open
        }
        return false;
    }
    
}
