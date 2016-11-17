/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers.Proportionality;

import java.util.ArrayList;
import java.util.List;

import Main.Observers.iResultsCollator;
import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import Pair.Pair;

/**
 *
 * @author ylo019
 */
public class SDProportionalSummary extends iResultsCollator {
    
    private List<String> SDProportionalAlgorithms = null;
    
    @Override
    protected void onProcess(String p1S, Object p1T) {
        if ("SDProportional Allocations".equals(p1S) && p1T instanceof List) {
            if (SDProportionalAlgorithms == null) {
                SDProportionalAlgorithms = new ArrayList<>((List)p1T);
            } else {
                SDProportionalAlgorithms.retainAll((List)p1T);
            }
        }
    }
    
    @Override
    protected void onEndCalculation() {
        PostBox.broadcast(MessageType.SUMMARY, new Pair<>("SD Proportional Algorithms", new ArrayList<>(SDProportionalAlgorithms)));
    }

}