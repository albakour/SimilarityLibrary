/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness.Concept;

import ConceptRelatedness.GlossMesures.GlossAlgorithm;
import java.util.ArrayList;
import SemanticResource.SemanticResourceHandler;
import PosTagging.PartOfSpeech;

/**
 *
 * @author sobhy
 * @param <V>
 */
public abstract class Concept<V, S extends SemanticResourceHandler> {

    /**
     *
     * attribute to represent the object that should be the concept itself its
     * type depends on the ontotlgy in consideration
     *
     */
    protected V value;
    protected S semanticResource;
    public boolean inTaxonomy;
    protected PartOfSpeech.Type partOfSpeech;
    ArrayList<Concept> RelatedConcepts;

    public Concept(V value) {
        this.value = value;
    }

    /**
     *
     * equivalent to toString function but it is distinguished by the name to
     * make sure that any class implements this class should implement this
     * function not inherit it form Object class
     *
     * @return
     */
    public abstract String representAsString();

    /**
     * to get the children nodes which are in the next level in the taxonomy
     *
     * @return
     */
    public abstract Concept[] getDirectAncestors();

    /**
     * to get the first level of parents (if there are more than on parent)
     *
     * @return
     */
    public abstract Concept[] getDirectSuccessors();

    /**
     * the length of the path from the root
     *
     * @return
     */
    public abstract int getDepth();

    /**
     * returns the count of all successors of the concept in all levels
     *
     * @return
     */
    public abstract int countSuccessors();

    /**
     * return the count of all the leaves that have this concept as an ancestor
     *
     * @return
     */
    public abstract int countSuccessorLeaves();

    /**
     *
     * returns the count of all ancestors of this concept
     *
     * @return
     */
    public abstract int countAncestors();

    public abstract String[] getDirectWordBag();

    // get concepts that contain this cocept
    public abstract Concept[] getContainingConcepts();

    // get concepts that represent parts of this concept
    public abstract Concept[] getPartsConcepts();

    public abstract Concept[] getGroupConcepts();

    public abstract Concept[] getSimilarConcepts();

    public abstract String getDefinition();

    /**
     * it is necessary to implement this function to work LCS Algorithm properly
     *
     * @param concept
     * @return
     */
    public abstract boolean equals(Concept concept);

    public ArrayList<String> getWordBag(GlossAlgorithm algorithm) {
        RelatedConceptsGenerator generator = algorithm.getRelatedConceptsGenerator();
        this.RelatedConcepts = generator.generate(this);
        ArrayList<String> result = new ArrayList<>();
        for (Concept concept : RelatedConcepts) {
            String[] wordBag = concept.getDirectWordBag();
            for (String word : wordBag) {
                result.add(word);
            }
        }
        return result;
    }

    public V getValue() {
        return value;
    }

    public S getSemanticResource() {
        return this.semanticResource;
    }

    public void setSemanticResource(S resource) {
        this.semanticResource = resource;
    }

    public void setPartOfSpeech(PartOfSpeech.Type pos) {
        this.partOfSpeech = pos;
    }
    public PartOfSpeech.Type getPartOfSpeech(){
        return this.partOfSpeech;
    }
    
    

}
