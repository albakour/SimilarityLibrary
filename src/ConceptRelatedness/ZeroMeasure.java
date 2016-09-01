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
public class ZeroMeasure extends ConceptsRelatednessAlgorithm {

    public ZeroMeasure(Concept concept1, Concept concept2) {
        super(concept1, concept2);
        this.explanation = "zero measure gives zero as relatedness for any two concepts";
        this.formula = "rel(c1,c2)=0";
        this.maximum = 0;
        this.minimum = 0;
        this.normalizedRelatedness = 0;
    }

    public ZeroMeasure() {
        super();
    }

    @Override
    public void execute() {
        relatedness = calculateRelatedness();
    }

    @Override
    protected double calculateRelatedness() {
        return 0.0;
    }

}
