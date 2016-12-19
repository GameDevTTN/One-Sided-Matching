/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PrefLibDataReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import MatchingAlgorithm.Auxiliary.InvalidPreferenceException;
import MatchingAlgorithm.Auxiliary.Permutation;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import Pair.Pair;

/**
 *
 * @author ylo019
 */
public class PrefLibLoader {
    
    private static final int MAX_SIZE = 100; //it will NOT work for n = 11, takes too long to run. 39m permutations!!
    
    public static PrefLibLoader load(File file) {
        return privateLoad(file);
    }

    private static PrefLibLoader privateLoad(File file) {
        try {
            return new PrefLibLoader(file);
        } catch (NotPrefLibFileException ex) {
            return null;
        }
    }
    
    private int numOfObjects;
    private Pair<Permutation,Integer>[] frequency;
    private int totalVotes;
    private PrefLibLoader(File file) throws NotPrefLibFileException{
        Scanner s = null;
        try {
            s = new Scanner(file);
            numOfObjects = Integer.parseInt(s.nextLine());
            //skip the next x lines
            for (int i = 0; i < numOfObjects; i++) {
                s.nextLine();
            }
            numOfObjects = Math.min(MAX_SIZE, numOfObjects);
            //next line is total votes, sum of votes, unique pref
            String[] split = s.nextLine().split(",");
            totalVotes = Integer.parseInt(split[1]); //second item
            int uniqueVotes = Integer.parseInt(split[2]);
            frequency = new Pair[uniqueVotes];
            numOfObjects = Math.min(totalVotes, numOfObjects);
            for (int i = 0; i < uniqueVotes; i++) {
                String[] data = s.nextLine().split(",");
                int freq = Integer.parseInt(data[0]);
                int[] prefList = new int[numOfObjects];
                int index = 0;
                for (int j = 1; j < data.length; j++) {
                    int value = Integer.parseInt(data[j]);
                    if (value <= numOfObjects) {
                        prefList[index++] = value;
                    }
                }
                frequency[i] = new Pair<>(new Permutation(numOfObjects, numOfObjects, prefList), freq);
            }
        } catch (FileNotFoundException | NumberFormatException | InvalidPreferenceException e) {
            throw new NotPrefLibFileException(e.getMessage());
        }
    }
    
    public PreferenceProfile readFromStart() {
        Permutation[] perm = new Permutation[numOfObjects];
        int counter = 0;
        for (int i = 0; i < frequency.length; i++) {
            for (int j = 0; j < frequency[i].getT(); j++) {
                perm[counter++] = frequency[i].getS();
            }
        }
        try {
            return new PreferenceProfile(numOfObjects, numOfObjects, perm);
        } catch (InvalidPreferenceException ex) {
            throw new RuntimeException("PrefLibLoader: readFromStart(): PreferenceProfile Error");
        }
    }
    
    public PreferenceProfile getRandom() {
        Permutation[] perm = new Permutation[numOfObjects];
        int[] rolls = new int[numOfObjects];
        Set<Integer> numbers = new HashSet<>();
        for (int i = 0; i < rolls.length; i++) {
            int roll = 0;
            do {
                roll = (int)(Math.random() * totalVotes + 1);
            } while (numbers.contains(roll));
            numbers.add(roll);
            rolls[i] = roll;
        }
        for (int i = 0; i < perm.length; i++) {
            perm[i] = frequency[getIndex(0, totalVotes, rolls[i])].getS();
        }
        try {
            return new PreferenceProfile(numOfObjects, numOfObjects, perm);
        } catch (InvalidPreferenceException ex) {
            throw new RuntimeException("PrefLibLoader.getRandom() throws InvalidPreferenceException");
        }
    }

    private int getIndex(int index, int remainingVotes, int roll) {
        int chance = frequency[index].getT();
        //System.out.printf("frequency: %d, remaining: %d, probability: %f, random: %f, %s\n", frequency[index].getT(), remainingVotes, probability, random, (random <= probability? "picked": ""));
        if (roll <= chance) {
            return index;
        }
        return getIndex((index + 1), remainingVotes - chance, roll - chance);
    }
}
