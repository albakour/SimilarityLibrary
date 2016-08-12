/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness.ConceptSimilarity.InformationConctentMeasures;

import ConceptRelatedness.SemanticResource.SemanticResourceHandler;
import ConceptRelatedness.Concept.Concept;


/**
 *
 * @author sobhy
 */
public class LinMeasure extends InformationContentAlgorithm {

    public LinMeasure(Concept concept1, Concept concept2, SemanticResourceHandler resource) {
        super(concept1, concept2,resource);
        maximum=1;
        minimum=0;
        formula="sim(c1,c2)=2IC(LCS(c1,c2)/(IC(c1)+IC(c2)))";
    }
    @Override
    protected double calculateRelatedness(){
        // formula
        
        //      2 IC( LCS (c1,c2) )
        //   ___________________________
        //        IC(c1) + IC (c2)
        this.informationContentFunction= InformationContentCalculatorFactory.produceObject();

        double result;
        result=2*informationContentFunction.calculateIC(lcs);
        double down= informationContentFunction.calculateIC(firstConcept)+informationContentFunction.calculateIC(secondConcept);
        result= (double) result/down;
        
        return result;
    }
    
}
