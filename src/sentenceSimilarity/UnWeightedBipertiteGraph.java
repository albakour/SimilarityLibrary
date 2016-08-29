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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author sobhy
 */
public class UnWeightedBipertiteGraph {

    int leftVertices;// number of left vertices
    int rightVertices;//number of right vertices
    int vertices;//total number of vertices
    public int cardinality;// number of edges in the matching
    boolean[][] bipartiteGraph; // adjacency matrix for unweighted graph
    ArrayList<ArrayList<Integer>> adjacencyList; // represent the graph as adjacency list to
    // iterate on the adjacent vertices of a vertex 
    private int[] mateInMatching;//  mateInMatching[v]=w ,w=the vertex to which v is matched in the current matching,
    //w=-1 if v is not in the matching
    // private int[] leftMatches; // the left part of the matching array  
    private boolean[] inMinVertexCover;  // inMinVertexCover[v] = true iff v is in min vertex cover
    private boolean[] leftMinVertexCover; // left part of min vertex cover
    private boolean[] rightMinVertexCover;// right part of min vertex cover
    private boolean[] marked;            // marked[v] = true iff v is reachable via alternating path
    private int[] edgeTo;                // edgeTo[v] = w if v-w is last edge on path to v 
    // it stores the vertex that was the cause why v in the the alternating path
    private boolean[][] optimalMatching;

    public UnWeightedBipertiteGraph(boolean[][] graph) {
        this.bipartiteGraph = graph;
        int x = graph[0].length;
        leftVertices = graph.length;
        rightVertices = 0;
        // in case of empty graph
        if (leftVertices > 0) {
            rightVertices = graph[0].length;
        }
        init();

    }

    /**
     * initialize variables
     */
    private void init() {
        vertices = leftVertices + rightVertices;
        mateInMatching = new int[vertices];

        // all vertices are not matched
        for (int v = 0; v < vertices; v++) {
            mateInMatching[v] = -1;
        }
        //empty path
        clearPath();
        // creating the adjacency list for each vertex
        createAdjacencyList();

    }

    /**
     * empty path
     */
    private void clearPath() {
        // all vertices are not reachable via alternating path at first
        marked = new boolean[vertices];
        // clear the previous edges in the last path
        edgeTo = new int[vertices];
        for (int v = 0; v < vertices; v++) {
            edgeTo[v] = -1;
        }
    }

    /**
     * execute the algorithm of calculating the best match and the minimum
     * vertex cover
     */
    public void execute() {
        // 
        bestMatch();
        minVertexCover();
        fillVertexCover();
        fillOptimalMatching();

    }

    /**
     * get the best match in the graph
     */
    private void bestMatch() {
        // alternating path algorithm
        while (hasAugmentingPath()) {

            // find one endpoint t in alternating path
            int t = -1;
            for (int v = 0; v < vertices; v++) {
                if (!isMatched(v) && edgeTo[v] != -1) {
                    t = v;
                    break;
                }
            }
            // iterate untill reach to the first vertex in the path
            //while iterating update the old matching to the new one 
            // resulted from the augmenting path found
            for (int v = t; v != -1; v = edgeTo[edgeTo[v]]) {
                int w = edgeTo[v];
                mateInMatching[v] = w;
                mateInMatching[w] = v;
            }
            // each iteration  increases the edges of the match by one edge 
            // beacuse of the augmenting path found that add a new edge
            // if not , it is not augmenting path
            cardinality++;
        }
    }

    private void minVertexCover() {
        // find min vertex cover from marked[] array
        inMinVertexCover = new boolean[vertices];
        for (int v = 0; v < vertices; v++) {
            if (inLeftVertices(v) && !marked[v]) {
                inMinVertexCover[v] = true;
            }
            if (!inLeftVertices(v) && marked[v]) {
                inMinVertexCover[v] = true;
            }
        }
    }

    private boolean hasAugmentingPath() {
        clearPath();
        // breadth-first search (starting from all unmatched vertices on left side of bipartition)
        Queue<Integer> queue = new LinkedList<>();
        for (int v = 0; v < vertices; v++) {
            if (inLeftVertices(v) && !isMatched(v)) {
                queue.add(v);
                marked[v] = true;
            }
        }

        // run BFS, stopping as soon as an alternating path is found
        while (!queue.isEmpty()) {
            int v = queue.remove();
            for (int w : adjacentVertices(v)) {

                // either (1) forward edge not in matching or (2) backward edge in matching
                if (isResidualGraphEdge(v, w)) {
                    if (!marked[w]) {
                        edgeTo[w] = v;
                        marked[w] = true;
                        if (!isMatched(w)) {
                            return true;
                        }
                        queue.add(w);
                    }
                }
            }
        }

        return false;
    }

    /**
     * is the vertex in the left side of the graph
     *
     * @param vertex
     * @return
     */
    private boolean inLeftVertices(int vertex) {
        if (vertex < leftVertices) {
            return true;
        }
        return false;
    }

    /**
     * is the vertex matched
     *
     * @param vertex
     * @return
     */
    private boolean isMatched(int vertex) {
        validate(vertex);
        if (mateInMatching[vertex] != -1) {
            return true;
        }
        return false;
    }

    /**
     * is the edge v-w a forward edge not in the matching or a reverse edge in
     * the matching?
     *
     * @param v
     * @param w
     * @return
     */
    private boolean isResidualGraphEdge(int v, int w) {
        if ((mateInMatching[v] != w) && inLeftVertices(v)) {
            return true;
        }
        if ((mateInMatching[v] == w) && !inLeftVertices(v)) {
            return true;
        }
        return false;
    }

    /**
     * get all vertices that adjacent to vertex
     *
     * @param vertex
     * @return
     */
    private ArrayList<Integer> adjacentVertices(int vertex) {
        return this.adjacencyList.get(vertex);
    }

    /**
     * calculate the list of neighbors of each list
     */
    private void createAdjacencyList() {
        this.adjacencyList = new ArrayList<>();
        // the adjacency entry for each vertex in the left side  
        for (int v = 0; v < leftVertices; v++) {
            ArrayList<Integer> entry = new ArrayList<>();
            for (int w = 0; w < rightVertices; w++) {
                if (bipartiteGraph[v][w]) {
                    entry.add(w + leftVertices);
                }

            }
            adjacencyList.add(entry);

        }
        // the adjacency entry for each vertex in the right side
        for (int w = 0; w < rightVertices; w++) {
            ArrayList<Integer> entry = new ArrayList<>();
            for (int v = 0; v < leftVertices; v++) {
                if (bipartiteGraph[v][w]) {
                    entry.add(v);
                }

            }
            adjacencyList.add(entry);

        }
    }

    /**
     * dose the current matching cover all the vertices?
     *
     * @return
     */
    public boolean isPerfect() {
        if (2 * cardinality == vertices) {
            return true;
        }
        return false;
    }

    private void validate(int v) {
        if (v < 0 || v >= vertices) {
            throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (vertices - 1));
        }
    }

    /**
     *
     * @return
     */
    public int getCardinality() {
        return this.cardinality;
    }

    /**
     * divide the vertex cover into left and right parts
     */
    private void fillVertexCover() {
        leftMinVertexCover = new boolean[leftVertices];
        for (int i = 0; i < leftVertices; i++) {
            leftMinVertexCover[i] = inMinVertexCover[i];
        }
        rightMinVertexCover = new boolean[rightVertices];
        for (int i = leftVertices; i < vertices; i++) {
            rightMinVertexCover[i - leftVertices] = inMinVertexCover[i];
        }
    }

    public boolean[] getLeftMinVertexCover() {
        return leftMinVertexCover;

    }

    public boolean[] getRightMinVertexCover() {
        return rightMinVertexCover;

    }

    private void fillOptimalMatching() {
        optimalMatching = new boolean[leftVertices][rightVertices];
        for (int i = 0; i < leftVertices; i++) {
            if (mateInMatching[i] != -1) {
                int match = mateInMatching[i] - leftVertices;
                optimalMatching[i][match] = true;
            }
        }
    }

    public boolean[][] getOptimalMatching() {
        return this.optimalMatching;
    }

}
