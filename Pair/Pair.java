/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pair;

import Main.Settings.Format;
import Main.Settings.Settings;

/**
 *
 * @author ylo019
 */
public class Pair<S,T> {
    
    private S s;
    private T t;
    
    public Pair(S s, T t) {
        this.s = s;
        this.t = t;
    }
    
    public S getS() {
        return s;
    }
    
    public T getT() {
        return t;
    }
    
    public String toString() {
        return Format.Format(s) + " " + Format.Format(t);
    }
}
