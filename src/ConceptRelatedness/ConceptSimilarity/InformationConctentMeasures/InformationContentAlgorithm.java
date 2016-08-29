/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness.ConceptSimilarity.InformationConctentMeasures;

import SemanticResource.SemanticResourceHandler;
import ConceptRelatedness.Concept.Concept;
import ConceptRelatedness.ConceptSimilarity.SimilarityAlgorithm;
import PosTagging.PartOfSpeech;

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
        if (this.firstConcept.getPartOfSpeech() != this.secondConcept.getPartOfSpeech()) {
            this.relatedness = 0;
            this.normalizedRelatedness = 0;
            explanation="incompaible part of speech";
            return;
        }
        if(this.firstConcept.getPartOfSpeech()!=PartOfSpeech.Type.noun||this.secondConcept.getPartOfSpeech()!=PartOfSpeech.Type.noun){
            defaultExecution();
            return;
        }

        // findLcs() should be called before  calculateSimilarity() because the second may use the first
        lcs = semanticResource.getLcs(firstConcept, secondConcept);
        if (lcs == null) {
            this.relatedness = 0;
            this.normalizedRelatedness = 0;
            explanation = "lcs not found";
            return;
        }

        relatedness = calculateRelatedness();
        normalizedRelatedness = normalizeRelatedness();
        // dealing with explanation
        explanation += "Lowest Common Subsummer : \n";
        explanation += lcs.representAsString();
        explanation += "\nInformation content function formula : " + informationContentFunction.getFormula();
    }

}
