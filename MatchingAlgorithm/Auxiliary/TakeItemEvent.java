/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.Auxiliary;

/**
 *
 * @author ylo019
 */
public class TakeItemEvent {
    
    private int agent;
    private int item;
    private int source;
    private boolean success;
    
    public TakeItemEvent(int agent, int item) {
        this(agent, item, 0);
    }
    
    public TakeItemEvent(int agent, int item, int source) {
        this(agent, item, source, true);
    }
    
    public TakeItemEvent(int agent, int item, boolean success) {
        this(agent, item, 0, success);
    }
    
    public TakeItemEvent(int agent, int item, int source, boolean success) {
        this.agent = agent;
        this.item = item;
        this.source = source;
        this.success = success;
    }
    
    @Override
    public String toString() {
        return "Agent " + agent + " takes item " + item + " from " + (source == 0? "the stash" : "agent " + source) + (success?"":", but fails");
    }
}
