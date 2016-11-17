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
public class IAC extends OrdinalIteratorAdaptor{
    
    public static IAC getIterator(int count) {
        if (count > 10 || count <= 1) {
            return null;
        }
        return new IAC(count);
    }    

    IAC(int count) {
        super(count);
    }
    
    @Override
    protected boolean isValid() {
        return true;
    }

    @Override
    public boolean hasNext() {
        if (profileIndex[0] >= profiles.length) {
            return false;
        }
        return true;
    }

    @Override
    protected void updateProfileIndex() {
        for (int i = size-1; i >= 0; i--) {
            if (++profileIndex[i] < profiles.length) {
                for (int j = i; j < size; j++) {
                    profileIndex[j] = profileIndex[i];
                }
                break;
            }
        }
    }
    
    @Override
    public String getName() {
        return "IAC";
    }
    
}
