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
public class NoMemoryQueueImplTest extends DeterministicAlgorithm {
        
    @Override
    public String getName() {
        return "NMALQ test";
    }
    
    private final int ROUND_PLACEHOLDER = -1;
    private boolean newItemIntroduced = false;
    
    private int getItemPrefIndex(int desiredItem) {
        return (desiredItem - 1);
    }
    
    @Override
    protected int[] solve(Permutation priority, PreferenceProfile input, int agents, int objects) {
        iIterator pp = priority.getIterator();
        iProfileIterator ip = input.getIterator();
        //who has what object
        int[] obj = new int[objects];
        
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
        agentOrder.add(ROUND_PLACEHOLDER); //always add placeholder
        while (!agentOrder.isEmpty()) {
            PostBox.broadcast(MessageType.DETAILS, Arrays.toString(agentOrder.toArray()));
            int actingAgent = agentOrder.remove(0);
            if (actingAgent == ROUND_PLACEHOLDER) {
                if (agentOrder.isEmpty()) {
                    break;
                } else {
                    if (newItemIntroduced) {
                        ip.resetPointers(); //reset the round
                        for (List<Integer> itemPref1 : itemPref) {
                            itemPref1.clear();
                        }    
                        newItemIntroduced = false;
                    }
                    agentOrder.add(ROUND_PLACEHOLDER);
                }
                continue; //skip the rest of the while loop
            }
            int desiredItem = (ip.hasNext(actingAgent)? ip.getNext(actingAgent) : Settings.NULL_ITEM);
            if (desiredItem == Settings.NULL_ITEM) {
                hasTaken[actingAgent - 1] = Settings.NULL_ITEM;
                PostBox.broadcast(new TakeItemEvent(actingAgent, desiredItem));
                continue;
            }
            if (!itemPref[getItemPrefIndex(desiredItem)].contains(actingAgent)) {
                //itemPref[getItemPrefIndex(desiredItem)].add(actingAgent); //AF
                itemPref[getItemPrefIndex(desiredItem)].add(0, actingAgent); //AL
            }

            int temp = obj[desiredItem - 1];
            if (temp > 0) { //someone has it
                if (!itemPref[getItemPrefIndex(desiredItem)].contains(temp)) {
                    itemPref[getItemPrefIndex(desiredItem)].add(temp);
                }
                if (itemPref[getItemPrefIndex(desiredItem)].indexOf(actingAgent) < itemPref[getItemPrefIndex(desiredItem)].indexOf(temp)) {
                    PostBox.broadcast(new TakeItemEvent(actingAgent, desiredItem, temp));
                    agentOrder.add(temp);
                    hasTaken[actingAgent - 1] = desiredItem; //acting agent takes item
                    obj[desiredItem - 1] = actingAgent; //acting agent have the item
                    hasTaken[temp - 1] = 0; //previous agent loses item;
                } else {
                    agentOrder.add(actingAgent);
                }
            } else {
                hasTaken[actingAgent - 1] = desiredItem; //takes item
                obj[desiredItem - 1] = actingAgent; //acting agent have the item
                PostBox.broadcast(new TakeItemEvent(actingAgent, desiredItem));
                newItemIntroduced = true;
            }
        }
        return hasTaken;
    }
}
