/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import Main.Settings.Settings;
import MatchingAlgorithm.Auxiliary.Permutation;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;

/**
 *
 * @author ylo019
 */
public class PreflibFormatWriter extends iResultsCollator {
    
    private long index = 0;
    public String path = Settings.DATA_SAVE_PATH;
    private int size = 0;
    
    @Override
    public void init() {
        size = Settings.M;
        if (Settings.DATA_SAVE_PATH != null) {
            PostBox.listen(this, MessageType.PREFERENCE);
            PostBox.listen(this, MessageType.SYSTEM);
        }
    }
    
    @Override
    protected void onPreference(PreferenceProfile pp) {
        Writer pw = null;
        try {
            File f = new File(path + "size" + String.format("%02d", size) + "/case" + String.format("%04d", index++) + ".soc");
            new File(path + "size" + String.format("%02d", size) + "/").mkdirs();
            f.createNewFile();
            pw = new PrintWriter(f);
            pw.write(preferenceProfileToPreflibFormat(pp));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PreflibFormatWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PreflibFormatWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (pw != null) {
            try {
                pw.close();
            } catch (IOException ex) {
                Logger.getLogger(PreflibFormatWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    protected void onEndSize() {
        size += Settings.INCREMENT;
        index = 0;
    }
    
    @Override
    protected void onTable(String algoName, iProbabilityMatrix probMatrix) {
        //do nothing
    }

    private String preferenceProfileToPreflibFormat(PreferenceProfile pp) {
        String output = "";
        output += pp.size() + "\n";
        for (int i = 0; i < pp.size(); i++) {
            output += (i+1) + ",X\n";
        }
        output += pp.size() + "," + pp.size() + "," + pp.size();
        for (Permutation p : pp.getProfiles()) {
            String key = "";
            for (int i : p.getArray()) {
                key += "," + i;
            }
            output+= "\n1" + key;
        }
        return output;
    }
    
}
