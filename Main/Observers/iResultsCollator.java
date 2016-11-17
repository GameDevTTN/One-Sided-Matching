/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers;

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
public abstract class iResultsCollator implements Observer {
    
    protected PreferenceProfile pp;
    protected Map<String, iProbabilityMatrix> results = new TreeMap<>();
    
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
                        onPreference((PreferenceProfile) p1.getS());
                    }
                    break;
                case TABLE:
                    if (p1.getS() instanceof String && p1.getT() instanceof iProbabilityMatrix) {
                        onTable((String) p1.getS(), (iProbabilityMatrix) p1.getT());
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

    protected void onPreference(PreferenceProfile profile) {
        pp = profile;
        results.clear();
    }
    protected void onTable(String algoName, iProbabilityMatrix probMatrix) {
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
