/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers;

import Main.Observers.Auxiliary.PreferenceType;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;

import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;
import Pair.Pair;

/**
 *
 * @author ylo019
 */
//Any collection of results should override this class or one of its subclasses
public abstract class iResultsCollator implements Observer {
    
    protected PreferenceProfile pp;
    protected Map<String, iProbabilityMatrix> results = new TreeMap<>();
    private PreferenceType pref;
    
    public iResultsCollator() {
        pref = PreferenceType.ONE_SIDED;
    }
    
    void setPreferenceType(PreferenceType newType) {
        pref = newType;
    }
    
    protected PreferenceType getPref() {
        return pref;
    }
    
    //can be overwritten to listen for different message type
    public void init() {
        PostBox.listen(this, MessageType.PROCESS);
        PostBox.listen(this, MessageType.PREFERENCE);
        PostBox.listen(this, MessageType.TABLE);
        PostBox.listen(this, MessageType.SYSTEM);
    }

    @Override
    public void update(Observable o, Object o1) {
        if (o instanceof PostBox && o1 instanceof Pair) {
            PostBox p = (PostBox)o;
            Pair p1 = (Pair)o1;
            switch (p.getSource()) {
                case PROCESS:
                    if (p1.getS() instanceof String) {
                        onProcess((String)p1.getS(), p1.getT());
                    }
                    break;
                case PREFERENCE:
                    if (p1.getS() instanceof PreferenceProfile) {
                        if (PreferenceType.ONE_SIDED == pref || PreferenceType.TWO_SIDED_PROPOSER == pref)
                            onPreference((PreferenceProfile) p1.getS());
                        else if ((p1.getT() instanceof PreferenceProfile) && PreferenceType.TWO_SIDED_PROPOSEE == pref)
                            onPreference((PreferenceProfile) p1.getT());
                    }
                    break;
                case TABLE:
                    if (p1.getS() instanceof String && p1.getT() instanceof iProbabilityMatrix) {
                        if (PreferenceType.ONE_SIDED == pref || PreferenceType.TWO_SIDED_PROPOSER == pref)
                            onTable((String) p1.getS(), (iProbabilityMatrix) p1.getT());
                        else if (PreferenceType.TWO_SIDED_PROPOSEE == pref)
                            onTable((String) p1.getS(), ((iProbabilityMatrix) p1.getT()).invert());
                    }
                    break;
                case SYSTEM:
                    if (p1.getS() instanceof String) {
                        onSystem((String)p1.getS());
                    }
                    if ("End Preference".equals(p1.getS())) {
                        onEndPreference();
                    }
                    if ("End Size".equals(p1.getS())) {
                        onEndSize();
                    }
                    if ("End Calculation".equals(p1.getS())) {
                        onEndCalculation();
                    }
                    break;
            }
        }
    }

    
    //default actions:
    /*
    onPreference = when a new preference profile is loaded. save the profile in variable pp, clear previous results.
    onTable = when an algorithm outputs a probability matrix. no default action. probMatrix cannot be null.
    
    //no default actions.
    onSystem = catchall on receiving a MessageType.System
    onEndPreference = after all algorithm outputted a probability matrix
    onEndSize = after all algorithm run all preference profile for that size
    onEndCalculation = after all algorithm run on preference profile of all sizes
    onProcess = when algorithms generate a MessageType.Process message
    clear = manually clear all data after a preference profile
    */
    protected void onPreference(PreferenceProfile profile) {
        pp = profile;
        results.clear();
    }
    protected void onTable(String algoName, iProbabilityMatrix probMatrix) {
        if (PreferenceType.TWO_SIDED_PROPOSEE == pref) {
            //reverse probMatrix
        }
        if (results.put(algoName, probMatrix) != null) {
            System.out.println(this.getClass());
            throw new RuntimeException("Duplicate Results");
        }
    }
    protected void onSystem(String p1S) {}
    protected void onEndPreference() {}
    protected void onEndCalculation() {}
    protected void onEndSize() {}
    protected void onProcess(String p1S, Object t) {}
    protected void clear() {};
    
}
