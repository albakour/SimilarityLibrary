/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness.ConceptSimilarity.PathMeasures;

import ConceptRelatedness.ConceptSimilarity.InformationConctentMeasures.InformationContentCalculator;
import ConceptRelatedness.ConceptSimilarity.InformationConctentMeasures.InformationContentCalculatorFactory;
import SemanticResource.SemanticResourceHandler;
import ConceptRelatedness.Concept.Concept;
import SemanticResource.SemanticResourceHandlerFactory;

/**
 *
 * @author sobhy
 */
public class JiangCornathWeightFunction implements WeightFunction {

    double alpha;
    double beta;

    public JiangCornathWeightFunction(double alpha, double beta) {
        this.alpha = alpha;
        this.beta = beta;
    }

    @Override
    public double function(Concept parent, Concept child) {

        //formula
        //
        //  weight(parent,child)=(beta + (1-beta)(average_number_of_edges/number_of_children(parent)))
        //*((depth(parent)+1)/depth(parent))^lapha*(IC(child)-IC(parent))
        //
        
        InformationContentCalculator informationContentFunction = InformationContentCalculatorFactory.produceObject();
        SemanticResourceHandler resource = SemanticResourceHandlerFactory.produceObject();
        double betaPart = beta + (1 - beta) * (resource.getAverageNumberOfDirectSuccessors() / parent.getDirectSuccessors().length);
        int depth = parent.getDepth();
        double alphaPart = Math.pow((double) (depth + 1) / depth, alpha);
        double icPart = informationContentFunction.calculateIC(child) - informationContentFunction.calculateIC(parent);

        return betaPart * alphaPart * icPart;

    }

    @Override
    public String getFormula() {
        return "weight(parent,child)=(beta + (1-beta)(average_number_of_edges/number_of_childs(parent)))*((depth(parent)+1)/depth(parent))^alpha*(IC(child)-IC(parent))";
    }

}
