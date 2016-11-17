/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers.Envy;

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
public class EnvyFreeSummary extends iResultsCollator {
    
    private List<String> envyFreeAlgorithm = null;
    
    @Override
    public void init() {
        PostBox.listen(this, MessageType.PROCESS);
        PostBox.listen(this, MessageType.SYSTEM);
    }

    @Override
    protected void onProcess(String p1S, Object p1T) {
        if (p1S.equals("SD Envy Free Allocations") && p1T instanceof ArrayList) {
            if (envyFreeAlgorithm == null) {
                envyFreeAlgorithm = new ArrayList<>((ArrayList)p1T);
            } else {
                envyFreeAlgorithm.retainAll((ArrayList)p1T);
            }            
        }
    }

    @Override
    protected void onEndCalculation() {
        PostBox.broadcast(MessageType.SUMMARY, new Pair<>("Ex-Ante Envy Free Algorithms", new ArrayList<>(envyFreeAlgorithm)));
    }
}