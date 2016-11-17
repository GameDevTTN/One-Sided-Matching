/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Main.Observers.AlgorithmObserver;
import Main.Observers.System.IO;
import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import MatchingAlgorithm.Auxiliary.InvalidPreferenceException;
import MatchingAlgorithm.Auxiliary.Permutation;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import MatchingAlgorithm.DeterministicAlgorithm.YankeeSwap.YankeeSwapStandard;
import Pair.Pair;

/**
 *
 * @author ylo019
 */
public class TestSpecificInput {
    
    public static void main(String[] args) {
        IO.getConsole();
        AlgorithmObserver algo = new AlgorithmObserver(YankeeSwapStandard.class);
        algo.init();
        PreferenceProfile p = null;
        try {
            p = new PreferenceProfile(4, 4, new Permutation[]{new Permutation(4, new int[]{1, 2, 3, 4}),
                new Permutation(4, new int[]{1, 2, 3, 4}),
                new Permutation(4, new int[]{1, 2, 3, 4}),
                new Permutation(4, new int[]{3, 1, 2, 4})});
        } catch (InvalidPreferenceException ex) {
            System.err.println("error");
        }
        PostBox.broadcast(MessageType.PREFERENCE, new Pair<>("Preference",p));
        System.out.println("done");
    }
    
}
