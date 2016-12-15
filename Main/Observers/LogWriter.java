/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers;

import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import Main.Settings.Settings;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;
import UtilityModels.ExponentialModel;
import UtilityModels.PluralityModel;
import UtilityModels.iUtilitiesModel;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class LogWriter extends iResultsCollator {
    
    public static int id = 0;
    public static final iUtilitiesModel[] utilModels = {new PluralityModel(), 
        //new ExponentialModel(0), 
        new ExponentialModel(1), 
        //new ExponentialModel(2), 
        new ExponentialModel(3), 
        new ExponentialModel(-1), 
        //new ExponentialModel(-2)
    };
    
    @Override
    public void onEndPreference() {
        int index = id++;
        for (Entry<String, iProbabilityMatrix> e : results.entrySet()) {
            for (iUtilitiesModel ium : utilModels) {
                String log = Settings.RUN + ", " + index + ", ";
                log += e.getKey();
                log += (", " + Settings.ORDINAL_PREFERENCE.getSimpleName() + ", " + Settings.PREF_PARAM);
                log += (", " + ium.getName());
                for (double[] d : e.getValue().inPreferenceOrder(pp)) {
                    log += (", " + Settings.DoubleToString(utility(d, ium)));
                }
                PostBox.broadcast(MessageType.LOG_ENTRY, log); 
            }
        }
        clear();
    }
    
    private double utility(double[] d, iUtilitiesModel utilityModel) {
        double[] utilities = utilityModel.getUtilities(d.length);
        double sum = 0.0;
        for (int index = 0; index < d.length; index++) {
            sum += d[index] * utilities[index];
        }
        return sum;
    }
    
}
