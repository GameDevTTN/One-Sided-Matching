/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers;

import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import Main.Settings.FormattingDoubleTable;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;
import Pair.Pair;

/**
 *
 * @author ylo019
 */
public class PreferenceOrder extends iResultsCollator{
    
    //preferences saved in a PreferenceProfile named pp
    
    @Override
    protected void onTable(String algoName, iProbabilityMatrix probMatrix) {
        PostBox.broadcast(MessageType.OUTPUT, new Pair<>(algoName + "(in Pref Order)", FormattingDoubleTable.DoubleTable(probMatrix.inPreferenceOrder(pp))));
    }    
}
