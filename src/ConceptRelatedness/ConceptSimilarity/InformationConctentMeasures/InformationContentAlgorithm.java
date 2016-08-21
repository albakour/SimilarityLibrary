/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness.ConceptSimilarity.InformationConctentMeasures;

import ConceptRelatedness.SemanticResource.SemanticResourceHandler;
import ConceptRelatedness.Concept.Concept;
import ConceptRelatedness.ConceptSimilarity.SimilarityAlgorithm;

/**
 *
 * @author sobhy
 */
public abstract class InformationContentAlgorithm extends SimilarityAlgorithm {

    protected InformationContentCalculator informationContentFunction;

    public InformationContentAlgorithm(Concept concept1, Concept concept2, SemanticResourceHandler resource) {
        super(concept1, concept2, resource);
    }

    @Override
    protected abstract double calculateRelatedness();

    /**
     *
     */
    @Override
    public void execute() {
        if (!this.firstConcept.inTaxonomy || !this.secondConcept.inTaxonomy) {
            defaultExecution();
            return;
        }

        // findLcs() should be called before  calculateSimilarity() because the second may use the first
        lcs = semanticResource.getLcs(firstConcept, secondConcept);

        relatedness = calculateRelatedness();
        normalizedRelatedness = normalizeRelatedness();
        // dealing with explanation
        explanation += "Lowest Common Subsummer : \n";
        explanation += lcs.representAsString();
        explanation += "\nInformation content function formula : " + informationContentFunction.getFormula();

    }

}
