/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers;

import java.util.TreeMap;

import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import Main.Settings.FormattingDoubleTable;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;
import MatchingAlgorithm.Auxiliary.iProfileIterator;
import Pair.Pair;

/**
 *
 * @author ylo019
 */
public class KeyResultBroadcaster extends iResultsCollator {

    @Override
    protected void onEndPreference() {
        String output = "";
        iProfileIterator ppi = pp.getIterator();
        while (ppi.hasNext(ppi.size())) {
            output += ppi.getNext(ppi.size());
        }
        double[][] oneDTable = new double[1][];
        oneDTable[0] = ((TreeMap<String, iProbabilityMatrix>)results).pollFirstEntry().getValue().read(ppi.size());
        output += FormattingDoubleTable.DoubleTable(oneDTable);
        PostBox.broadcast(MessageType.COMPARISON, new Pair<>("",output));
    }
    
}
