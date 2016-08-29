/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness.ConceptSimilarity.InformationConctentMeasures;

import ConceptRelatedness.Concept.Concept;
import SemanticResource.SemanticResourceHandler;

/**
 *
 * @author sobhy
 */
public class UnitInformationContentCalculator extends InformationContentCalculator{

    public UnitInformationContentCalculator(SemanticResourceHandler resource) {
        super(resource);
    }

    @Override
    public double calculateIC(Concept concept) {
        return 1.0;
    }
    
}
