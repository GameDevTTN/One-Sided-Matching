/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MatchingAlgorithm.Auxiliary;

import Main.Settings.FormattingDoubleTable;
import UtilityModels.iUtilitiesModel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ylo019
 */
public abstract class iProbabilityMatrix {
    
    protected final double[][] normalized;
    private final int agents;
    private final int objects;
    
    protected iProbabilityMatrix(int agent, int items) {
        normalized = new double[agent][items];
        agents = agent;
        objects = items;
    }
    
    protected void normalize() throws EmptyMatrixException {
        for (double[] arr : normalized) {
            double sum = 0;
            for (double d : arr) {
                sum += d;
            }
            if (sum == 0)
                continue;
            for (int i = 0; i < arr.length; i++) {
                arr[i] /= sum;
            }
        }
    }

    public PairDoubles compare(PreferenceProfile pref, iProbabilityMatrix other, int count) {
        double better = 0;
        double worse = 0;
        try {
            normalize();
            other.normalize();
        } catch (EmptyMatrixException ex) {
            throw new RuntimeException("PM: compare(...): cannot compare empty probability matrix");
        }
        iProfileIterator prefProfile = pref.getIterator();
        for (int i = 0; i < count; i++) {
            int var = prefProfile.getNext(i + 1);
            double miniSum = normalized[i][var - 1];
            double miniSum2 = other.normalized[i][var - 1];
            for (int j = 0; j < count - 1; j++) {
                var = prefProfile.getNext(i + 1);
                better += (miniSum * other.normalized[i][var - 1]);
                worse += (miniSum2 * normalized[i][var - 1]);
                //next cycle
                miniSum2 += other.normalized[i][var - 1];
                miniSum += normalized[i][var - 1];
            }
        }
        return new PairDoubles(better, worse);
    }
    
    public boolean isEnvyFree(PreferenceProfile pref) {
        return numberOfEnviousAgents(pref) == 0;
    }
    
    public boolean isWeaklyEnvyFree(PreferenceProfile pref) {
        return numberOfWeaklyEnviousAgents(pref) == 0;
    }
    
    //this deals with SD-envyness
    public double numberOfEnviousAgents(PreferenceProfile pref) {;
        return envy(pref, false, true);
    }
    
    //this deals with weakly SD-envyness
    public double numberOfWeaklyEnviousAgents(PreferenceProfile pref) {
        return envy(pref, true, true);
    }
    
    //SD-envyness
    public double numberOfEnviousPairs(PreferenceProfile pref) {
        return envy(pref, false, false);
    }
    
    //weakly SD-envyness
    public double numberOfWeaklyEnviousPairs(PreferenceProfile pref) {
        return envy(pref, true, false);
    }
    
    private double envy(PreferenceProfile pref, boolean isWeaklySD, boolean countsAgent) {
        int envyCount = 0;
        try {
            normalize();
        } catch (EmptyMatrixException ex) {
            throw new RuntimeException("PM: numberOfEnviousPairs(): probability matrix is empty");
        }
        if (pref.size() != size()) {
            throw new RuntimeException("PM: numberOfEnviousPairs(PreferenceProfile): size of iPM & PP does not match");
        }
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (i == j)
                    continue;
                if (isWeaklySD ? 
                        pref.hasWeakSdEnvy(i+1, normalized[i].clone(), normalized[j].clone()) : 
                        pref.hasStrongSdEnvy(i+1, normalized[i].clone(), normalized[j].clone())) {
                    ++envyCount;
                    if (countsAgent) {
                        break;
                    }
                }
            }
        }
        return (double)envyCount;
    }
    
    public double opv(PreferenceProfile pp) {
        try {
            normalize();
        } catch (EmptyMatrixException ex) {
            throw new RuntimeException("PM: opv(): probability matrix is empty");
        }
        double alpha = Double.POSITIVE_INFINITY;
        for (int i = 0; i < size(); i++) {
            alpha = Math.min(alpha, pp.optimalProportionalityValue(i + 1, normalized[i].clone()));
        }
        
        return alpha;
    }
    
    public double read(int agent, int object) {
        if (agent <= 0 || object <= 0 || agent > size() || object > objectSize()) {
            throw new RuntimeException("iProbabilityMatrix: read(int, int): agent, object not between 1 and size inclusive");
        }
        try {
            normalize();
        } catch (EmptyMatrixException ex) {
            return 0;
        }
        return (normalized[agent - 1][object - 1]);
    }
    
    public double[] read(int agent) {
        if (agent <= 0 || agent > size()) {
            throw new RuntimeException("iProbabilityMatrix: read(int): agent is not between 1 and size inclusive");
        }
        try {
            normalize();
        } catch (EmptyMatrixException ex) {
            return null;
        }
        return (normalized[agent - 1].clone());        
    }
    
    public int size() {
        return agents;
    }
    
    public int objectSize() {
        return objects;
    }
    
    @Override
    public String toString() {
        try {
            normalize();
        } catch (EmptyMatrixException ex) {
            return "";
        }
        return FormattingDoubleTable.DoubleTable(normalized);
    }
    
    public Double getPluralityUtility(PreferenceProfile pref) {
        double count = 0.0;
        try {
            normalize();
        } catch (EmptyMatrixException ex) {
            throw new RuntimeException("iProbabilityMatrix: getBordaCount(PreferenceProfile): matrix is empty");
        }
        iProfileIterator iter = pref.getIterator();
        for (int a = 0; a < pref.size(); a++) { //loop for each agent
            count += normalized[a][iter.getNext(a + 1) - 1];
        }
        return count; //returns the average borda score
    }
    
    public Double getNashUtility(PreferenceProfile pref) {
        double product = 1.0;
        try {
            normalize();
        } catch (EmptyMatrixException ex) {
            throw new RuntimeException("iProbabilityMatrix: getNashUtility(PreferenceProfile): matrix is empty");
        }
        iProfileIterator iter = pref.getIterator();
        for (int a = 0; a < pref.size(); a++) {
            double sum = 0.0;
            int score = pref.size();
            while (iter.hasNext(a + 1)) {
                sum += normalized[a][iter.getNext(a + 1) - 1] * score--;
            }
            product *= sum;
        }
        return Math.pow(product, 1.0/pref.size());
    }
    
    public Double getAdditiveUtility(PreferenceProfile pref, iUtilitiesModel utility) {
        double[] utilities = utility.getUtilities(objects);
        if (utilities.length != objects) {
            throw new RuntimeException("iProbabilityMatrix: getAdditiveUtility(...): utilities size != objects");
        }
        double sum = 0.0;
        try {
            normalize();
        } catch (EmptyMatrixException ex) {
            throw new RuntimeException("iProbabilityMatrix: getBordaCount(PreferenceProfile): matrix is empty");
        }
//        iProfileIterator iter = pref.getIterator();
        for (int a = 0; a < pref.size(); a++) { //loop for each agent
//            int score = pref.size();
//            while (iter.hasNext(a + 1)) {
//                sum += normalized[a][iter.getNext(a + 1) - 1] * score--;
//            }
            sum += getAgentUtilities(pref, utilities, a + 1);
        }
        return sum/pref.size(); //returns the average borda score
    }

    public Double getBordaCount(PreferenceProfile pref) {
        double sum = 0.0;
        try {
            normalize();
        } catch (EmptyMatrixException ex) {
            throw new RuntimeException("iProbabilityMatrix: getBordaCount(PreferenceProfile): matrix is empty");
        }
//        iProfileIterator iter = pref.getIterator();
        for (int a = 0; a < pref.size(); a++) { //loop for each agent
//            int score = pref.size();
//            while (iter.hasNext(a + 1)) {
//                sum += normalized[a][iter.getNext(a + 1) - 1] * score--;
//            }
            sum += getAgentBordaCount(pref, a + 1);
        }
        return sum/pref.size(); //returns the average borda score
    }
    
    public Double getMinBordaCount(PreferenceProfile pref) {
        double min = Double.MAX_VALUE;
        try {
            normalize();
        } catch (EmptyMatrixException ex) {
            throw new RuntimeException("iProbabilityMatrix: getMinBordaCount(PreferenceProfile): matrix is empty");
        }
//        iProfileIterator iter = pref.getIterator();
        for (int a = 0; a < pref.size(); a++) { //loop for each agent
//            int score = pref.size();
//            double currTotal = 0;
//            while (iter.hasNext(a + 1)) {
//                currTotal += normalized[a][iter.getNext(a + 1) - 1] * score--;
//            }
            min = Math.min(min, getAgentBordaCount(pref, a + 1));
        }
        return min; //returns the average borda score
    }
    
    public Double getAgentUtilities(PreferenceProfile pref, double[] utilities, int agent) {
        if (agent <= 0 || agent > size()) {
            throw new RuntimeException("iProbabilityMatrix: getAgentBordaCount(PreferenceProfile, int): agent not between 1 and size inclusive");
        }
        try {
            normalize();
        } catch (EmptyMatrixException ex) {
            return 0.0d;
        }
        int score = 0;
        double total = 0;
        iProfileIterator iter = pref.getIterator();
        while (iter.hasNext(agent)) {
            total += normalized[agent - 1][iter.getNext(agent) - 1] * utilities[score++];
        }
        return total;
    }
    
    public Double getAgentBordaCount(PreferenceProfile pref, int agent) {
        if (agent <= 0 || agent > size()) {
            throw new RuntimeException("iProbabilityMatrix: getAgentBordaCount(PreferenceProfile, int): agent not between 1 and size inclusive");
        }
        try {
            normalize();
        } catch (EmptyMatrixException ex) {
            return 0.0d;
        }
        int score = pref.size();
        double total = 0;
        iProfileIterator iter = pref.getIterator();
        while (iter.hasNext(agent)) {
            total += normalized[agent - 1][iter.getNext(agent) - 1] * score--;
        }
        return total;
    }

    public double[][] inPreferenceOrder(PreferenceProfile pp) {
        double[][] newMatrix = new double[size()][objectSize()];
        iProfileIterator pi = pp.getIterator();
        for (int i = 0; i < pp.size(); i++) {
            int agent = i + 1;
            for (int j = 0; j < pp.objectSize(); j++) {
                newMatrix[i][j] = read(agent, pi.getNext(agent));
            }
        }
        return newMatrix;
    }
    
    public boolean equals(iProbabilityMatrix other) {
        if (size() != other.size() || objectSize() != other.objectSize()) {
            return false;
        }
        try {
            normalize();
            other.normalize();
        } catch (EmptyMatrixException ex) {
            throw new RuntimeException("iProbabilityMatrix: equals(iPM): EmptyMatrixException");
        }
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < objectSize(); j++) {
                if (normalized[i][j] != other.normalized[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public iProbabilityMatrix invert() {
        try {
            normalize();
        } catch (EmptyMatrixException ex) {
            System.out.println("iProbabilityMatrix: invert(): fail to normalize");
            return null;
        }
        iProbabilityMatrix matrix = new AbstractProbabilityMatrix(objects, agents); //inverting the matrix size
        for (int i = 0; i < agents; i++) {
            for (int j = 0; j < objects; j++) {
                matrix.normalized[j][i] = normalized[i][j];
            }
        }
        ProbabilisticProbabilityMatrix out = new ProbabilisticProbabilityMatrix(objects, agents);
        try {
            out.addPMatrix(matrix);
        } catch (EmptyMatrixException ex) {
            System.out.println("iProbabilityMatrix: invert(): EmptyMatrixException");
            return null;
        }
        return out;
    }
}
