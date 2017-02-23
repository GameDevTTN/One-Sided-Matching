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
public class ExponentialUtilityPercentageOfMax extends CustomUtilityModelPercentageOfMax {

    public ExponentialUtilityPercentageOfMax() {
        this(0.0d);
    }
    
    public ExponentialUtilityPercentageOfMax(double param) {
        super(new ExponentialModel(param));
    }

}
