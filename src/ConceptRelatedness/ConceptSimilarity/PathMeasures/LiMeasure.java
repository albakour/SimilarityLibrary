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
public class LiMeasure extends PathAlgorithm<UnitFunction> {

    double alpha;
    double beta;
    // optimal parameters experimentally
    //        alpha = 0.2;
    //        beta = 0.6;

    public LiMeasure(double alpha, double beta, Concept concept1, Concept concept2, SemanticResourceHandler resource) {
        super(concept1, concept2, resource);
        this.weighter=new UnitFunction(resource);
        this.alpha = alpha;
        this.beta = beta;
        minimum = 0;
        maximum = Math.tanh(beta * semanticResource.getMaxDepth());
        formula = "sim(c1,c2)=exp(-alpha*len(c1,c2))*tanh(beta*depth(lcs))";

    }

    @Override
    protected double calculateRelatedness() {

        //formula
        //
        // sim(c1,c2)=exp(-alpha*len(c1,c2))*tanh(beta*depth(lcs))
        double first = Math.exp((-1) * alpha * pathLength);
        double result = first * Math.tanh(beta * lcs.getDepth());
        return result;
    }

}
