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
import ConceptRelatedness.ConceptSimilarity.InformationConctentMeasures.*;

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
    protected double informationContent;
    protected InformationContentCalculator icCalculator;
    protected boolean isIcCalculated;
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
     *
     * @return the children nodes which are in the next level in the taxonomy
     */
    public abstract Concept[] getDirectAncestors();

    /**
     *
     * @return the first level of parents (if there are more than on parent)
     */
    public abstract Concept[] getDirectSuccessors();

    /**
     * the length of the path from the root
     *
     * @return the length of the path from the root
     */
    public abstract int getDepth();

    /**
     *
     * @return the count of all successors of the concept in all levels
     */
    public abstract int countSuccessors();

    /**
     *
     * @return the count of all the leaves that have this concept as an ancestor
     */
    public abstract int countSuccessorLeaves();

    /**
     *
     * returns the count of all ancestors of this concept
     *
     * @return
     */
    public abstract int countAncestors();

    /**
     *
     * @return the words listed in the gloss of the concept
     */
    public abstract String[] getDirectWordBag();

     /**
      * 
      * @return  get concepts that contain this concept
      */
    public abstract Concept[] getContainingConcepts();

    /**
     * get concepts that represent parts of this concept
     *
     * @return concepts that are parts of this concept
     */
    public abstract Concept[] getPartsConcepts();

    /**
     *
     * @return concepts that which are in the same group of sense with this
     * concept
     *
     */
    public abstract Concept[] getGroupConcepts();

    /**
     *
     * @return return what semantic resource provide as similar senses
     */
    public abstract Concept[] getSimilarConcepts();

    /**
     *
     * @return the definition of the concept
     */
    public abstract String getDefinition();

    /**
     * it is necessary to implement this function to work LCS Algorithm properly
     *
     * @param concept
     * @return
     */
    public abstract boolean equals(Concept concept);

    /**
     *
     * @return the information content of this concept
     */
    public double getIc() {
        InformationContentCalculator currentCalculator = InformationContentCalculatorFactory.produceObject();
        if (currentCalculator.equals(this.icCalculator)) {
            if (isIcCalculated) {
                return this.informationContent;
            }
        }
        this.icCalculator = currentCalculator;
        if (this.partOfSpeech == PartOfSpeech.Type.noun) {
            this.informationContent = icCalculator.calculateIC(this);
        } else {
            this.informationContent = 0;
        }
        isIcCalculated = true;
        return this.informationContent;
    }

    /**
     *
     * @param generator the related concepts generator to be used to generate
     * the related words
     * @return the words that can be considered as defining words of this
     * concept
     */
    public ArrayList<String> getWordBag(RelatedConceptsGenerator generator) {
        //RelatedConceptsGenerator generator = algorithm.getRelatedConceptsGenerator();
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

    public PartOfSpeech.Type getPartOfSpeech() {
        return this.partOfSpeech;
    }

    public InformationContentCalculator getIcCalculator() {
        return this.icCalculator;
    }

}
