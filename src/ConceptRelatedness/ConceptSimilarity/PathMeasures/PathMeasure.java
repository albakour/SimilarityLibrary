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
public class PathMeasure extends PathAlgorithm<UnitFunction> {

    public PathMeasure(Concept concept1, Concept concept2) {
        super(concept1, concept2);
        this.weighter = new UnitFunction();
        maximum = 2 * semanticResource.getMaxDepth();
        minimum = 0;
        formula = "sim(c1,c2)=2*max_depth - len(c1,c2)";
    }

    public PathMeasure() {
        super();
        this.weighter = new UnitFunction();
        maximum = 2 * semanticResource.getMaxDepth();
        minimum = 0;
        formula = "sim(c1,c2)=2*max_depth - len(c1,c2)";
    }

    @Override
    protected double calculateRelatedness() {

        //formula
        // sim(c1,c2) = 2*max_depth - len (c1,c2)
        return 2 * semanticResource.getMaxDepth() - this.pathLength;

    }

}
