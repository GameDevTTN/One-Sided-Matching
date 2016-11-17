/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.Hybrid;

import MatchingAlgorithm.DeterministicAlgorithm.RandomSerialDictatorship;
import MatchingAlgorithm.DeterministicAlgorithm.YankeeSwap.YankeeSwapStandardWithGTTC;

/**
 *
 * @author ylo019
 */
public class RSDYSGTTC extends HybridAlgorithm {
    
    public RSDYSGTTC() {
        super(new RandomSerialDictatorship(), new YankeeSwapStandardWithGTTC());
    }
    
}
