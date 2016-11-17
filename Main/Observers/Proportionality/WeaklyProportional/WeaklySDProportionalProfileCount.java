/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers.Proportionality.WeaklyProportional;

import java.util.Arrays;

import Main.Observers.Proportionality.SDProportionalProfileCount;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;

/**
 *
 * @author ylo019
 */
public class WeaklySDProportionalProfileCount extends SDProportionalProfileCount {
    
    @Override
    protected double count(iProbabilityMatrix value, PreferenceProfile pp) {
        int sdPropAgent = 0;
        double[] proportional = new double[pp.size()];
        Arrays.fill(proportional, 1.0/pp.size());
        for (int i = 1; i <= pp.size(); i++) {
            if (!pp.doesStrictSDPrefer(i, proportional, value.read(i))) {
                ++sdPropAgent;
            }
        }
        return sdPropAgent;
    }
    
    @Override
    public String getName() {
        return "w\\%Prop";
    }
    
}
