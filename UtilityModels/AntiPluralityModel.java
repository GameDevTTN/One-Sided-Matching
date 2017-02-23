/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UtilityModels;

/**
 *
 * @author ylo019
 */
public class AntiPluralityModel implements iUtilitiesModel {

    @Override
    public double[] getUtilities(int size) {
        double[] out = new double[size];
        for (int i = 0; i < size - 1; i++) { //all except the last becomes 1
            out[i] = 1;
        }
        return out;
    }

    @Override
    public String getName() {
        return "Anti-Plurality";
    }
    
}
