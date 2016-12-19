/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.Auxiliary;

import Main.Settings.Settings;

/**
 *
 * @author ylo019
 */
public class ProbabilityMatrix extends iProbabilityMatrix{
    
    private int permutation;
    private final int[][] distribution;
    
    public ProbabilityMatrix(int agent, int object) {
        super(agent, object);
        distribution = new int[agent][object];
    }
    
    public boolean addMatching(Permutation p) {
        if (p == null || p.size() != size()) {
            return false;
        }
        int count = 0;
        ++permutation;
        iIterator pp = p.getIterator();
        while (pp.hasNext()) {
            int next = pp.getNext();
            if (next != Settings.NULL_ITEM) {
                distribution[count][next - 1]++;
            }
            ++count;
        }
        return true;
    }
    
    @Override
    protected void normalize() throws EmptyMatrixException {
        if (permutation == 0) {
            throw new EmptyMatrixException("normalize(): empty matrix cannot be normalized");
        }
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < objectSize(); j++) {
                normalized[i][j] = distribution[i][j]/((double)permutation);
            }
        }
    }
    
}
