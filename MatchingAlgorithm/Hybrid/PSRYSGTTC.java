/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.Hybrid;

import MatchingAlgorithm.ProbabilisticSerialRule;
import MatchingAlgorithm.DeterministicAlgorithm.YankeeSwap.YankeeSwapStandardWithGTTC;

/**
 *
 * @author ylo019
 */
public class PSRYSGTTC extends HybridAlgorithm {
    
    public PSRYSGTTC() {
        super(new ProbabilisticSerialRule(), new YankeeSwapStandardWithGTTC());
    }
}
