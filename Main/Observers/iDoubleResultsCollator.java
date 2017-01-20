/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers;

import java.util.TreeMap;

import Main.Observers.Auxiliary.dtoPercentageTable;
import Main.Observers.Auxiliary.dtoTable;

/**
 *
 * @author ylo019
 */
//for collating double type data after each sizes
public abstract class iDoubleResultsCollator extends iGenericResultsCollator<Double> {
    
    //whether to conver the double into percentage
    protected boolean hasPercentageOutput() {
        return false;
    }
    
    @Override
    protected Double defaultValue() {
        return 0.0d;
    }
    
    @Override
    protected Object formatOutput(TreeMap<String, Double> output) {
        return (hasPercentageOutput() ?
                    new dtoPercentageTable(output) : new dtoTable<>(output));
    }
    
}
