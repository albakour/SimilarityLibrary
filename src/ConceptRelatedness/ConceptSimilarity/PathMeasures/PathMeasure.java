/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness.ConceptSimilarity.PathMeasures;

import ConceptRelatedness.SemanticResource.SemanticResourceHandler;
import ConceptRelatedness.Concept.Concept;

/**
 *
 * @author sobhy
 */
public class PathMeasure extends PathAlgorithm<UnitFunction> {

    public PathMeasure(Concept concept1, Concept concept2, SemanticResourceHandler resource) {
        super(concept1, concept2, resource);
        this.weighter=new UnitFunction(resource);
        maximum = 2 * semanticResource.getMaxDepth();
        minimum = 0;
        formula = "sim(c1,c2)=2*max_depth - len(c1,c2)";
    }

    protected double calculateRelatedness() {

        //formula
        // sim(c1,c2) = 2*max_depth - len (c1,c2)
        return 2 * semanticResource.getMaxDepth() - this.pathLength;

    }

}
