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
public class OneUniquePreference extends OrdinalIteratorAdaptor {

    public OneUniquePreference(int count) {
        super(count);
    }

    @Override
    public boolean hasNext() {
        return (profileIndex[profileIndex.length - 1] < profiles.length);
    }

    @Override
    protected void updateProfileIndex() {
        ++profileIndex[profileIndex.length - 1];
    }

    @Override
    protected boolean isValid() {
        return true;
    }
    
    @Override
    public String getName() {
        return "OneUnique";
    }
}
