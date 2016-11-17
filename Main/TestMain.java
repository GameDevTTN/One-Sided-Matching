/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import MatchingAlgorithm.Auxiliary.InvalidPreferenceException;
import MatchingAlgorithm.Auxiliary.Permutation;

/**
 *
 * @author ylo019
 */
public class TestMain {
    
    public static void main(String[] args) throws InvalidPreferenceException {
        Permutation[] a = new Permutation[3];
        Permutation[] b = new Permutation[3];
        a[2] = new Permutation(3, new int[]{1,2,3});
        a[1] = new Permutation(3, new int[]{1,2,3});
        a[0] = new Permutation(3, new int[]{2,3,1});
        b[0] = new Permutation(3, new int[]{1,2,3});
        b[1] = new Permutation(3, new int[]{3,1,2});
        b[2] = new Permutation(3, new int[]{3,1,2});
        System.out.println(compare(b));
        
    }
    
    private static boolean compare(Permutation[] a) {
        //naive algorithm - probably optimisable
        Permutation[] curr = a;
        for (Permutation p : curr) {
            Permutation[] newPerm = new Permutation[curr.length];
            for (int q = 0; q < newPerm.length; q++) {
                try {
                    newPerm[q] = curr[q].renaming(p.inverse());
                } catch (InvalidPreferenceException ex) {
                    throw new RuntimeException(ex.getMessage());
                }
            }
            Arrays.sort(newPerm, 0, newPerm.length);
            for (Permutation p2 : newPerm) {
                try {
                    System.out.println(new Permutation(p2));
                } catch (InvalidPreferenceException ex) {
                    Logger.getLogger(TestMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            for (int r = 0; r < curr.length; r++) {
                if (curr[r].compareTo(newPerm[r]) < 0) {
                    break;
                } else if (curr[r].compareTo(newPerm[r]) > 0) {
                    return false;
                }
            }
        }
        return true;
    }
    
}
