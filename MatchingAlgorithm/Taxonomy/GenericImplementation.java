/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.Taxonomy;

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
public abstract class GenericImplementation extends DeterministicAlgorithm {
    
    protected abstract boolean hasMemory();
    
    protected abstract boolean insertNewProposalAsFirst();
    
    protected abstract boolean isStack();
    
    @Override
    protected int[] solve(Permutation priority, PreferenceProfile input, int agents) {
        iIterator pp = priority.getIterator();
        iProfileIterator ip = input.getIterator();
        //who has what object
        int[] obj = new int[agents];
        
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
        while (!agentOrder.isEmpty()) {
            int actingAgent = agentOrder.remove(0);
            int desiredItem = ip.getNext(actingAgent);
            if (!itemPref[desiredItem - 1].contains(actingAgent)) {
                if (insertNewProposalAsFirst()) {
                    itemPref[desiredItem - 1].add(0, actingAgent);
                } else {
                    if (!itemPref[desiredItem - 1].isEmpty() && !hasMemory()) {
                        itemPref[desiredItem - 1].add(itemPref[desiredItem - 1].size() - 1, actingAgent); //insert new pref just before the last steal
                    } else {
                        itemPref[desiredItem - 1].add(actingAgent); //insert new pref just before the last steal
                    }
                }
            }
               
            int temp = obj[desiredItem - 1];
            if (temp > 0) { //someone has it
                if (itemPref[desiredItem - 1].indexOf(actingAgent) < itemPref[desiredItem - 1].indexOf(temp)) {
                    PostBox.broadcast(new TakeItemEvent(actingAgent, desiredItem, temp));
                    if (isStack()) {
                        agentOrder.add(0, temp);
                    } else {
                        agentOrder.add(temp);
                    }
                    hasTaken[actingAgent - 1] = desiredItem; //takes item
                    obj[desiredItem - 1] = actingAgent; //acting agent have the item
                    hasTaken[temp - 1] = 0; //that agent loses item;
                } else {
                    //%%JL Should agent that failed a steal allowed to retry?
                    if (isStack()) {
                        agentOrder.add(0, actingAgent);
                    } else {
                        agentOrder.add(actingAgent);
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
                        if (obj[i] != 0) {
                            itemPref[i].add(obj[i]);
                        }
                    }
                }
            }
        }
        return hasTaken;
    }
    
}
