/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.ItemProposingAlgorithms;

/**
 *
 * @author ylo019
 */
public class BordaAtDecreasingAverage extends BordaAtIncreasingAverage {
    
    public BordaAtDecreasingAverage() {
        super.increasingAverage = false;
    }
    
    @Override
    public String getName() {
        return "ItemProposing DecreasingBorda AnonAgent NotAnonItem";
    }
    
}
