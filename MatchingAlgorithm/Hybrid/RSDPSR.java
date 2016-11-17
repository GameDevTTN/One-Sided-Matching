/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.Hybrid;

import MatchingAlgorithm.ProbabilisticSerialRule;
import MatchingAlgorithm.DeterministicAlgorithm.RandomSerialDictatorship;

/**
 *
 * @author ylo019
 */
public class RSDPSR extends HybridAlgorithm{
    
    public RSDPSR() {
        super(new RandomSerialDictatorship(), new ProbabilisticSerialRule());
    }    
}
