/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordinalpreferencegenerator;

import Main.Settings.Configurations;
import Main.Settings.Settings;

/**
 *
 * @author ylo019
 */
public class IC extends OrdinalIteratorAdaptor {

    public IC(int count) {
        super(count);
    }
    
    public IC(int agent, int object) {
        super(agent, object);
    }

    @Override
    public boolean hasNext() {
        if (profileIndex[0] > (Configurations.FIXED_ORDER_FOR_ALGORITHM?profiles.length - 1:0)) {
            return false;
        }
        return true;
    }

    @Override
    protected void updateProfileIndex() {
        for (int i = agents-1; i >= 0; i--) {
            if (++profileIndex[i] < profiles.length) {
                for (int j = i + 1; j < agents; j++) {
                    profileIndex[j] = 0;
                }
                break;
            }
        }
    }

    @Override
    protected boolean isValid() {
        return true;
    }
    
    @Override
    public String getName() {
        return "IC";
    }
}
