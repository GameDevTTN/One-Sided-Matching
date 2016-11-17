/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers;

import Main.Observers.CompareTables.CompareValuesTable;
import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import Pair.Pair;

/**
 *
 * @author ylo019
 */
public class EquivalentAlgorithm extends iResultsCollator {
    
    private CompareValuesTable isSame = null;
    
    @Override
    public void init() {
        PostBox.listen(this, MessageType.PROCESS);
        PostBox.listen(this, MessageType.SYSTEM);
    }
    
    @Override
    protected void onProcess(String p1S, Object p1T) {
        if (p1S.equals("Comparing Output") && p1T instanceof CompareValuesTable) {
            if (isSame == null) {
                isSame = (CompareValuesTable)p1T;
            } else {
                isSame = isSame.and((CompareValuesTable)p1T);
            }
        }
    }
    
    @Override
    protected void onEndCalculation() {
        PostBox.broadcast(MessageType.SUMMARY, new Pair<>("Equivalent Algorithms", isSame));
    }
    
}
