/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.Taxonomy;

/**
 *
 * @author ylo019
 */
public class MemorylessAcceptFirstQueue extends GenericImplementation {

    @Override
    public String getName() {
        return "Memoryless AcceptFirst Queue implementation";
    }
    
    @Override
    protected boolean hasMemory() {
        return false;
    }
    
    @Override
    protected boolean insertNewProposalAsFirst() {
        return false;
    }
    
    @Override
    protected boolean isStack() {
        return false;
    }
    
}
