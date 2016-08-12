/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness.ConceptSimilarity.InformationConctentMeasures;

import ConceptRelatedness.ConceptSimilarity.InformationConctentMeasures.InformationContentCalculator;
import ConceptRelatedness.SemanticResource.SemanticResourceHandler;
import ConceptRelatedness.Concept.Concept;

/**
 *
 * @author sobhy
 */
public class SecoEtAlInformationContentFunction extends InformationContentCalculator {

    public SecoEtAlInformationContentFunction(SemanticResourceHandler resource) {
        super(resource);
        this.formula = "IC(c) = 1- log(hypo(c)+1)/log(max_nodes)";
    }

    @Override
    public double calculateIC(Concept concept) {

        // formula 
        //        log( hypo(c)+1)
        //   1 -  ________________
        //        log (max_nodes)
        double result = (double) Math.log(concept.countSuccessors() + 1) / Math.log(semanticResource.getNumberOfConcepts());
        result = 1 - result;
        return result;
    }

}
