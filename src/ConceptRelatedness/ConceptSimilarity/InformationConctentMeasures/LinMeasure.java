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
public class LinMeasure extends InformationContentAlgorithm {

    public LinMeasure(Concept concept1, Concept concept2) {
        super(concept1, concept2);
        maximum = 1;
        minimum = 0;
        formula = "sim(c1,c2)=2IC(LCS(c1,c2)/(IC(c1)+IC(c2)))";
    }

    public LinMeasure() {
        super();
        maximum = 1;
        minimum = 0;
        formula = "sim(c1,c2)=2IC(LCS(c1,c2)/(IC(c1)+IC(c2)))";
    }

    @Override
    protected double calculateRelatedness() {
        // formula

        //      2 IC( LCS (c1,c2) )
        //   ___________________________
        //        IC(c1) + IC (c2)
        double result;
        result = 2 * lcs.getIc();
        double down = firstConcept.getIc() + secondConcept.getIc();
        result = (double) result / down;

        return result;
    }

}
