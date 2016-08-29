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
public class JiangCornathMeasure extends PathAlgorithm<JiangCornathWeightFunction> {

    // this is a hybrid measure where the edges are weighted using information content
    double alpha;
    double beta;
    // the experimantal optimal values
    //  alpha = 0.5;
    //  beta = 0.3;

    public JiangCornathMeasure(Concept concept1, Concept concept2, double alpha, double beta, SemanticResourceHandler resource) {
        super(concept1, concept2, resource);
        this.weighter=new JiangCornathWeightFunction(alpha, beta, resource);
        // alpha and beta are the parameters of the formula 
        //of the weight of the edge between the a parent and a child 
        this.alpha = alpha;
        this.beta = beta;
        maximum = Double.MAX_VALUE;
        minimum = 0;
        this.formula = "sim(c1,c2)=1/len(c1,c2)";
    }

    @Override
    protected double calculateRelatedness() {
        

        double result = (double) 1 / pathLength;
        return result;
    }
    public double getAlpha(){
        return this.alpha;
    }
    public double getBeta(){
        return this.beta;
    }

}
