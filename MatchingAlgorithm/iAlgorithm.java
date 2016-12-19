/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm;

import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;

/**
 *
 * @author ylo019
 */
public interface iAlgorithm {
    
//    public iProbabilityMatrix solve(PreferenceProfile input, int agents);
    public iProbabilityMatrix solve(PreferenceProfile input, int agents, int objects);
    public String getName();
}
