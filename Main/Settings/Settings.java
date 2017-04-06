/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Settings;

//import MatchingAlgorithm.Gurobi.*;

/**
 *
 * @author ylo019
 */
public abstract class Settings {
    
    public static final int RUN = (int) (Math.random() * Integer.MAX_VALUE);

    
    //code for null item
    //required to be -1 for Hungarian Algorithm (unless modifying the code in HA)
    public static final int NULL_ITEM = -1;
    
    
    //main method location & number of agents/items
//    public static final Class APP_CLASS = MainBruteForce.class;
    public static String PATH = "DATADUMP/280317/SchoolChoice/";//"290716/Mallows10/";
//    public static final String DATA_SAVE_PATH = null; //"randomDataPackTest/real/Pack1/";
    

}
