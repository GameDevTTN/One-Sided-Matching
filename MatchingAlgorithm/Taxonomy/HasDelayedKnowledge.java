/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.Taxonomy;

import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ylo019
 */
public abstract class HasDelayedKnowledge<T extends GenericImplementation> extends GenericImplementation {
    
    private GenericImplementation inner;
    
    protected HasDelayedKnowledge(Class<T> clazz) {
        try {
            inner = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(HasDelayedKnowledge.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected final boolean hasMemory() {
        return inner.hasMemory();
    }
    
    @Override
    protected final boolean insertNewProposalAsFirst() {
        return inner.insertNewProposalAsFirst();
    }
    
    @Override
    protected final boolean isStack() {
        return inner.isStack();
    }
    
    @Override
    protected boolean hasDelayedMemory() {
        return true;
    }
    
    @Override
    public String getName() {
        return "HasDelayedKnowledge " + inner.getName();
    }
    
}
