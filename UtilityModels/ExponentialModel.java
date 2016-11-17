/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UtilityModels;

import java.util.Arrays;

/**
 *
 * @author ASUS
 */
public class ExponentialModel implements iUtilitiesModel {
    
    private final double alpha;
    
    public ExponentialModel(double alpha) {
        this.alpha = alpha;
    }

    @Override
    public double[] getUtilities(int size) {
        double[] out = new double[size];
        if (alpha == 0.0) {
            for (int i = 0; i < out.length; i++) {
                out[i] = (double)(size - 1 - i);///(size - 1);
            }
        } else {
            for (int i = 0; i < out.length; i++) {
                out[i] = (1 - Math.exp(-1 * alpha * (size - 1 - i)))/alpha;
            }
        }
        return out;
    }

    @Override
    public String getName() {
        return "Exp " + alpha;
    }
    
}
