/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import ordinalpreferencegenerator.PartialIterator;
import ordinalpreferencegenerator.PartialProfileIterator;
import Main.Observers.AlgorithmObserver;
import Main.Observers.KeyResultBroadcaster;
import Main.Observers.System.IO;
import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import Main.Settings.Configurations;
import Main.Settings.Settings;
import Main.Settings.iAppClass;
import MatchingAlgorithm.DeterministicAlgorithm.YankeeSwap.YankeeSwapStandardWithGTTC;
import Pair.Pair;

/**
 *
 * @author ylo019
 */
public class StrategyProofnessStart implements iAppClass {
    
    @Override
    public void start() {
        
        IO.getConsole();
        int size = 0;//Configurations.getConfigurations().getParams()[1];
        PartialIterator op = PartialIterator.getIterator(size);
//        OrdinalPreferenceIterator op = OrdinalPreferenceRandomiser.getRandomiser(10, 300);
        new KeyResultBroadcaster().init();
        new AlgorithmObserver(YankeeSwapStandardWithGTTC.class).init();
        while (op.hasNext()) {
            PartialProfileIterator next = op.getNext();
            PostBox.broadcast(MessageType.SYSTEM, new Pair<>("Pref", next));
            //print priority
            while (next.hasNext()) {
                PostBox.broadcast(MessageType.PREFERENCE, new Pair<>("Preference", next.getNext()));
                PostBox.broadcast(MessageType.SYSTEM, new Pair<>("Compare", ""));
            }
        }
        PostBox.broadcast(MessageType.SYSTEM, new Pair<>("Finished", ""));
        IO.getConsole().close();
    }
    
}
