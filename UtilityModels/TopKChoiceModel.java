/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UtilityModels;

import java.util.Arrays;

/**
 *
 * @author ylo019
 */
public class TopKChoiceModel implements iUtilitiesModel {
    
    private int defaultSize;
    private int k;
    public TopKChoiceModel(int k, int defaultSize) {
        this.k = k;
        this.defaultSize = defaultSize;
    }

    @Override
    public double[] getUtilities(int size) {
        
        double[] out = new double[size];
        int boundary = (k - 1) * (size - 1)/(defaultSize - 1);
        for (int i = 0; i <= boundary; i++) {            
//            System.out.println(String.format("size %d defaultSize %d", size, defaultSize));
//            System.out.println(String.format("k %d i %d", k, i));
            out[i] = 1;
        }
        //System.out.println(Arrays.toString(out));
        return out;
    }

    @Override
    public String getName() {
        return String.format("Top %d choice", k);
    }
    
}
