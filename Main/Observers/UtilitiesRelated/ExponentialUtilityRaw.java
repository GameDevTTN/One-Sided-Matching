/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers.UtilitiesRelated;

import UtilityModels.ExponentialModel;

/**
 *
 * @author ylo019
 */
public class ExponentialUtilityRaw extends CustomUtilityModelRaw {
    
    public ExponentialUtilityRaw() {
        this(0.0d);
    }
    
    public ExponentialUtilityRaw(double param) {
        super(new ExponentialModel(param));
    }
}
