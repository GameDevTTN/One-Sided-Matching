/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.Taxonomy;

import Main.Observers.OneSidedAlgorithmObserver;
import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import Main.Settings.Settings;
import MatchingAlgorithm.Auxiliary.Permutation;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import MatchingAlgorithm.Auxiliary.Restrictions.*;
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
//restriction code correct but outdated
public class GenericImplementation extends DeterministicAlgorithm { 

    
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
        this.factory = new RestrictionFactoryAdaptor();
        this.factory.clearRestriction(0, 0);
    }
    
    public GenericImplementation(boolean memory, boolean acceptFirst, boolean stack, boolean delayedKnowledge, boolean sameItemPref, iRestrictionFactory factory) {
        this(memory, acceptFirst, stack, delayedKnowledge, sameItemPref);
        if (factory != null) {
            this.factory = factory;
        } else {
            this.factory = new RestrictionFactoryAdaptor();
        }
        this.factory.clearRestriction(0, 0);
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
                //(delayedKnowledge ? "DelayMemory" : "NoDelayMemory") + " " +
                //(sameItemPref ? "SharePref" : "NoSharePref") + " " +
                (factory.toString());
    }
    
    iRestrictionFactory factory = null;
    
    //this method has no side-effects, so it is safe to expose
    @Override
    public int[] solve(Permutation priority, PreferenceProfile input, int agents, int objects) {
        factory.clearRestriction(agents, objects);
        iIterator pp = priority.getIterator();
        iProfileIterator ip = input.getIterator();
        //who has what object
        int[] obj = new int[objects];
        int[] delayedMemory = Arrays.copyOf(obj, agents); //if it does not have rounds or delayed memory, this should always not empty
        
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
                    delayedMemory = Arrays.copyOf(obj, agents);
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
                    delayedMemory[getItemPrefIndex(desiredItem)] > 0 //I know someone has it
                    && itemPref[getItemPrefIndex(desiredItem)].contains(actingAgent) //I know item's pref for me
                    && itemPref[getItemPrefIndex(desiredItem)].contains(delayedMemory[desiredItem - 1]) //I know item's pref for other
                    && itemPref[getItemPrefIndex(desiredItem)].indexOf(delayedMemory[desiredItem - 1]) < itemPref[getItemPrefIndex(desiredItem)].indexOf(actingAgent)) { //that person is ranked higher than me
                agentOrder.add(0, actingAgent); //do not propose and retake turn
                continue; //repeat the while loop
            }
            int temp = obj[desiredItem - 1];
            if (temp > 0) { //someone has it
                if (!itemPref[getItemPrefIndex(desiredItem)].contains(temp)) {
                    itemPref[getItemPrefIndex(desiredItem)].add(temp);
                }
                if (itemPref[getItemPrefIndex(desiredItem)].indexOf(actingAgent) < itemPref[getItemPrefIndex(desiredItem)].indexOf(temp) && factory.checkRestriction(actingAgent, desiredItem, temp)) {
                    PostBox.broadcast(new TakeItemEvent(actingAgent, desiredItem, temp));
                    factory.updateRestriction(actingAgent, desiredItem, temp);
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
                factory.updateRestriction(actingAgent, desiredItem, temp);
                if (!hasMemory()) {
                    ip.resetPointers(); //reset the round
                    for (List<Integer> itemPref1 : itemPref) {
                        itemPref1.clear();
                    }
                }
            }
        }
        return hasTaken;
    }
}
