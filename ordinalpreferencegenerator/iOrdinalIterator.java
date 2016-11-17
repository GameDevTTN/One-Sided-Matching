/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordinalpreferencegenerator;

import MatchingAlgorithm.Auxiliary.PreferenceProfile;

/**
 *
 * @author ylo019
 */
public interface iOrdinalIterator {
    
    public boolean hasNext();
    //This method returns the next preference profile
    public PreferenceProfile getNext();
    
    public int profileLength();
    
    public String getName();
}
