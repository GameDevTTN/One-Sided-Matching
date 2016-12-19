/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordinalpreferencegenerator;

import MatchingAlgorithm.Auxiliary.InvalidPreferenceException;
import MatchingAlgorithm.Auxiliary.PreferenceProfile;

/**
 *
 * @author ylo019
 */
public class IANCWithSharedFirstChoice extends IANC {

    public IANCWithSharedFirstChoice(int count) {
        super(count);
    }
    
    @Override
    public boolean isValid() {
        if (!super.isValid()) {
            return false;
        }
        try {
            return NotAllUniqueFirstChoice.check(new PreferenceProfile(agents, agents, returnProfile()));
        } catch (InvalidPreferenceException ex) {
            throw new RuntimeException("IANCWSFC: isValid(): IPE thrown");
        }
    }
    
}
