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
public class ResnikkMeasure extends InformationContentAlgorithm {

    public ResnikkMeasure(Concept concept1, Concept concept2) {
        super(concept1, concept2);
        formula = "sim(c1,c2)=IC(LCS(c1,c2))";
        maximum = Double.MAX_VALUE;
        minimum = 0;
    }

    public ResnikkMeasure() {
        super();
        formula = "sim(c1,c2)=IC(LCS(c1,c2))";
        maximum = Double.MAX_VALUE;
        minimum = 0;
    }

    @Override
    protected double calculateRelatedness() {
        // formula
        // IC ( LCS (c1,c2) )

        double result = lcs.getIc();

        return result;
    }

}
