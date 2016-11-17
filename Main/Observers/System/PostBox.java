/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers.System;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import MatchingAlgorithm.Auxiliary.TakeItemEvent;
import Pair.Pair;

/**
 *
 * @author ylo019
 */
public class PostBox extends Observable {

    private final MessageType mt;
    
    private PostBox(MessageType mt) {this.mt = mt;}
    
    private void broadcast(Object message) {
        setChanged();
        notifyObservers(message);
    }
    
    private static final Map<MessageType, PostBox> notifyMap = new HashMap<>();
    
    public static void listenAll(Observer o) {
        for (MessageType mt : MessageType.values()) {
            listen(o, mt);
        }
    }
    
    public static void listen(Observer o, MessageType mt) {
        if (!notifyMap.containsKey(mt)) {
            notifyMap.put(mt, new PostBox(mt));
        }
        notifyMap.get(mt).addObserver(o);
    }
    
    public static void remove(Observer box) {
        for (PostBox pb : notifyMap.values()) {
            pb.deleteObserver(box);
        }
    }
    
    public static void broadcast(MessageType mt, Object message) {
        if (notifyMap.containsKey(mt)) {
            notifyMap.get(mt).broadcast(message);
        }
    }
    
    public static void broadcast(TakeItemEvent message) {
        broadcast(MessageType.DETAILS, new Pair<>("", message));
    }
    
    public MessageType getSource() {
        return mt;
    }
    
    
    private List<Observer> observers = new ArrayList<Observer>();
    @Override
    public void addObserver(Observer o) {
        if (o == null) {
            throw new NullPointerException("PostBox: addObserver(Observer): o is null");
        }
        observers.add(o);
    }
    @Override
    public int countObservers() {
        return observers.size();
    }
    @Override
    public void deleteObserver(Observer o) {
        observers.remove(o);
    }
    @Override
    public void deleteObservers() {
        observers.clear();
    }
    @Override
    public void notifyObservers() {
        notifyObservers(null);
    }
    @Override
    public void notifyObservers(Object arg) {
        if (hasChanged()) {
            for (Observer ob : observers) {
                ob.update(this, arg);
            }
            clearChanged();
        }
        
    }
}
