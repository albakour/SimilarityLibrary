/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness.ConceptSimilarity.InformationConctentMeasures;

import ConceptRelatedness.ConceptSimilarity.InformationConctentMeasures.InformationContentCalculator;
import SemanticResource.SemanticResourceHandler;
import ConceptRelatedness.Concept.Concept;

/**
 *
 * @author sobhy
 */
public class SanchezIsernBatetInformationContentFuntion extends InformationContentCalculator {

    public SanchezIsernBatetInformationContentFuntion(SemanticResourceHandler resource) {
        super(resource);
        this.formula = "IC(c)=-log((1 + leaves(c)/subsumers(c) )/(max_leaves + 1 ))";
    }

    @Override
    public double calculateIC(Concept concept) {
        // formula

        //                  leaves(c) 
        //           1 + ________________
        //                 subsumers(c)
        //   - log ( _______________________ )
        //               max_leaves + 1
        double result;
        double temp = (double) concept.countSuccessorLeaves() / (concept.countAncestors()+1) + 1;
        temp /= (double) semanticResource.getMaxLeaves() + 1;
        result = (-1) * Math.log(temp);
        return result;
    }
}
