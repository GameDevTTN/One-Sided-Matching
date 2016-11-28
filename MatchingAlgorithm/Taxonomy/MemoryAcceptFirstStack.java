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
public class MemoryAcceptFirstStack extends GenericImplementation {

    @Override
    protected boolean hasMemory() {
        return true;
    }

    @Override
    protected boolean insertNewProposalAsFirst() {
        return false;
    }

    @Override
    protected boolean isStack() {
        return true;
    }

    @Override
    public String getName() {
        return "Memory AcceptFirst Stack implementation";
    }
    
}
