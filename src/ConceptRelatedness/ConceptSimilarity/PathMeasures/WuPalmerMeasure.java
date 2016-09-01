/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness.ConceptSimilarity.PathMeasures;

import SemanticResource.SemanticResourceHandler;
import ConceptRelatedness.Concept.Concept;

/**
 *
 * @author sobhy
 */
public class WuPalmerMeasure extends PathAlgorithm<UnitFunction> {

    public WuPalmerMeasure(Concept concept1, Concept concept2) {
        super(concept1, concept2);
        weighter = new UnitFunction();

        formula = "sim(c1,c2)=2*depth(lcs(c1,c2))/(2*depth(lcs(c1,c2))+len(c1,c2)";
        maximum = 1;
        minimum = 0;
    }

    public WuPalmerMeasure() {
        super();
        weighter = new UnitFunction();
        formula = "sim(c1,c2)=2*depth(lcs(c1,c2))/(2*depth(lcs(c1,c2))+len(c1,c2)";
        maximum = 1;
        minimum = 0;
    }

    @Override
    protected double calculateRelatedness() {
        //formula
        //                  2 depth(lcs(c1,c2))
        // sim(c1,c2)= ______________________________
        //             2depth(lcs(c1,c2))+ len(c1,c2)

        double up = 2 * lcs.getDepth();
        double result = up / (pathLength + up);
        return result;
    }

}
