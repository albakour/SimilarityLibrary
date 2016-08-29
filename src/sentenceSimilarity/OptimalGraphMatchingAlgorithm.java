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

    protected double[][] optimalMatching; // optimalMatching[i][j]= weight of the edge between vertices i ,j if
    // the edge i->j is in the optimal matching , -1 if not
    protected double[][] graph;
    protected double optimalMatchWeight; // the weight of the best matching
    
    
    public OptimalGraphMatchingAlgorithm(double[][] graph){
        this.graph=graph;
    }
    
    public abstract void execute();

    public double[][]getOptimalMatching() {
        return optimalMatching;
    }

    public double getOptimalMatchWeight() {
        return optimalMatchWeight;
    }
    public void setGraph(double [][] graph){
        this.graph=graph;
    }

}
