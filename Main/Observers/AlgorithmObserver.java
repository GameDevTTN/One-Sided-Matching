/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers;

import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import MatchingAlgorithm.iAlgorithm;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import Pair.Pair;
import java.util.Arrays;

/**
 *
 * @author ylo019
 */
public class AlgorithmObserver implements Observer {
    
    private iAlgorithm algorithm;
    
    public AlgorithmObserver(iAlgorithm algo) {
        if (algo != null) {
            algorithm = algo;
        } else {
            throw new RuntimeException("AlgorithmObserver(iAlgorithm): algo is null");
        }
    }
    
    public AlgorithmObserver(Class<? extends iAlgorithm> algoClass) {
        try {
            algorithm = algoClass.getConstructor().newInstance();
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            try {
                algorithm = algoClass.getConstructor(Double.TYPE).newInstance(new Double(0.5));
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex1) {
                
            }
        }
    }
    
    public AlgorithmObserver(Class<? extends iAlgorithm> algoClass, int param) {
        try {
            algorithm = algoClass.getConstructor(Integer.TYPE).newInstance(new Integer(param));
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex1) {
                
        }
    }
    
    public AlgorithmObserver(Class<? extends iAlgorithm> algoClass, Boolean[] params) {
        try {
            Class[] clazzes = new Class[params.length];
            Arrays.fill(clazzes, Boolean.TYPE);
            algorithm = algoClass.getConstructor(clazzes).newInstance(params);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex1) {
            System.out.println("no good");
        }
    }
    
    public void init() {
        PostBox.listen(this, MessageType.PREFERENCE);
    }

    @Override
    public void update(Observable o, Object o1) {
        if (o instanceof PostBox && o1 instanceof Pair) {
            PostBox p = (PostBox)o;
            Pair p1 = (Pair)o1;
            switch (p.getSource()) {
                case PREFERENCE:
                    if (p1.getT() instanceof Boolean && (boolean)p1.getT() &&p1.getS() instanceof PreferenceProfile) {
                        PostBox.broadcast(MessageType.TABLE, new Pair<>(algorithm.getName(),algorithm.solve((PreferenceProfile) p1.getS(), ((PreferenceProfile)p1.getS()).size(), ((PreferenceProfile)p1.getS()).objectSize())));
                    }
                    break;
            }
        }
    }
    
    public String getName() {
        return algorithm.getName();
    }
    
}
