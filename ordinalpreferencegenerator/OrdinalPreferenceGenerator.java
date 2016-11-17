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
public class OrdinalPreferenceGenerator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        OrdinalPreferenceGenerator.printPreferenceWithDebug();
    }

    private static void generatePreference(int agentCount, int objCount) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        int[][] desirability = new int[objCount][4];
        System.out.printf("Item quality\n");
        for (int i = 0; i < desirability.length; i++) {
            System.out.printf("%d: ",i);
            for (int j = 0; j < desirability[i].length; j++) {
                desirability[i][j] = (int)(Math.random() * 10);
                System.out.printf("%d ",desirability[i][j]);
            }
            System.out.printf("\n");
        }
        int[][] agentUtilityFunction = new int[agentCount][4];
        System.out.printf("Agent utility\n");
        for (int i = 0; i < agentUtilityFunction.length; i++) {
            System.out.printf("%d: ", i);
            for (int j = 0; j < agentUtilityFunction[i].length; j++) {
                agentUtilityFunction[i][j] = (int)(Math.random() * 10 - 4);
                System.out.printf("%d ",agentUtilityFunction[i][j]);
            }
            System.out.printf("\n");
        }
        System.out.printf("Agent ordinal preference\n");
        int[][] agentPref = new int[agentCount][objCount];
        for (int i = 0; i < agentPref.length; i++) {
            System.out.printf("%d: ", i);
            for (int j = 0; j < agentPref[i].length; j++) {
                agentPref[i][j] = j;
            }
            //selection sort
            for (int k = 0; k < agentPref[i].length - 1; k++) {
                int maxUtility = crossSum(desirability[agentPref[i][k]], agentUtilityFunction[i]);
                int maxIndex = k;
                for (int l = k + 1; l < agentPref[i].length; l++) {
                    int newUtility = crossSum(desirability[agentPref[i][l]], agentUtilityFunction[i]);
                    if (newUtility > maxUtility) {
                        maxIndex = l;
                        maxUtility = newUtility;
                    }
                }
                if (k != maxIndex) {
                   // System.out.printf("swapped %d %d\n",k,maxIndex);
                    agentPref[i][k] += agentPref[i][maxIndex];
                    agentPref[i][maxIndex] = agentPref[i][k] - agentPref[i][maxIndex];
                    agentPref[i][k] -= agentPref[i][maxIndex];
                }
                System.out.printf("%d ",agentPref[i][k]);
            }
            System.out.printf("%d\n",agentPref[i][agentPref[i].length - 1]);
        }
    }

    private static void printPreferenceWithDebug() {
        generatePreference(5,30);
    }

    private static int crossSum(int[] desirability, int[] agentUtilityFunction) {
        int sum = 0;
        for (int i = 0; i < Math.min(desirability.length, agentUtilityFunction.length); i++) {
            sum += desirability[i]*agentUtilityFunction[i];
        }
        return sum;
    }

    //complete preferences only - strict preferences
    private class AssignmentProblem {
        private int[][] preferences;
        AssignmentProblem(int[][] preferences) {
            this.preferences = preferences;
        }
        public int[][] getPreferenes() {
            return preferences;
        }
        public int getAgentCount() {
            return preferences.length;
        }
        public int getObjectCount() {
            return preferences[0].length;
        }
    }
}