/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SemanticResource;

import ConceptRelatedness.Concept.Concept;
import WordSenseDisambiguation.Word;
import java.util.ArrayList;


public abstract class  SemanticResourceHandler {
    /**
     * initialize parameters and collect statistics
     */
    // static because the implementing class should be singleton
    protected static FindLCSAlgorithm lcsGetter;
    protected  static Concept root;
    public abstract void init();
    public  abstract Concept[]getWrappingConcepts(Word word);

    /**
     * returns the number of all concepts in the taxonomy
     *
     * @return
     */
    public abstract int getNumberOfConcepts();

    /**
     * returns 
     *
     * @return the maximum depth form the root of the taxonomy
     */
    public abstract int getMaxDepth();

    /**
     * returns 
     *
     * @return the number of leaves in the taxonomy
     */
    public abstract int getMaxLeaves();

    /**
     * returns 
     *
     * @return the average number of direct children in the taxonomy
     */
    public abstract double getAverageNumberOfDirectSuccessors();

    /**
     * returns 
     * Lowest Common Subsummer of both concepts
     *
     * @param concept1
     * @param concept2
     * @param lcs
     * @return the shortest path between concepts concept1 concept2 that pass by
     */
    public abstract ArrayList<Concept> getShortestPath(Concept concept1, Concept concept2, Concept lcs);

    /**
     * returns 
     * @param concept1
     * @param concept2
     * @return the Lowest Common Subsummer of the concepts concept1 concept2
     */
    public Concept getLcs(Concept concept1, Concept concept2){
        return lcsGetter.getLcs(concept1, concept2);
    }
    public Concept getRoot(){
        return this.root;
    }

}
