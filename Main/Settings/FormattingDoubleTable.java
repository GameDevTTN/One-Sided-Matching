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
public class FormattingDoubleTable {
    
    public static String DoubleTable(double[][] table) {
        String out = "";
        for (double[] arr : table) {
            if (!out.equals(""))
                out += "\n";
            for (double d : arr) {
                out += Format.Format(d) + " ";
            }
        }
        return out;
    }
    
}
