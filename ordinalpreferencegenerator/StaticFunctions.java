/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordinalpreferencegenerator;

import java.util.ArrayList;
import java.util.List;

import MatchingAlgorithm.Auxiliary.InvalidPreferenceException;
import MatchingAlgorithm.Auxiliary.Permutation;

/**
 *
 * @author ylo019
 */
public class StaticFunctions {
    private static int fact(int n) {
        if (n <= 1) {
            return 1;
        }
        return n * fact(n - 1);
    }

    public static Permutation[] permutations(int n) {
        Permutation[] output = new Permutation[fact(n)];
        List<Integer> var = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            var.add(i);
        }
        generatePermutations(output, 0, var, new int[n], 0);
        return output;
    }
    // check the Heap's algorithm to generate permutation
    private static int generatePermutations(Permutation[] profiles, int index, List<Integer> var, int[] i, int pos) {
        if (var.isEmpty()) {
            try {
                profiles[index++] = new Permutation(i.length, i.length, i);
            } catch (InvalidPreferenceException ex) {
                throw new RuntimeException(ex.getMessage());
            }
            return index;
        }
        for (Integer iter: var) {
            List<Integer> clone = new ArrayList<>(var);
            int posClone = pos;
            int[] iClone = i.clone();
            clone.remove(iter);
            iClone[posClone++] = iter;
            index = generatePermutations(profiles, index, clone, iClone, posClone);
        }
        return index;
    }    
}
