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
public class LeakcockChodorowMeasure extends PathAlgorithm<UnitFunction> {

    public LeakcockChodorowMeasure(Concept concept1, Concept concept2, SemanticResourceHandler resource) {
        super(concept1, concept2, resource);
        this.weighter=new UnitFunction(resource);
        maximum = Math.log(2 * semanticResource.getMaxDepth() + 1);
        minimum = 0;
        formula = "sim(c1,c2)=-log((len(c1,c2)+1)/(2 max_depth+1))";
    }

    @Override
    protected double calculateRelatedness() {
        //formula 
        //                      len (c1,c2)+1
        // sim(c1,c2) = -log( _________________ )
        //                      2 max_depth+1 

        double down = 2 * semanticResource.getMaxDepth() + 1;
        double result = (-1) * Math.log((pathLength + 1) / down);
        return result;
    }

}
