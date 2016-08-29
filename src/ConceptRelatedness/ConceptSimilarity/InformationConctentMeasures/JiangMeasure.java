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
public class JiangMeasure extends InformationContentAlgorithm {

    public JiangMeasure(Concept concept1, Concept concept2, SemanticResourceHandler resource) {
        super(concept1, concept2, resource);
        maximum = Double.MAX_VALUE;
        minimum = 0;
        formula="sim(c1,c2)=1/(IC(c1)+IC(c2)-2IC(LCS(c1,c2)))";
    }

    @Override
    protected double calculateRelatedness() {
        // formula
        //                  1
        //  ________________________________________
        //  IC (c1) + IC (c2) - 2 IC ( LCS ( c1,c1) ) 

        this.informationContentFunction = InformationContentCalculatorFactory.produceObject();
        double result;

        result = informationContentFunction.calculateIC(firstConcept) + informationContentFunction.calculateIC(secondConcept);
        result -= 2 * informationContentFunction.calculateIC(lcs);
        result = 1 / result;

        return result;
    }
}
