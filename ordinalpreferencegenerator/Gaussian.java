/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordinalpreferencegenerator;

import Main.Settings.Configurations;
import Main.Settings.Settings;
import MatchingAlgorithm.Auxiliary.InvalidPreferenceException;
import MatchingAlgorithm.Auxiliary.Permutation;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import Pair.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author ASUS
 */
public class Gaussian implements iOrdinalIterator{
    
    protected final int size;
    protected final int objectSize;
    private int runs;
    private Permutation[] out;
    
    private Random r = new Random();
    
    Gaussian(int run, int count, int object) { //package private
        size = count;
        objectSize = object;
        out = new Permutation[size];
        runs = run;
    }

    @Override
    public boolean hasNext() {
        return runs > 0;
    }
    @Override
    public PreferenceProfile getNext() {
        if (!hasNext()) {
            throw new RuntimeException("Gaussian: getNext(): end of profiles");
        }
        --runs;
        Permutation[] out = new Permutation[size];
        //edit code below to make it generate Gaussian distribution
        List<Double> objects = new ArrayList<>();
        List<Double> agents = new ArrayList<>();

        for (int i = 0; i < out.length; i++) {
            objects.add(r.nextGaussian());
            agents.add(r.nextGaussian());
        }
        for (int i = 0; i < out.length; i++) {
            int[] ranks = new int[size];
            List<Pair<Integer, Double>> indivPreference = new ArrayList<>();
            for (int j = 0; j < objects.size(); j++) {
                indivPreference.add(new Pair<>(j+1, agents.get(i) - objects.get(j)));
            }
            int index = 0;
            while (!indivPreference.isEmpty()) {
                Pair<Integer, Double> max = null;
                for (Pair<Integer, Double> p : indivPreference) {
                    if (max == null || p.getT() < max.getT()) {
                        max = p;
                    }
                }
                if (!indivPreference.remove(max)) {
                    throw new RuntimeException("Gaussian: getNext(): cannot remove from indivPreference");
                }
                ranks[index++] = max.getS();
            }
            //sort

            try {
                out[i] = new Permutation(size, objectSize, ranks);
            } catch (InvalidPreferenceException ex) {
                throw new RuntimeException("OrdinalPreferenceRandomiser: getNext(): shuffledArray is not a permutation");
            }
        }
        try {
            return new PreferenceProfile(size, size, out);
        } catch (InvalidPreferenceException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
    
    protected Permutation[] returnProfile() {
        return out.clone();
    }

    @Override
    public int profileLength() {
        return size;
    }

    @Override
    public String getName() {
        return "Gaussian";
    }
    
}
