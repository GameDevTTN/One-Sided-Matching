/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers.UtilitiesRelated;

import Main.Observers.System.MessageType;
import Main.Observers.iDoubleResultsCollator;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;
import UtilityModels.ExponentialModel;
import UtilityModels.iUtilitiesModel;

/**
 *
 * @author ylo019
 */
public class CustomUtilityModelPercentageOfMax extends iDoubleResultsCollator {
    
    protected double max = 0;
    
    private iUtilitiesModel model;
    
    public CustomUtilityModelPercentageOfMax(iUtilitiesModel model) {
        this.model = model;
    }
    
    @Override
    protected void beforeResults() {
        max = 0;
        for (iProbabilityMatrix pm : results.values()) {
            max = Math.max(max, pm.getAdditiveUtility(pp, model));
        }
    }

    @Override
    protected void onEachResult(String key, iProbabilityMatrix value) {
        defaultTable.put(key, defaultTable.getOrDefault(key, 0.0) + value.getAdditiveUtility(pp, model)/max);
    }
    
    @Override
    protected Double onEachEntry(String key) {
        return getMap().getOrDefault(key, 0.0)/getProfileCount() * 100;
    }
    
    @Override
    protected MessageType broadcastType() {
        return MessageType.SUMMARY;
    }
    
    @Override
    protected String getName() {
        return model.getName();
    }
    
    @Override
    protected String getSuffix() {
        return "";
    }

    @Override
    protected boolean hasPercentageOutput() {
        return true;
    }
    @Override
    protected void clear() {
        super.clear();
    }    
}
