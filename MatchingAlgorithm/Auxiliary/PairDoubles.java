/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.Auxiliary;

import Main.Settings.Settings;
import Pair.Pair;

/**
 *
 * @author ylo019
 */
public class PairDoubles extends Pair<Double, Double> {

    public PairDoubles(Double s, Double t) {
        super(s, t);
    }
    
    public Double difference() {
        return (getS() - getT());
    }
    
    @Override
    public String toString() {
        return Settings.format(getS() - getT());
    }
}
