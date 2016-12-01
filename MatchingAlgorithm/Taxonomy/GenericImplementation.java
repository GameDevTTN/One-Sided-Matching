/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.Taxonomy;

import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import MatchingAlgorithm.Auxiliary.Permutation;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import MatchingAlgorithm.Auxiliary.TakeItemEvent;
import MatchingAlgorithm.Auxiliary.iIterator;
import MatchingAlgorithm.Auxiliary.iProfileIterator;
import MatchingAlgorithm.DeterministicAlgorithm.DeterministicAlgorithm;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author ylo019
 */
public abstract class GenericImplementation extends DeterministicAlgorithm { //improved with GTTC
    
    private final int ROUND_PLACEHOLDER = -1;
    
    //default implementation - RSD - should be overridden regardless
    protected boolean hasMemory() {
        return true;
    }
    
    protected boolean insertNewProposalAsFirst() {
        return false;
    }
    
    protected boolean isStack() {
        return true;
    }
    
    private boolean isQueue() { //final
        return !isStack();
    }
    
    protected boolean hasDelayedMemory() {
        return false;
    }
    
    @Override
    protected int[] solve(Permutation priority, PreferenceProfile input, int agents) {
        iIterator pp = priority.getIterator();
        iProfileIterator ip = input.getIterator();
        //who has what object
        int[] obj = new int[agents];
        int[] delayedKnowledge = Arrays.copyOf(obj, agents); //if it does not have rounds or delayed memory, this should always not empty
        
        //datastructure for item preferences
        List<Integer>[] itemPref = new List[agents];
        for (int i = 0; i < itemPref.length; i++) {
            itemPref[i] = new ArrayList<Integer>();
        }
        
        //what has each agent got
        int[] hasTaken = new int[agents];
        //which agent still has to act
        List<Integer> agentOrder = new ArrayList<Integer>();
        while (pp.hasNext()) {
            agentOrder.add(pp.getNext());
        }
        if (isQueue() && hasDelayedMemory()) {
            agentOrder.add(ROUND_PLACEHOLDER);
        }
        while (!agentOrder.isEmpty()) {
            PostBox.broadcast(MessageType.DETAILS, Arrays.toString(agentOrder.toArray()));
            int actingAgent = agentOrder.remove(0);
            if (actingAgent == ROUND_PLACEHOLDER) {
                if (agentOrder.isEmpty()) {
                    break;
                } else {
                    delayedKnowledge = Arrays.copyOf(obj, agents);
                    agentOrder.add(ROUND_PLACEHOLDER);
                }
                continue; //skip the rest of the while loop
            }
            int desiredItem = ip.getNext(actingAgent);
            if (!itemPref[desiredItem - 1].contains(actingAgent)) {
                if (insertNewProposalAsFirst()) {
                    itemPref[desiredItem - 1].add(0, actingAgent);
                } else {
                    itemPref[desiredItem - 1].add(actingAgent);
                }
            }
            //if I know someone has the item & I will be rejected. don't propose and go back to the top of the queue
            //that belief is allowed to be wrong
            if (hasDelayedMemory() &&
                    delayedKnowledge[desiredItem - 1] > 0 //I know someone has it
                    && itemPref[desiredItem - 1].contains(actingAgent) //I know item's pref for me
                    && itemPref[desiredItem - 1].contains(delayedKnowledge[desiredItem - 1]) //I know item's pref for other
                    && itemPref[desiredItem - 1].indexOf(delayedKnowledge[desiredItem - 1]) < itemPref[desiredItem - 1].indexOf(actingAgent)) { //that person is ranked higher than me
                agentOrder.add(0, actingAgent); //do not propose and retake turn
                continue; //repeat the while loop
            }
            int temp = obj[desiredItem - 1];
            if (temp > 0) { //someone has it
                if (!itemPref[desiredItem - 1].contains(temp)) {
                    itemPref[desiredItem - 1].add(temp);
                }
                if (itemPref[desiredItem - 1].indexOf(actingAgent) < itemPref[desiredItem - 1].indexOf(temp)) {
                    PostBox.broadcast(new TakeItemEvent(actingAgent, desiredItem, temp));
                    if (isStack()) {
                        agentOrder.add(0, temp);
                    } else if (isQueue()) {
                        agentOrder.add(temp);
                    } else {
                        throw new RuntimeException(this.getClass() + ": solve(): " + "neither stack nor queue.");
                    }
                    hasTaken[actingAgent - 1] = desiredItem; //takes item
                    obj[desiredItem - 1] = actingAgent; //acting agent have the item
                    hasTaken[temp - 1] = 0; //that agent loses item;
                } else {
                    //%%JL Should agent that failed a steal allowed to retry?
                    if (isStack()) {
                        agentOrder.add(0, actingAgent);
                    } else if (isQueue()){
                        agentOrder.add(actingAgent);
                    } else {
                        throw new RuntimeException(this.getClass() + ": solve(): " + "neither stack nor queue.");
                    }
                }
            } else {
                hasTaken[actingAgent - 1] = desiredItem; //takes item
                obj[desiredItem - 1] = actingAgent; //acting agent have the item
                PostBox.broadcast(new TakeItemEvent(actingAgent, desiredItem));
                if (!hasMemory()) {
                    ip.resetPointers(); //reset the round
                    for (int i = 0; i < itemPref.length; i++) {
                        itemPref[i].clear();
                    }
                }
            }
        }
        return hasTaken;
    }
    
}
