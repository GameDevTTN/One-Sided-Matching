/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import Main.Observers.Auxiliary.dtoTable;
import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import Main.Settings.Configurations;
import Main.Settings.Settings;
import MatchingAlgorithm.iOneSidedAlgorithm;
import Pair.Pair;

/**
 *
 * @author ylo019
 */
//create LaTeX table for the measures
public class LaTeXTablePrinter extends iResultsCollator {
    
    private List<String> algorithmNames = new ArrayList<>();
    private List<String> measures = new ArrayList<>();
    private List<String>[] values;
    private int size;
    
    @Override
    public void init() {
        PostBox.listen(this, MessageType.SUMMARY);
        PostBox.listen(this, MessageType.SYSTEM);
        algorithmNames = Configurations.getConfigurations().getAlgorithmNames();
        values = new List[algorithmNames.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = new ArrayList<>();
        }
        size = Configurations.getConfigurations().peekIterator().profileLength();
    }
    

    @Override
    public void update(Observable o, Object o1) {
        if (o instanceof PostBox && o1 instanceof Pair) {
            PostBox p = (PostBox)o;
            Pair p1 = (Pair)o1;
            switch (p.getSource()) {
                case SUMMARY:
                    if (o1 instanceof Pair) {
                        if (((Pair)o1).getS() instanceof String && ((Pair)o1).getT() instanceof dtoTable) {
                            measures.add((String) ((Pair)o1).getS());
                            dtoTable table = (dtoTable) ((Pair)o1).getT();
                            for (String s : algorithmNames) {
                                values[algorithmNames.indexOf(s)].add(table.lookup(s));
                            }
                        }
                    }
                    break;
                case SYSTEM:
                    if (p1.getS() instanceof String) {
                        onSystem((String)p1.getS());
                    }
                    if ("End Size".equals(p1.getS())) {
                        onEndSize();
                    }
                    break;
            }
        }
    }
    
    @Override
    protected void onEndSize() {
        PostBox.broadcast(MessageType.PRINT, toString());
        clear();
        size += Configurations.getConfigurations().peekIterator().profileLength();
    }
    
    @Override
    protected void clear() {
        super.clear();
        measures.clear();
        for (List<String> ls : values) {
            ls.clear();
        }
    }
    
//    @Override
//    public String toString() {
//        String output = "";
//        for (int i = 0; i < algorithmNames.size(); i++) {
//            output += Configurations.getConfigurations().getPreferenceDescription();
//            output += "," + size;                    
//            output += "," + algorithmNames.get(i);
//            for (int j = 0; j < values[i].size(); j++) {
//                output += "," + values[i].get(j);
//            }
//            output += "\n";
//        }
//        
//        return output;
//    }
    
    @Override
    public String toString() {
        String output = "";
        output += "\\caption{} \\label{tab:title}\n\\begin{tabular}{";
        for (int i = 0; i <= measures.size(); i++) {
            output += "l";
        }
        output += "}\n";
        //process data here
        //heading
        for (String s : measures) {
            output += " & " + s;
        }
        output += "\\\\\n";
        //loop
        for (int i = 0; i < algorithmNames.size(); i++) {
            //name of algo
            output += algorithmNames.get(i);
            //data
            for (int j = 0; j < values[i].size(); j++) {
                output += " & " + values[i].get(j);
            }
            output += "\\\\\n";
        }
        
        output +="\\end{tabular}\n";
        return output;
    }

}
