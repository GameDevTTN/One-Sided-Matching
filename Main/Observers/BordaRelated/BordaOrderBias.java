/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers.BordaRelated;

import Main.Observers.Auxiliary.dtoTable;
import Main.Observers.System.MessageType;
import Main.Observers.iGenericResultsCollator;
import Main.Observers.iResultsCollator;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 *
 * @author ylo019
 */
public class BordaOrderBias extends iGenericResultsCollator<double[]> { 
    
    @Override
    protected void onEachResult(String key, iProbabilityMatrix value) {
        if (defaultTable.get(key) == null) {
            defaultTable.put(key, new double[value.size()]);
        }
        double[] array = defaultTable.get(key);
        for (int i = 0; i < value.size(); i++) {
            array[i] += value.getAgentBordaCount(pp, i + 1);
        }
    }

    @Override
    protected MessageType broadcastType() {
        return MessageType.SUMMARY;
    }

    @Override
    protected double[] defaultValue() {
        return new double[0];
    }
    
    @Override
    protected double[] onEachEntry(String key) {
        double[] out = getMap().getOrDefault(key, defaultValue());
        double[] includesMean = new double[out.length + 1];
        double sum = 0.0d;
        for (int i = 0; i < out.length; i++) {
            includesMean[i] = out[i]/getProfileCount();
            sum += includesMean[i];
        }
        includesMean[includesMean.length - 1] = sum/out.length;
        return includesMean;
    }

    @Override
    protected Object formatOutput(TreeMap<String, double[]> output) {
        return new dtoTable(output);
    }
    
}
