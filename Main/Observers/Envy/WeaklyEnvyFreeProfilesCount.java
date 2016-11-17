/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers.Envy;

import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;

/**
 *
 * @author ylo019
 */
public class WeaklyEnvyFreeProfilesCount extends EnvyFreeProfilesCount {
    
    @Override
    protected void onEachResult(String key, iProbabilityMatrix value) {
        defaultTable.put(key, defaultTable.getOrDefault(key, 0.0) + (value.isWeaklyEnvyFree(pp) ? 1 : 0));
    }
    
    @Override
    public String getName() {
        return "w\\%EF";
    }
    
}
