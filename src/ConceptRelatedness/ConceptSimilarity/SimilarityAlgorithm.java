/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness.ConceptSimilarity;

import ConceptRelatedness.Concept.Concept;
import ConceptRelatedness.*;
import SemanticResource.SemanticResourceHandler;

/**
 *
 * @author sobhy
 */
public abstract class SimilarityAlgorithm extends ConceptsRelatednessAlgorithm {

    // Lowest Common Subsummer
    protected Concept lcs;

    public SimilarityAlgorithm(Concept concept1, Concept concept2) {
        super(concept1, concept2);
    }

    public SimilarityAlgorithm() {
        super();
    }

    /**
     *
     */
    @Override
    public abstract void execute();

    @Override
    protected abstract double calculateRelatedness();

    /**
     * the default behavior in case of no taxonomy provided contains the two
     * considered concepts
     */

    protected void defaultExecution() {
        ConceptsRelatednessAlgorithm defaultMeasure = DefaultConceptRelatednessMeasureFactory.produceObject();
        defaultMeasure.setFirstConcept(firstConcept);
        defaultMeasure.setSecondConcept(secondConcept);
        defaultMeasure.execute();
        this.relatedness = defaultMeasure.getRelatedness();
        this.normalizedRelatedness = defaultMeasure.getNormalizedRelatedness();
        this.explanation = "\nDefault Measure Result\n" + defaultMeasure.getExplanation();
        this.formula = "\n Default Measure Formula\n" + defaultMeasure.getFormula();
        //this.maximum = defaultMeasure.getMaximum();
        //this.minimum = defaultMeasure.getMinimum();

    }

}
