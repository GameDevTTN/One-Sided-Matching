/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.DeterministicAlgorithm.improvementAlgorithms;

import Main.Settings.Settings;
import MatchingAlgorithm.Auxiliary.InvalidPreferenceException;
import MatchingAlgorithm.Auxiliary.Permutation;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import MatchingAlgorithm.Auxiliary.iProfileIterator;

/**
 *
 * @author ylo019
 */
public class GTTC {
    
    private GTTC() {}
    
    public static void main(String[] args) throws InvalidPreferenceException {
        Permutation res = new Permutation(4, new int[]{1,2,3,4});
        PreferenceProfile inp = new PreferenceProfile(4, 4, new Permutation[]{new Permutation(4, new int[]{1,2,3,4}),
                                                                                new Permutation(4, new int[]{1,3,2,4}),
                                                                                new Permutation(4, new int[]{2,3,4,1}),
                                                                                new Permutation(4, new int[]{3,2,4,1})});
        System.out.println(improve(res, inp));
    }

    public static Permutation improve(Permutation result, PreferenceProfile input) {
        if (result.size() == input.size()) {
            iProfileIterator iterator = input.getIterator();
            int[] resultArr = result.getArray(); //who holds what
            int[] hasObj = new int[input.objectSize()]; //what is held by who
            for (int i = 0; i < resultArr.length; i++) {
                if (resultArr[i] != Settings.NULL_ITEM) {
                    hasObj[resultArr[i] - 1] = i + 1;
                }
            }
            int[] allocated = new int[input.size()];
            int allocatedAgents = 0;
            for (int j = 0; j < resultArr.length; j++) {
                if (resultArr[j] == Settings.NULL_ITEM) {
                    allocated[j] = Settings.NULL_ITEM;
                    ++allocatedAgents;
                }
            }
            while (allocatedAgents < allocated.length) {
                iterator.resetPointers();
                int[] pointsTo = new int[resultArr.length];
                for (int i = 0; i < resultArr.length; i++) {
                    while (allocated[i] == 0 && (pointsTo[i] == 0 || allocated[hasObj[pointsTo[i] - 1] - 1] != 0)) {
                        pointsTo[i] = iterator.getNext(i + 1);
                    }
                }
                for (int j = 0; j < resultArr.length; j++) {
                    if (allocated[j] == 0) {
                        //detect cycle
                        int currentAgent = j + 1;
                        for (int k = 0; k <= resultArr.length; k++) {
                            currentAgent = hasObj[pointsTo[currentAgent - 1] - 1];
                        }
                        //allocate
                        while (allocated[currentAgent - 1] == 0) {
                            allocated[currentAgent - 1] = pointsTo[currentAgent - 1];
                            ++allocatedAgents;
                            currentAgent = hasObj[pointsTo[currentAgent - 1] - 1];
                        };
                    }
                }
            }
            try {
                return new Permutation(result.size(), allocated);
            } catch (InvalidPreferenceException ex) {
                throw new RuntimeException("YankeeSwapStandardWithGTTC: improve(...): allocated is not a permutation");
            }
        }
        throw new RuntimeException("YankeeSwapStandardWithGTTC: improve(...): input size doesn't match output size");
    }
    
    
}
