/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers.Proportionality;

import java.util.Arrays;

import Main.Observers.iDoubleResultsCollator;
import Main.Observers.System.MessageType;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;

/**
 *
 * @author ylo019
 */
public class SDProportionalProfileCount extends iDoubleResultsCollator {
    
    @Override
    protected void onEachResult(String key, iProbabilityMatrix value) {
        defaultTable.put(key, defaultTable.getOrDefault(key, 0.0) + (count(value, pp) == value.size() ? 1 : 0));
    }
    
    protected double count(iProbabilityMatrix value, PreferenceProfile pp) {
        int sdPropAgent = 0;
        double[] proportional = new double[pp.size()];
        Arrays.fill(proportional, 1.0/pp.size());
        for (int i = 1; i <= pp.size(); i++) {
            if (pp.doesSDPrefer(i, value.read(i), proportional)) {
                ++sdPropAgent;
            }
        }
        return sdPropAgent;
    }
    
    @Override
    protected boolean hasPercentageOutput() {
        return true;
    }
    
    @Override
    protected Double onEachEntry(String key) {
        return defaultTable.getOrDefault(key, 0.0)/getProfileCount() * 100;
    }
    
    @Override
    public String getName() {
        return "\\%Prop";
    }

    @Override
    protected MessageType broadcastType() {
        return MessageType.SUMMARY;
    }
    
}
