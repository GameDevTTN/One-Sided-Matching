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
public class Custom extends OrdinalIteratorAdaptor {

    public Custom(int count) {
        super(count);
    }
    
    int val = 2;

    @Override
    public boolean hasNext() {
        return (profileIndex[val] <= profiles.length - 1);
    }

    @Override
    protected void updateProfileIndex() {
        profileIndex[val]++;
    }

    @Override
    protected boolean isValid() {
        return true;
    }

    @Override
    public String getName() {
        return "Custom Profile Generator";
    }
    
}
