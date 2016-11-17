/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordinalpreferencegenerator;

import java.lang.reflect.InvocationTargetException;

import MatchingAlgorithm.Auxiliary.InvalidPreferenceException;
import MatchingAlgorithm.Auxiliary.Permutation;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;

/**
 *
 * @author ylo019
 */
public abstract class OrdinalIteratorAdaptor implements iOrdinalIterator {
    
    protected final int size;
    protected final Permutation[] profiles;
    protected final int[] profileIndex;
    
    public static OrdinalIteratorAdaptor getIterator(Class<? extends OrdinalIteratorAdaptor> iteratorClass, int count) {
        if (count > 10 || count <= 1) {
            return null;
        }
        try {
            return iteratorClass.getConstructor(Integer.TYPE).newInstance(new Integer(count));
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new RuntimeException("OrdinalIteratorAdaptor: getIterator(Class, int): cannot create new instance.");
        }
    }
    
    OrdinalIteratorAdaptor(int count) { //package private
        size = count;
        profiles = StaticFunctions.permutations(size);
        profileIndex = new int[size];
    }
    
    @Override
    public abstract boolean hasNext();
    
    protected Permutation[] returnProfile() {
        Permutation[] output = new Permutation[size];
        for (int i = 0; i < size; i++) {
            output[i] = profiles[profileIndex[i]];
        }
        return output;
    }
    //This method returns the next preference profile
    @Override
    public PreferenceProfile getNext() {
        Permutation[] output = returnProfile();
        do {
            updateProfileIndex();
        } while (!isValid());
        //return the preference profile
        try {
            return new PreferenceProfile(size, size, output);
        } catch (InvalidPreferenceException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
    
    protected abstract void updateProfileIndex();
    
    protected abstract boolean isValid();
    
    @Override
    public int profileLength() {
        return profiles.length;
    }
    
    protected Permutation getProfile(int index) {
        return profiles[index];
    }
    
    @Override
    public abstract String getName();
}
