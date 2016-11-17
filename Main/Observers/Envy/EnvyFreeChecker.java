/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers.Envy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Main.Observers.iResultsCollator;
import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;
import Pair.Pair;

/**
 *
 * @author ylo019
 */
public class EnvyFreeChecker extends iResultsCollator {
    
    private final List<String> isEF = new ArrayList<>();
    
    @Override
    protected void onEndPreference() {
        PostBox.broadcast(MessageType.SYSTEM, new Pair<>("Computing Envy Freeness", ""));
        for (Map.Entry<String, iProbabilityMatrix> e : results.entrySet()) {
            if (e.getValue().isEnvyFree(pp)) {
                isEF.add(e.getKey());
            }
        }
        PostBox.broadcast(MessageType.PROCESS, new Pair<>("SD Envy Free Allocations", new ArrayList<>(isEF)));
        clear();
    }

    @Override
    protected void clear() {
        isEF.clear();
    }
    
}
