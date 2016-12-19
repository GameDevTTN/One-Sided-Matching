/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordinalpreferencegenerator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import MatchingAlgorithm.Auxiliary.InvalidPreferenceException;
import MatchingAlgorithm.Auxiliary.Permutation;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;

/**
 *
 * @author ylo019
 */
//obsolete class
public class OrdinalPreferenceRandomiser {
    
    private final static OrdinalIteratorAdaptor[] staticArray = new OrdinalIteratorAdaptor[20];
    
    public static OrdinalIteratorAdaptor getRandomiser(final int count, final int numberOfPP) {
        if (count > staticArray.length || count <= 1 || numberOfPP <= 0 || numberOfPP > 100000) {
            return null;
        }
        if (staticArray[count - 1] == null) {
            staticArray[count - 1] = new OrdinalIteratorAdaptor(count){
            private int runs = numberOfPP;
            @Override
            public boolean hasNext() {
                return runs > 0;
            }
            @Override
            public PreferenceProfile getNext() {
                if (!hasNext()) {
                    throw new RuntimeException("OrdinalPreferenceRandomiser: getNext(): end of profiles");
                }
                --runs;
                Permutation[] out = new Permutation[count];
                Integer[] array = new Integer[count];
                for (int i = 0; i < out.length; i++) {
                    array[i] = count+1;
                }
                for (int i = 0; i < out.length; i++) {
                    List<Integer> listOfInts = Arrays.asList(array);
                    Collections.shuffle(listOfInts);
                    int[] shuffledArray = new int[count];
                    for (int j = 0; j < listOfInts.size(); j++) {
                        shuffledArray[j] = listOfInts.get(j);
                    }
                    try {
                        out[i] = new Permutation(count, count, shuffledArray);
                    } catch (InvalidPreferenceException ex) {
                        throw new RuntimeException("OrdinalPreferenceRandomiser: getNext(): shuffledArray is not a permutation");
                    }
                }
                try {
                    return new PreferenceProfile(count, count, out);
                } catch (InvalidPreferenceException ex) {
                    throw new RuntimeException(ex.getMessage());
                }
            }

                @Override
                protected void updateProfileIndex() {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                protected boolean isValid() {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public String getName() {
                    return "OrdinalPrefRandomiser";
                }
            };
        }
        return staticArray[count - 1];
    }
}
