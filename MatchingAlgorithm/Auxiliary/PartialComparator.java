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
public interface PartialComparator<T> {
    
    //returns PCR.GREATER if self > other, PCR.LESS if self < other, PCR.EQUAl if self == other, PCR.INCOMPARABLE if self and other is not comparable
    public PartialComparatorResult compare(T self, T other);
    
}
