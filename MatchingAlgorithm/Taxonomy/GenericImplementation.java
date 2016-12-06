/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.Taxonomy;

import Main.Observers.AlgorithmObserver;
import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import MatchingAlgorithm.Auxiliary.Permutation;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import MatchingAlgorithm.Auxiliary.TakeItemEvent;
import MatchingAlgorithm.Auxiliary.iIterator;
import MatchingAlgorithm.Auxiliary.iProfileIterator;
import MatchingAlgorithm.DeterministicAlgorithm.DeterministicAlgorithm;
import MatchingAlgorithm.DeterministicAlgorithm.GTTCImprovement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author ylo019
 */
public class GenericImplementation extends DeterministicAlgorithm { 

    public static void initAll() {
        for (int i = 0; i < Math.pow(2,5); i++) {
            Boolean[] params = new Boolean[5];
            for (int j = 0; j < params.length; j++) {
                params[j] = (i/((int)Math.pow(2,j))) % 2 == 0;
            }
            //if memory + sharedpref then RSD
            if (params[0] == true && params[4] == true) {
                continue;
            }
            //if stack + delaymemory = return (not implemented)
            if (params[2] == true && params[3] == true) {
                continue;
            }
            //remove specifc cases
            if (params[0] == false && params[2] == true && params[4] == true) { //memory stack share pref
                continue;
            }
            //Memory AcceptLast Queue DelayMemory NoSharePref
            if (i == 22) {
                continue;
            }
            //removing all delay memory and/or sharePref
            if (params[3] == true || params[4] == true) {
                continue;
            }
            new AlgorithmObserver(GenericImplementation.class, params).init();
            new AlgorithmObserver(new GTTCImprovement(new GenericImplementation(params[0], params[1], params[2], params[3], params[4]))).init();
        }
    }
//improved with GTTC
    
    private final int ROUND_PLACEHOLDER = -1;
    
    
    private boolean hasMemory = true;
    private boolean acceptFirst = true;
    private boolean stack = true;
    private boolean delayedKnowledge = false;
    private boolean sameItemPref = false;
    
    protected GenericImplementation() {
        
    }
    //default implementation - RSD - should be overridden regardless
    public GenericImplementation(boolean memory, boolean acceptFirst, boolean stack, boolean delayedKnowledge, boolean sameItemPref) {
        this.hasMemory = memory;
        this.acceptFirst = acceptFirst;
        this.stack = stack;
        this.delayedKnowledge = delayedKnowledge;
        this.sameItemPref = sameItemPref;
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
    
    private boolean hasDelayedMemory() {
        return delayedKnowledge;
    }
    
    private boolean itemSharePreferences() {
        return sameItemPref;
    }
    
    private int getItemPrefIndex(int desiredItem) {
        return (itemSharePreferences() ? 0 : desiredItem - 1);
    }
    
    @Override
    public String getName() {
        return (hasMemory ? "Memory" : "NoMemory") + " " +
                (acceptFirst ? "AcceptFirst" : "AcceptLast") + " " +
                (stack ? "Stack" : "Queue") + " " +
                (delayedKnowledge ? "DelayMemory" : "NoDelayMemory") + " " +
                (sameItemPref ? "SharePref" : "NoSharePref");
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
                    delayedKnowledge[getItemPrefIndex(desiredItem)] > 0 //I know someone has it
                    && itemPref[getItemPrefIndex(desiredItem)].contains(actingAgent) //I know item's pref for me
                    && itemPref[getItemPrefIndex(desiredItem)].contains(delayedKnowledge[desiredItem - 1]) //I know item's pref for other
                    && itemPref[getItemPrefIndex(desiredItem)].indexOf(delayedKnowledge[desiredItem - 1]) < itemPref[getItemPrefIndex(desiredItem)].indexOf(actingAgent)) { //that person is ranked higher than me
                agentOrder.add(0, actingAgent); //do not propose and retake turn
                continue; //repeat the while loop
            }
            int temp = obj[desiredItem - 1];
            if (temp > 0) { //someone has it
                if (!itemPref[getItemPrefIndex(desiredItem)].contains(temp)) {
                    itemPref[getItemPrefIndex(desiredItem)].add(temp);
                }
                if (itemPref[getItemPrefIndex(desiredItem)].indexOf(actingAgent) < itemPref[getItemPrefIndex(desiredItem)].indexOf(temp)) {
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
