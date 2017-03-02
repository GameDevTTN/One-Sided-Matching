/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Settings;


/**
 *
 * @author ylo019
 */
public class FormattingObjectTable {
    
    public static String OneDimTable(Object[] array) {
    String out = "";
        for (Object p : array) {
            if (!out.equals(""))
                out += "\n";
            out += Format.Format(p);
        }
        out += "\n";
        return out;
    }
    
}
