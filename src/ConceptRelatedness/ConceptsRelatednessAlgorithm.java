/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness;

import ConceptRelatedness.Concept.Concept;
import SemanticResource.SemanticResourceHandler;

/**
 *
 * @author sobhy
 */
public abstract class ConceptsRelatednessAlgorithm {

    // the semantic resource
    protected SemanticResourceHandler semanticResource;

    // the final result
    protected double relatedness;
    protected double normalizedRelatedness;

    // concepts to measure the similarity between
    protected Concept firstConcept;
    protected Concept secondConcept;
    //to explain the result of the algorithm
    protected String explanation;
    protected String formula;

    // the boundsof the value of similarity given by this algorithm
    protected double maximum;
    protected double minimum;

    public ConceptsRelatednessAlgorithm(Concept concept1, Concept concept2) {
        this.firstConcept = concept1;
        this.secondConcept = concept2;
        // this.semanticResource = semanticResource;
        this.semanticResource = SemanticResource.SemanticResourceHandlerFactory.produceObject();
        explanation = "";
    }

    public ConceptsRelatednessAlgorithm() {
        this.semanticResource = SemanticResource.SemanticResourceHandlerFactory.produceObject();
        explanation = "";
    }

    /**
     * execute the algorithm
     */
    public abstract void execute();

    /**
     * calculate relatedness value
     *
     * @return the relatedness value
     */
    protected abstract double calculateRelatedness();

    /**
     * maps the relatedness value with a value in [0,1]
     *
     * @return the relatedness in [0,1]
     */
    protected double normalizeRelatedness() {
        return (relatedness - minimum) / (maximum - minimum);
    }

    public String getExplanation() {
        return explanation;
    }

    public String getFormula() {
        return formula;
    }

    public void setFirstConcept(Concept concept) {
        this.firstConcept = concept;
        explanation = "";
    }

    public void setSecondConcept(Concept concept) {
        this.secondConcept = concept;
        explanation = "";
    }

    public double getRelatedness() {
        return this.relatedness;
    }

    public SemanticResourceHandler getSemanticResource() {
        return this.semanticResource;
    }

    public double getMaximum() {
        return this.maximum;
    }

    public double getMinimum() {
        return this.minimum;
    }

    public double getNormalizedRelatedness() {
        return this.normalizedRelatedness;
    }

}
