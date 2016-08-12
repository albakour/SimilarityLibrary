/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness.Concept;

import ConceptRelatedness.ConceptsRelatednessAlgorithm;
import ConceptRelatedness.GlossMesures.ExtendedGlossMasure;

/**
 *
 * @author sobhy
 */
public class RelatedConceptsGeneratorFactory {
    
    public static RelatedConceptsGenerator produceObject(ConceptsRelatednessAlgorithm algorithm){
        if(algorithm instanceof ExtendedGlossMasure){
            RelatedConceptsGenerator object=new ExtendedRelatedConceptsGenerator();
            return object;
        }
        //default path
        RelatedConceptsGenerator object=new TraditionalRelatedConceptsGenerator();
        return object;
    }
    
}
