/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.File;
import java.util.Scanner;

import PrefLibDataReader.PrefLibLoader;

/**
 *
 * @author ylo019
 */
public class DebugLoader {
    
    public static void main(String[] args) {
        PrefLibLoader p = PrefLibLoader.load(new File("ED-12-01-TEST.txt"));
        String s = "";
        Scanner scan = new Scanner(System.in);
        while (!s.equals("q")) {
            System.out.println(p.getRandom());
            s = scan.nextLine();
        }
    }
}
