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
public class TruncatedIC extends IC {

    public TruncatedIC(int count) {
        super(count);
    }
    
    public TruncatedIC(int agent, int object) {
        super(agent, object);
    }
    
    @Override
    public boolean hasNext() {
        if (profileIndex[0] > 0) {
            return false;
        }
        return true;
    }
}
