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
public class RandomUtilityModel implements iUtilitiesModel {

    //it is a way to randomise things, it is not a consistent set of utilities.
    
    @Override
    public double[] getUtilities(int size) {
        double[] out = new double[size];
        for (int i = 0; i < out.length; i++) {
            out[i] = Math.random();
        }
        return out;
    }

    @Override
    public String getName() {
        return "Random Numbers";
    }
    
}
