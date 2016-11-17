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
public interface iIterator {
    
    public int size();
    
    public boolean hasNext();

    public int tryNext() throws InvalidPreferenceException;

    public int getNext();

    public void resetPointer();
}
