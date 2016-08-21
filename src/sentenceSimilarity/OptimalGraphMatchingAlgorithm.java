/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentenceSimilarity;

/**
 *
 * @author sobhy
 */
public abstract class OptimalGraphMatchingAlgorithm {

    protected int[] optimalMatch; // optimalMatch[i]= v , where the edge (i,v) is in the optimal matcing 
    protected double[][] graph;
    protected double optimalMatchWeight; // the weight of the best matching
    
    
    public OptimalGraphMatchingAlgorithm(double[][] graph){
        this.graph=graph;
    }
    
    public abstract void execute();

    public int[] getOptimalMatch() {
        return optimalMatch;
    }

    public double getOptimalMatchWeight() {
        return optimalMatchWeight;
    }
    public void setGraph(double [][] graph){
        this.graph=graph;
    }

}
