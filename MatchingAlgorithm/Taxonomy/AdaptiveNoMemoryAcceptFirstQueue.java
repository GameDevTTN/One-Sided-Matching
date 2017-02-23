/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.Taxonomy;

import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import Main.Settings.Settings;
import MatchingAlgorithm.Auxiliary.Permutation;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import MatchingAlgorithm.Auxiliary.Restrictions.iRestriction;
import MatchingAlgorithm.Auxiliary.Restrictions.iRestrictionFactory;
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
public class AdaptiveNoMemoryAcceptFirstQueue extends DeterministicAlgorithm {
    private final int ROUND_PLACEHOLDER = -1;
    
    
    private boolean hasMemory = true;
    private boolean acceptFirst = true;
    private boolean stack = true;
    private boolean delayedMemory = true;
    
    //default implementation - RSD - should be overridden regardless
    public AdaptiveNoMemoryAcceptFirstQueue() {
        this.hasMemory = false;
        this.acceptFirst = true;
        this.stack = false;
        this.delayedMemory = true;
        this.factory = new iRestrictionFactory() {
            @Override
            public iRestriction[] getRestrictions(int agent, int item) {
                return new iRestriction[]{};
            }
        };
        clearRestriction(0, 0);
    }
    
    //default implementation - RSD - should be overridden regardless
    private boolean hasMemory() {
        return hasMemory;
    }
    
    private boolean insertNewProposalAsFirst() {
        return !acceptFirst; //the negation is intentional. accept first = not insert new proposal at start
    }
    
    private boolean isStack() {
        return stack;
    }
    
    private boolean isQueue() { //final
        return !isStack();
    }
    
   
    private int getItemPrefIndex(int desiredItem) {
        return (desiredItem - 1);
    }
    
    private boolean hasDelayedMemory() {
        return delayedMemory;
    }
    
    @Override
    public String getName() {
        return "No Memory Adaptive Boston";
    }
    
    iRestrictionFactory factory = null;
    List<iRestriction> restrictions = new ArrayList<iRestriction>() {
        @Override
        public String toString() {
            String output = "";
            for (iRestriction r : this) {
                output += (r.toString() + " ");
            }
            return output;
        }
    };
    @Override
    protected int[] solve(Permutation priority, PreferenceProfile input, int agents, int objects) {
        clearRestriction(agents, objects);
        iIterator pp = priority.getIterator();
        iProfileIterator ip = input.getIterator();
        //who has what object
        int[] obj = new int[objects];
        int[] memory = Arrays.copyOf(obj, agents); //if it does not have rounds or delayed memory, this should always not empty
        
        //datastructure for item preferences
        List<Integer>[] itemPref = new List[objects];
        for (int i = 0; i < itemPref.length; i++) {
            itemPref[i] = new ArrayList<>();
        }
        
        //what has each agent got
        int[] hasTaken = new int[agents];
        //which agent still has to act
        List<Integer> agentOrder = new ArrayList<>();
        while (pp.hasNext()) {
            agentOrder.add(pp.getNext());
        }
        agentOrder.add(ROUND_PLACEHOLDER);
        while (!agentOrder.isEmpty()) {
            PostBox.broadcast(MessageType.DETAILS, Arrays.toString(agentOrder.toArray()));
            int actingAgent = agentOrder.remove(0);
            if (actingAgent == ROUND_PLACEHOLDER) {
                if (agentOrder.isEmpty()) {
                    break;
                } else {
                    memory = Arrays.copyOf(obj, agents);
                    agentOrder.add(ROUND_PLACEHOLDER);
                }
                continue; //skip the rest of the while loop
            }
            int desiredItem = (ip.hasNext(actingAgent)? ip.getNext(actingAgent) : Settings.NULL_ITEM);
            if (desiredItem == Settings.NULL_ITEM) {
                hasTaken[actingAgent - 1] = Settings.NULL_ITEM;
                continue;
            }
            if (!itemPref[getItemPrefIndex(desiredItem)].contains(actingAgent)) {
                if (insertNewProposalAsFirst()) {
                    itemPref[getItemPrefIndex(desiredItem)].add(0, actingAgent);
                } else {
                    itemPref[getItemPrefIndex(desiredItem)].add(actingAgent);
                }
            }
            //if I know someone has the item & I will be rejected. don't propose and go back to the top of the queue
            //that belief is allowed to be wrong
            if (hasDelayedMemory() &&
                    memory[getItemPrefIndex(desiredItem)] > 0 //I know someone has it
                    && itemPref[getItemPrefIndex(desiredItem)].contains(actingAgent) //I know item's pref for me
                    && itemPref[getItemPrefIndex(desiredItem)].contains(memory[desiredItem - 1]) //I know item's pref for other
                    && itemPref[getItemPrefIndex(desiredItem)].indexOf(memory[desiredItem - 1]) < itemPref[getItemPrefIndex(desiredItem)].indexOf(actingAgent)) { //that person is ranked higher than me
                agentOrder.add(0, actingAgent); //do not propose and retake turn
                continue; //repeat the while loop
            }
            int temp = obj[desiredItem - 1];
            if (temp > 0) { //someone has it
                if (!itemPref[getItemPrefIndex(desiredItem)].contains(temp)) {
                    itemPref[getItemPrefIndex(desiredItem)].add(temp);
                }
                if (itemPref[getItemPrefIndex(desiredItem)].indexOf(actingAgent) < itemPref[getItemPrefIndex(desiredItem)].indexOf(temp) && checkRestriction(actingAgent, desiredItem, temp)) {
                    PostBox.broadcast(new TakeItemEvent(actingAgent, desiredItem, temp));
                    updateRestriction(actingAgent, desiredItem, temp);
                    if (isStack()) {
                        agentOrder.add(0, temp);
                    } else if (isQueue()) {
                        agentOrder.add(temp);
                    } else {
                        throw new RuntimeException(this.getClass() + ": solve(): " + "neither stack nor queue.");
                    }
                    hasTaken[actingAgent - 1] = desiredItem; //acting agent takes item
                    obj[desiredItem - 1] = actingAgent; //acting agent have the item
                    hasTaken[temp - 1] = 0; //previous agent loses item;
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
                updateRestriction(actingAgent, desiredItem, temp);
                if (!hasMemory()) {
                    ip.resetPointers(); //reset the round
                    if (agentOrder.remove(Integer.valueOf(ROUND_PLACEHOLDER))) {//reset the position of round placeholder
                        agentOrder.add(ROUND_PLACEHOLDER); //add placeholder at the end
                    } else {
                        throw new RuntimeException("missing round placeholder");
                    }
                    for (List<Integer> itemPref1 : itemPref) {
                        itemPref1.clear();
                    }
                }
            }
        }
        return hasTaken;
    }
 
    //hack code
    private boolean checkRestriction(int actingAgent, int item, int currentAgent) {
        for (iRestriction r : restrictions) {
            if (!r.attemptToTake(actingAgent, item, currentAgent)) {
                return false;
            }
        }
        return true;
    }

    private void updateRestriction(int actingAgent, int item, int currentAgent) {
        for (iRestriction r : restrictions) {
            r.take(actingAgent, item, currentAgent);
        }
    }
    
    private void clearRestriction(int agents, int items) {
        restrictions.clear();
        if (factory == null) {
            return;
        }
        for (iRestriction r : factory.getRestrictions(agents, items)) {
            restrictions.add(r);
        }
    }
    //end hack code
}

