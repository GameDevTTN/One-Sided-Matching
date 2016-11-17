/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers.Proportionality.WeaklyProportional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import Main.Observers.iResultsCollator;
import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;
import Pair.Pair;

/**
 *
 * @author ylo019
 */
public class WeaklySDProportionalityChecker extends iResultsCollator {
    
    private final List<String> isSDProportional = new ArrayList<>();

    @Override
    protected void onEndPreference() {
        PostBox.broadcast(MessageType.SYSTEM, new Pair<>("Checking SD Proportional", ""));
        for (Map.Entry<String, iProbabilityMatrix> e : results.entrySet()) {
            if (isProportional(e.getValue(), pp, e.getValue().size())) {
                isSDProportional.add(e.getKey());
            }
        }
        PostBox.broadcast(MessageType.PROCESS, new Pair<>("Weakly SDProportional Allocations", new ArrayList<>(isSDProportional)));
        clear();
    }

    @Override
    protected void clear() {
        isSDProportional.clear();
    }

    protected boolean isProportional(iProbabilityMatrix matrix, PreferenceProfile pp, double factor) {
        double[] proportional = new double[pp.size()];
        Arrays.fill(proportional, 1.0/factor);
        for (int i = 1; i <= pp.size(); i++) {
            if (pp.doesStrictSDPrefer(i, proportional, matrix.read(i))) {
                return false;
            }
        }
        return true;
    }
}