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
public class EgalitarianModel implements iUtilitiesModel {

    @Override
    public double[] getUtilities(int size) {
        double[] out = new double[size];
        double sum = 0;
        for (int i = 0; i < size; i++) {
            out[i] = -sum;
            sum += Math.pow(size * 2, i);
        }
        return out;
    }

    @Override
    public String getName() {
        return "Egal Model";
    }
    
}
