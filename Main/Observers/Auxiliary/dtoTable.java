/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers.Auxiliary;

import java.util.Map;
import java.util.TreeMap;

import Main.Settings.Settings;

/**
 *
 * @author ylo019
 */
public class dtoTable<T> {
    
        
    protected TreeMap<String, T> results;

    public dtoTable(TreeMap<String, T> results) {
        this.results = (TreeMap<String, T>) results.clone();
    }

    public String lookup(String s) {
        return (results.containsKey(s) ? Settings.format(results.get(s)) : "fails to find");
    }
    
    @Override
    public String toString() {
        String out = "";
        for (Map.Entry<String, T> e : results.entrySet()) {
            if (!out.equals(""))
                out += "\n";
            out += Settings.format(e.getValue()) + " " + e.getKey();
        }
        return out;
    }
}
