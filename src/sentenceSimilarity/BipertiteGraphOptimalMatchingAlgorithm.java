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
public class BipertiteGraphOptimalMatchingAlgorithm extends OptimalGraphMatchingAlgorithm {

    double[][] tempGraph; // the graph after doing the modifiacations to fit the algorithm
    boolean[][] zeroEdgedGraph; // the subgraph that has only zero weight edges and all of them
    private final double epsilon = 1e-9; // epsilon to use when equality conditions on double values 
    private int matchSize; // the number of edges in the optimal matching 
    private UnWeightedBipertiteGraph bipertiteHandler; // object to handler the unweighted bipertite graph needed algorithms

    public BipertiteGraphOptimalMatchingAlgorithm(double[][] graph) {
        super(graph);
    }

    @Override
    public void execute() {
        tempGraph = this.graph;
        // if the graph has different dimensions , balance them
        balanceTempGraph();
        //  modify the graph to fit the a minimum problem 
        tempGraph = convertToMinimumProblem();
        initZeroGraph();
        initTempGraph();
        do {
            // get the new unweigheted zero edged graph
            updateZeroEdgedGraph();
            bipertiteHandler = new UnWeightedBipertiteGraph(zeroEdgedGraph);
            bipertiteHandler.execute();
            this.matchSize = bipertiteHandler.getCardinality();
            if (reachPerfectMatch()) {
                this.optimalMatch = bipertiteHandler.getleftMatches();
                break;
            }
            // modify the weights to enable more iteration to reach to a perfect matching
            adjustWeights(bipertiteHandler.getLeftMinVertexCover(), bipertiteHandler.getRightMinVertexCover());
        } while (true);
        optimalMatchWeight = calculateWeight();

    }

    /**
     * modify the graph to fit minimum algorithm by finding the maximum and to
     * subtract all the entries from
     *
     * @return
     */

    public double[][] convertToMinimumProblem() {
        double max = -1;
        double[][] result = new double[tempGraph.length][tempGraph.length];
        for (int i = 0; i < tempGraph.length; i++) {
            for (int j = 0; j < tempGraph.length; j++) {
                if (tempGraph[i][j] > max) {
                    max = tempGraph[i][j];
                }
            }

        }
        for (int i = 0; i < tempGraph.length; i++) {
            for (int j = 0; j < tempGraph.length; j++) {
                result[i][j] = max - tempGraph[i][j];
            }
        }
        return result;
    }

    /**
     * add dummy vertices with zero edges to the graph to balance the dimensions
     */
    private void balanceTempGraph() {
        if (tempGraph.length == tempGraph[0].length) {
            return;
        }
        int dim = Math.max(tempGraph.length, tempGraph[0].length);
        int min = Math.min(tempGraph.length, tempGraph[0].length);
        double[][] temp = new double[dim][dim];
        if (dim == tempGraph.length) {
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    if (j < min) {
                        temp[i][j] = tempGraph[i][j];
                    } else {
                        temp[i][j] = 0;
                    }
                }

            }
        } else {
            for (int j = 0; j < dim; j++) {
                for (int i = 0; i < dim; i++) {
                    if (i < min) {
                        temp[i][j] = tempGraph[i][j];
                    } else {
                        temp[i][j] = 0;
                    }
                }

            }

        }
        tempGraph = temp;
    }

    /**
     * the first step of the algorithm which means to determine the best match
     * for each vertex independently which will be recognized as zero edge after
     * subtracting the weight of the minimum weighted edge of the vertex from
     * all the weights of the vertex edges
     */

    private void initTempGraph() {
        // subtract min value of each row from all the values of the row
        for (int i = 0; i < tempGraph.length; i++) {
            double min = Double.MAX_VALUE;
            for (int j = 0; j < tempGraph[i].length; j++) {
                if (tempGraph[i][j] < min) {
                    min = tempGraph[i][j];
                }
            }
            for (int j = 0; j < tempGraph[i].length; j++) {
                tempGraph[i][j] -= min;
            }
        }
        // subtract min value of each column from all the values of the column
        for (int j = 0; j < tempGraph[0].length; j++) {
            double min = Double.MAX_VALUE;
            for (int i = 0; i < tempGraph.length; i++) {
                if (tempGraph[i][j] < min) {
                    min = tempGraph[i][j];
                }
            }
            for (int i = 0; i < tempGraph.length; i++) {
                tempGraph[i][j] -= min;
            }
        }
    }

    /**
     * update to represent the new zero weighted edges
     */
    private void updateZeroEdgedGraph() {
        for (int i = 0; i < tempGraph.length; i++) {
            for (int j = 0; j < tempGraph[i].length; j++) {
                // equlaity with zero in double
                if (Math.abs(tempGraph[i][j]) < epsilon) {
                    zeroEdgedGraph[i][j] = true;
                }
            }
        }
    }

    private void initZeroGraph() {
        zeroEdgedGraph = new boolean[tempGraph.length][tempGraph[0].length];
        for (int i = 0; i < zeroEdgedGraph.length; i++) {
            for (int j = 0; j < zeroEdgedGraph[0].length; j++) {
                zeroEdgedGraph[i][j] = false;
            }
        }
    }
    /**
     * is the current matching include all vertices
     * @return 
     */
    private boolean reachPerfectMatch() {
        if (matchSize == tempGraph.length) {
            return true;
        }
        return false;
    }
    /**
     * update the weights to 
     * increase the weight of the edges which are between vertices from the min vertex cover 
     * decrease the weight of the edges that has just one vertex in the min vertex cover 
     * @param leftCover
     * @param rightCover 
     */
    private void adjustWeights(boolean[] leftCover, boolean[] rightCover) {
        double delta = Double.MAX_VALUE;
        for (int i = 0; i < tempGraph.length; i++) {
            for (int j = 0; j < tempGraph.length; j++) {
                if (!leftCover[i] && !rightCover[j]) {
                    if (delta < tempGraph[i][j]) {
                        delta = tempGraph[i][j];
                    }
                }
            }
        }
        for (int i = 0; i < tempGraph.length; i++) {
            for (int j = 0; j < tempGraph.length; j++) {
                if (!leftCover[i] && !rightCover[j]) {
                    tempGraph[i][j] -= delta;
                    continue;
                }
                if (leftCover[i] && rightCover[j]) {
                    tempGraph[i][j] += delta;
                    continue;
                }
                if (leftCover[i] || rightCover[j]) {
                    continue;
                }
            }
        }

    }
    /**
     * calculate the weight of the best matching found
     * @return 
     */

    private double calculateWeight() {
        double result = 0;
        for (int i = 0; i < this.graph.length; i++) {
            if (optimalMatch[i] != -1) {
                result += this.graph[i][optimalMatch[i]];
            }
        }
        return result;
    }

}
