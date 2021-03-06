/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import MatchingAlgorithm.Auxiliary.PreferenceProfile;
import MatchingAlgorithm.Auxiliary.iProbabilityMatrix;
import MatchingAlgorithm.Auxiliary.iProfileIterator;

/**
 *
 * @author ylo019
 */
public class GraphImpl implements iGraph {
    
    private boolean[][] hasEdge;
    
    public GraphImpl(iProbabilityMatrix ipm, PreferenceProfile pp) {
        hasEdge = new boolean[ipm.size()][ipm.size()];
        update(ipm, pp);
    }
    
    public GraphImpl(int size) {
    	hasEdge = new boolean[size][size];
    }
    
    public void addEdge(int from, int to) {
    	if (from < 0 || to < 0 || from >= hasEdge.length || to >= hasEdge.length) {
    		throw new RuntimeException("GraphImpl add Edge: out of bounds");
    	}
    	hasEdge[from][to] = true;
    }
    
    //put an edge between two nodes (item_x and item_y) iff someone with item_x wants item_y more
    private void update(iProbabilityMatrix ipm, PreferenceProfile pp) {
        for (int i = 0; i < ipm.size(); i++) {
            double[] probabilisticAllocation = ipm.read(i+1);
            iProfileIterator ipi = pp.getIterator();
            boolean[] appearedOnPreference = new boolean[pp.size()];
            while (ipi.hasNext(i+1)) {
                int checkingItem = ipi.getNext(i+1) - 1;
                appearedOnPreference[checkingItem] = true;
                if (probabilisticAllocation[checkingItem] > 0) {
                    for (int j = 0; j < appearedOnPreference.length; j++) {
                        if (checkingItem != j && appearedOnPreference[j]) {
                            hasEdge[checkingItem][j] = true;
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public boolean hasCycles() {
        NodeColour[] nodeStatus = new NodeColour[hasEdge.length];
        Arrays.fill(nodeStatus, NodeColour.WHITE);
        List<Integer> stack = new ArrayList<Integer>();
        stack.add(0);
        while (true) {
            while (!stack.isEmpty()) {
                int topNode = stack.get(stack.size() - 1);
                for (int i = 0; i < nodeStatus.length; i++) {
                    if (hasEdge[topNode][i] && nodeStatus[i] == NodeColour.WHITE) {
                        nodeStatus[topNode] = NodeColour.GREY;
                        stack.add(i);
                        break;
                    } else if (hasEdge[topNode][i] && nodeStatus[i] == NodeColour.GREY) {
                        return true;
                    }
                }
                if (topNode == stack.get(stack.size() - 1)) {
                    nodeStatus[topNode] = NodeColour.BLACK;
                    stack.remove(stack.size() - 1);
                }
            }
            for (int i = 0; i < nodeStatus.length; i++) {
                switch (nodeStatus[i]) {
                    case WHITE:
                        stack.add(i);
                        break;
                    case GREY:
                        throw new RuntimeException("GraphImpl: hasCycles(): found grey vertice while not processing a vertice");
                    case BLACK:
                    default:
                }
            }
            if (stack.isEmpty()) {
                return false;
            }
        }
    }
    
    public boolean hasCycleFrom(int node) {
        NodeColour[] nodeStatus = new NodeColour[hasEdge.length];
        Arrays.fill(nodeStatus, NodeColour.WHITE);
        List<Integer> stack = new ArrayList<Integer>();
        stack.add(node);
        while (!stack.isEmpty()) {
            int topNode = stack.get(stack.size() - 1);
            for (int i = 0; i < nodeStatus.length; i++) {
                if (hasEdge[topNode][i] && nodeStatus[i] == NodeColour.WHITE) {
                    nodeStatus[topNode] = NodeColour.GREY;
                    stack.add(i);
                    break;
                } else if (hasEdge[topNode][i] && nodeStatus[i] == NodeColour.GREY) {
                    if (i == node) {
                    	return true;
                    }
                }
            }
            if (topNode == stack.get(stack.size() - 1)) {
                nodeStatus[topNode] = NodeColour.BLACK;
                stack.remove(stack.size() - 1);
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
        return Arrays.deepToString(hasEdge);
    }    
    
    private enum NodeColour {
        WHITE, GREY, BLACK;
    }
}
