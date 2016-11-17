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
public class FormattingIntTable {
    
    public static String OneDimTable(int[] array) {
        String out = "";
        for (int i : array) {
            out += Settings.format(i) + " ";
        }
        return out;
    }
    
}
