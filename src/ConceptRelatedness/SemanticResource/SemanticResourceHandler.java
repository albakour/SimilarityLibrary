/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness.SemanticResource;

import ConceptRelatedness.Concept.Concept;
import java.util.ArrayList;


public abstract class  SemanticResourceHandler {
    /**
     * initialize parameters and collect statistics
     */
    // static because the implementing class should be singleton
    protected static FindLCSAlgorithm lcsGetter;
    public abstract void init();
    public  abstract Concept[]getWrappingConcepts(String word);

    /**
     * returns the number of all concepts in the taxonomy
     *
     * @return
     */
    public abstract int getNumberOfConcepts();

    /**
     * returns the maximum depth form the root of the taxonomy
     *
     * @return
     */
    public abstract int getMaxDepth();

    /**
     * returns the number of leaves in the taxonomy
     *
     * @return
     */
    public abstract int getMaxLeaves();

    /**
     * returns the average number of direct children in the taxonomy
     *
     * @return
     */
    public abstract double getAverageNumberOfDirectSuccessors();

    /**
     * returns the shortest path between concepts concept1 concept2 that pass by
     * Lowest Common Subsummer of both concepts
     *
     * @param concept1
     * @param concept2
     * @param lcs
     * @return
     */
    public abstract ArrayList<Concept> getShortestPath(Concept concept1, Concept concept2, Concept lcs);

    /**
     * returns the Lowest Common Subsummer of the concepts concept1 concept2
     * @param concept1
     * @param concept2
     * @return
     */
    public Concept getLcs(Concept concept1, Concept concept2){
        return lcsGetter.getLcs(concept1, concept2);
    }

}
