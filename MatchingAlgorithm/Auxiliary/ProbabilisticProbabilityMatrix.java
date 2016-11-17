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
public class ProbabilisticProbabilityMatrix extends iProbabilityMatrix {
    
    private final double[][] distribution;
    private double weight;
    
    public ProbabilisticProbabilityMatrix(int agent) {
        super(agent, agent);
        distribution = new double[agent][agent];
    }
    
    public boolean addMatching(int[] p, double weight) {
        if (p == null || p.length != size() || weight < 0) {
            return false;
        }
        int count = 0;
        this.weight += weight;
        for (int i = 0; i < p.length; ++i) {
            distribution[count++][p[i] - 1] += weight;
        }
        return true;
    }
    
    public boolean addPMatrix(iProbabilityMatrix other) throws EmptyMatrixException {
        if (other == null || size() != other.size()) {
            return false;
        }
        other.normalize();
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                distribution[i][j] += other.normalized[i][j];
            }
        }
        ++weight;
        return true;
    }

    @Override
    protected void normalize() throws EmptyMatrixException {
        if (weight == 0) {
            throw new EmptyMatrixException("normalize(): empty matrix cannot be normalized");
        }
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                normalized[i][j] = distribution[i][j]/weight;
            }
        }
    }
    
}
