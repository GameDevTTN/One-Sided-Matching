/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Observers;

import MatchingAlgorithm.iAlgorithm;
import MatchingAlgorithm.iOneSidedAlgorithm;
import java.util.Observer;

/**
 *
 * @author ylo019
 * @param <T>
 */
public interface iAlgorithmObserver<T extends iAlgorithm> extends Observer {
    
    public void init();
    public String getName();
    
}
