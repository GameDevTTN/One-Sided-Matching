/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers;

import Main.Observers.System.MessageType;
import Main.Observers.System.PostBox;

/**
 *
 * @author ylo019
 */
public class Timer extends iResultsCollator{
    
    private long startTime;
    
    @Override
    public void init() {
        startTime = System.currentTimeMillis();
        super.init();
    }
    
    @Override
    public void onEndSize() {
        PostBox.broadcast(MessageType.PRINT, "Total time taken: " + (System.currentTimeMillis() - startTime) + " ms");
        startTime = System.currentTimeMillis();
    }
    
}
