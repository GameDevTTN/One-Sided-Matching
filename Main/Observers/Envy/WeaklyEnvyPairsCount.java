/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers.Envy;

import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;
import Pair.Pair;

/**
 *
 * @author ylo019
 */
public class WeaklyEnvyPairsCount extends EnvyAgentCount {
    
    @Override
    protected void onEachResult(String key, iProbabilityMatrix value) {
        defaultTable.put(key, defaultTable.getOrDefault(key, 0.0) + (1 - value.numberOfWeaklyEnviousPairs(pp)/(value.size() * (value.size() - 1))));
        PostBox.broadcast(MessageType.DETAILS, new Pair<>("Average % of Weakly SD Envious Pairs", value.numberOfWeaklyEnviousPairs(pp)));
    }
    
    @Override
    public String getName() {
        return "w\\#EF";
    }
    
}
