/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.Auxiliary;

import java.util.Arrays;

import Main.Settings.FormattingIntTable;
import Main.Settings.Settings;

/**
 *
 * @author ylo019
 */
public class Permutation implements Comparable<Permutation> {
    
    public Permutation(Permutation clone) throws InvalidPreferenceException {
        this(clone.preferences.length, clone.preferences);
    }
    
    public Permutation(int object, int[] preference) throws InvalidPreferenceException {
        if (!isValid(object, preference)) {
            throw new InvalidPreferenceException("Preference(): preference is null or is not a valid preference array of size object");
        }
        preferences = preference.clone();
    }
    
    //only guarantees no repeats and no out-of-bound values
    private boolean isValid(int object, int[] preference) {
        if (preference == null || preference.length != object) {
            return false;
        }
        for (int i : preference) {
            if (i != Settings.NULL_ITEM && (i < 1 || i > object)) {
                return false;
            }
        }
        boolean[] found = new boolean[object];
        for (int i : preference) {
            if (i == Settings.NULL_ITEM) {
                continue;
            }
            if (found[i - 1]) {
                return false;
            }
            found[i - 1] = true;
        }
        return true;
    }
    
    public Permutation inverse() {
        int[] inv = new int[preferences.length];
        for (int i = 0; i < preferences.length; i++) {
            inv[preferences[i] - 1] = i + 1;
        }
        try {
            return new Permutation(preferences.length, inv);
        } catch (InvalidPreferenceException ex) {
            throw new RuntimeException("Permutation: inverse(): output is not a permutation");
        }
    }
    
    public boolean isBefore(int agent, int other) {
        for (int i : preferences) {
            if (i == agent) {
                return true;
            } else if (i == other) {
                return false;
            }
        }
        throw new RuntimeException("Permutation: isBefore(): out of bounds");
    }
    
    public iIterator getIterator() {
        return new PermutationIterator();
    }

    @Override
    public String toString() {
        return FormattingIntTable.OneDimTable(preferences);
    }
    
    public Permutation renaming(Permutation pattern) throws InvalidPreferenceException {
        if (pattern == null) {
            throw new NullPointerException("Matching: renaming(Matching): pattern is null");
        }
        if (preferences.length != pattern.preferences.length) {
            throw new InvalidPreferenceException("Matching: renaming(Matching): pattern is wrong length");
        }
        int[] newPref = new int[preferences.length];
        for (int i = 0; i < newPref.length; i++) {
            newPref[i] = pattern.preferences[preferences[i] - 1];
        }
        return new Permutation(preferences.length, newPref);
    }
    
    protected int[] preferences;
    
    public int[] getArray() {
        return preferences.clone();
    }
    
    public int size() {
        if (preferences == null) {
            return 0;
        }
        return preferences.length;
    }

    @Override
    public int compareTo(Permutation t) {
        if (t == null) {
            throw new NullPointerException("Permutation: compareTo(Permutation): t is null");
        }
        for (int i = 0; (i < preferences.length && i < t.preferences.length); i++) {
            if (preferences[i] < t.preferences[i]) {
                return -1;
            } else if (preferences[i] > t.preferences[i]) {
                return 1;
            }
        }
        if (preferences.length < t.preferences.length) {
            return -1;
        } else if (preferences.length > t.preferences.length) {
            return 1;
        }
        return 0;
    }
    
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Permutation) || ((Permutation)other).size() != size()) {
            return false;
        }
        Permutation others = (Permutation)other;
        return Arrays.equals(preferences, others.preferences);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Arrays.hashCode(this.preferences);
        return hash;
    }
    
    public class PermutationIterator implements iIterator {
        
        protected int pointer = -1;
        
        @Override
        public int size() {
            return Permutation.this.size();
        }
        
        @Override
        public boolean hasNext() {
            if (preferences == null) {
                return false;
            }
            return pointer < preferences.length - 1;
        }
    
        @Override
        public int tryNext() throws InvalidPreferenceException {
            if (hasNext()) {
                return preferences[++pointer];
            }
            throw new InvalidPreferenceException("tryNext(): does not have another item");
        }

        @Override
        public int getNext() {
            try {
                return tryNext();
            } catch (InvalidPreferenceException ex) {
                throw new RuntimeException("getNext(): Unchecked exception. does not have another item");
            }
        }

        @Override
        public void resetPointer() {
            pointer = -1;
        }
        
        @Override
        public String toString() {
            return Permutation.this.toString();
        }
    }
    
}
