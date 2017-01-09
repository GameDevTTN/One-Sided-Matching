/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers.Auxiliary;

import Main.Settings.Format;
import java.util.Map;
import java.util.TreeMap;

import Main.Settings.Settings;

/**
 *
 * @author ylo019
 */
public class dtoPercentageTable extends dtoTable<Double> {

    public dtoPercentageTable(TreeMap<String, Double> results) {
        super(results);
    }
    
    @Override
    public String lookup(String s) {
        return (results.containsKey(s) ? Format.Format(results.get(s)/100) : "fails to find");
    }

    @Override
    public String toString() {
        String out = "";
        for (Map.Entry<String, Double> e : results.entrySet()) {
            if (!out.equals(""))
                out += "\n";
            out += Format.Format(e.getValue()) + "% " + e.getKey();
        }
        return out;
    }    
}
