/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers;

import Main.Observers.Auxiliary.PreferenceType;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;
import java.util.Observable;


/**
 *
 * @author ylo019
 */
public final class ResultCollatorWrapper extends iResultsCollator {
    
    private iResultsCollator irc;
    
    public ResultCollatorWrapper(iResultsCollator collator, PreferenceType newType) {
        irc = collator;
        irc.setPreferenceType(newType);
    }
    
       //can be overwritten to listen for different message type
    public void init() {
        irc.init();
    }

    @Override
    public void update(Observable o, Object o1) {
        throw new UnsupportedOperationException("no good");
    }
    @Override
    protected void onPreference(PreferenceProfile profile) {
        throw new UnsupportedOperationException("no good");
    }
    @Override
    protected void onTable(String algoName, iProbabilityMatrix probMatrix) {
        throw new UnsupportedOperationException("no good");
    }
    @Override
    protected void onSystem(String p1S) {throw new UnsupportedOperationException("no good");}
    @Override
    protected void onEndPreference() {throw new UnsupportedOperationException("no good");}
    @Override
    protected void onEndCalculation() {throw new UnsupportedOperationException("no good");}
    @Override
    protected void onEndSize() {throw new UnsupportedOperationException("no good");}
    @Override
    protected void onProcess(String p1S, Object t) {throw new UnsupportedOperationException("no good");}
    @Override
    protected void clear() {throw new UnsupportedOperationException("no good");};
    
}
