/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers;

import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import Pair.Pair;

/**
 *
 * @author ylo019
 */
//to count the number of preferences - does not process the data otherwise
public class PreferencesCounter extends iResultsCollator {
    
    private int counter = 0;
    
    @Override
    public void init() {
        PostBox.listen(this, MessageType.PREFERENCE);
        PostBox.listen(this, MessageType.SYSTEM);
    }
    
    @Override
    protected void onPreference(PreferenceProfile pp) {
        ++counter;
    }

    @Override
    protected void onEndSize() {
        PostBox.broadcast(MessageType.SUMMARY, new Pair<>("Total number of preferences ran", counter));
        clear();
    }
    
    @Override
    protected void clear() {
        counter = 0;
    }
    
}
