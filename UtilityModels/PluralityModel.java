/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UtilityModels;

/**
 *
 * @author ASUS
 */
public class PluralityModel implements iUtilitiesModel {

    @Override
    public double[] getUtilities(int size) {
        double[] out = new double[size];
        out[0] = 1;
        return out;
    }

    @Override
    public String getName() {
        return "Plurality";
    }
    
}
