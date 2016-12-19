/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.WeakHashMap;

import ordinalpreferencegenerator.ImpartialCultureFactory;
import ordinalpreferencegenerator.iOrdinalIterator;
import Main.Observers.iResultsCollator;
import Main.Observers.System.CsvLog;
import Main.Observers.System.IO;
import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;
import Main.Settings.Settings;
import Main.Settings.iAppClass;
import MatchingAlgorithm.Auxiliary.InvalidPreferenceException;
import MatchingAlgorithm.Auxiliary.Permutation;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;
import Pair.Pair;

/**
 *
 * @author ylo019
 */
public class MainBruteForce extends iResultsCollator implements iAppClass {

    public MainBruteForce() {
    }
    @Override
    public void init() {
        //this line can be commented out to avoid storing everything in the hash
//        super.init();
        
    }
    
    @Override
    public void start() {
        
        IO.getConsole();
        CsvLog.getConsole();
        this.init();
        Settings.init();
        PostBox.broadcast(MessageType.PRINT, Settings.ORDINAL_PREFERENCE.getSimpleName());
        for (int i = Settings.M; i <= Settings.N; i+=Settings.INCREMENT) {
            int h = 0; int unH = 0;
            PostBox.broadcast(MessageType.PRINT, "Beginning of size " + i);
            iOrdinalIterator op = (Settings.O == -1 ? ImpartialCultureFactory.createImpartialCulture(Settings.ORDINAL_PREFERENCE, i) : ImpartialCultureFactory.createImpartialCulture(Settings.ORDINAL_PREFERENCE, i, Settings.O));
            while (op.hasNext()) {
                PreferenceProfile next = op.getNext();
                //only run a % of the profile
                if (Math.random() > Settings.RUN_CHANCE) {
                    continue;
                }
                //check if the profile has been ran already
                PreferenceProfile id = preferenceAlreadyRan(next);
                if (id != null) {
                    ++h;
                    PostBox.broadcast(MessageType.PREFERENCE, new Pair<>(id, false));
                    for (Map.Entry<String, iProbabilityMatrix> e : hash.getOrDefault(id, new HashMap<String,iProbabilityMatrix>()).entrySet()) {
                        PostBox.broadcast(MessageType.TABLE, new Pair<>(e.getKey(), e.getValue())); //feed the reordered list to match the raw data
                    }
                } else {
                    ++unH;
                    System.out.println(unH);
                    //print priority
                    PostBox.broadcast(MessageType.PREFERENCE, new Pair<>(next, true));
                }
                PostBox.broadcast(MessageType.SYSTEM, new Pair<>("End Preference", ""));
                //System.out.println(++ii);
            }
            PostBox.broadcast(MessageType.PRINT, "End of size");
            PostBox.broadcast(MessageType.PRINT, "Hashed: " + h + " Unhashed: " + unH);
            PostBox.broadcast(MessageType.SYSTEM, new Pair<>("End Size",""));
        }
        PostBox.broadcast(MessageType.PRINT, "End of calculation");
        PostBox.broadcast(MessageType.SYSTEM, new Pair<>("End Calculation", ""));
        IO.getConsole().close();
        CsvLog.getConsole().close();
    }

    
    private final Map<PreferenceProfile, Map<String, iProbabilityMatrix>> hash = new WeakHashMap<>();
    private final List<PreferenceProfile> leastRecentUse = new LinkedList<>();
    
    private PreferenceProfile preferenceAlreadyRan(PreferenceProfile currProfile) {
        Permutation[] profiles = currProfile.getProfiles();
        for (Permutation p : profiles) {
            Permutation[] newPerm = new Permutation[profiles.length];
            for (int q = 0; q < newPerm.length; q++) {
                try {
                    newPerm[q] = profiles[q].renaming(p.inverse());
                } catch (InvalidPreferenceException ex) {
                    throw new RuntimeException(ex.getMessage());
                }
            }
            Arrays.sort(newPerm, 0, newPerm.length);
            for (int r = 0; r < profiles.length; r++) {
                if (profiles[r].compareTo(newPerm[r]) < 0) {
                    break;
                } else if (profiles[r].compareTo(newPerm[r]) > 0) {
                    try {
                        PreferenceProfile newPP = new PreferenceProfile(currProfile.size(), currProfile.objectSize(), newPerm);
                        if (hash.containsKey(newPP)) {
                            if (leastRecentUse.remove(newPP)) {
                                leastRecentUse.add(newPP);
                            } else {
                                System.out.println("zombie");
                            }
                            return newPP;
                        }
                    } catch (InvalidPreferenceException ex) {
                        throw new RuntimeException("preferenceAlreadyRan(PreferenceProfile): uncaught exception");
                    }
                }
            }
        }
        return null;
    }
    
    @Override
    protected void onEndPreference() {
        if (!hash.containsKey(pp)) {
            hash.put(pp, (Map<String, iProbabilityMatrix>)((TreeMap<String, iProbabilityMatrix>)results).clone());
            while (Runtime.getRuntime().freeMemory() < 1500000 && !leastRecentUse.isEmpty()) {
                leastRecentUse.remove(0);
            }
            leastRecentUse.add(pp);
            System.out.println(Runtime.getRuntime().freeMemory() + " " + hash.size());
        }
    }
    
    @Override
    protected void onEndSize() {
        hash.clear();
    }
    
}
