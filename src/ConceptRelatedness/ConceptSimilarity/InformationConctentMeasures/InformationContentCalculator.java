/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness.ConceptSimilarity.InformationConctentMeasures;

import SemanticResource.SemanticResourceHandler;
import ConceptRelatedness.Concept.Concept;

/**
 *
 * @author sobhy
 */
public abstract class InformationContentCalculator {

    protected String formula;
    SemanticResourceHandler semanticResource;

    public InformationContentCalculator(SemanticResourceHandler resource) {
        this.semanticResource = resource;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getFormula() {
        return formula;
    }

    /**
     * calculate information content value
     *
     * @param concept purpose concept
     * @return information content value
     */
    public abstract double calculateIC(Concept concept);

}
