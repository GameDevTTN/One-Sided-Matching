/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordinalpreferencegenerator;

/**
 *
 * @author ylo019
 */
public class IC extends OrdinalIteratorAdaptor {

    public IC(int count) {
        super(count);
    }

    @Override
    public boolean hasNext() {
        if (profileIndex[0] > 0) {
            return false;
        }
        return true;
    }

    @Override
    protected void updateProfileIndex() {
        for (int i = size-1; i >= 0; i--) {
            if (++profileIndex[i] < profiles.length) {
                for (int j = i + 1; j < size; j++) {
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
