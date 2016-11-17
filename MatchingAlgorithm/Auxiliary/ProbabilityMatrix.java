/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.Auxiliary;

/**
 *
 * @author ylo019
 */
public class ProbabilityMatrix extends iProbabilityMatrix{
    
    private int permutation;
    private final int[][] distribution;
    
    public ProbabilityMatrix(int agent) {
        super(agent, agent);
        distribution = new int[agent][agent];
    }
    
    public boolean addMatching(Permutation p) {
        if (p == null || p.size() != size()) {
            return false;
        }
        int count = 0;
        ++permutation;
        iIterator pp = p.getIterator();
        while (pp.hasNext()) {
            distribution[count++][pp.getNext() - 1]++;
        }
        return true;
    }
    
    @Override
    protected void normalize() throws EmptyMatrixException {
        if (permutation == 0) {
            throw new EmptyMatrixException("normalize(): empty matrix cannot be normalized");
        }
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                normalized[i][j] = distribution[i][j]/((double)permutation);
            }
        }
    }
    
}
