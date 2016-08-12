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
public class ZhouEtAlInformationContentFunction extends InformationContentCalculator {
    
    double balanceFactor;
    

    public ZhouEtAlInformationContentFunction(double balanceFactor,SemanticResourceHandler resource) {
        super(resource);
        this.balanceFactor = balanceFactor;
    }
    

    @Override
    public double calculateIC(Concept concept) {
       // formula
        
        //             log( hypo(c)+1)                log(depth(c)
        //  k ( 1 -  __________________ ) + (1-k)( __________________ )
        //             log (max_nodes)               log (max_depth)
        double result;
        double numberOfChildrenPart=1- (double)(Math.log(concept.countSuccessors()+1))/Math.log(semanticResource.getNumberOfConcepts());
        double depthPart= (double)Math.log(concept.getDepth())/Math.log(semanticResource.getMaxDepth());
        result =balanceFactor* numberOfChildrenPart +(1-balanceFactor)* depthPart;
        return result;
    }

}
