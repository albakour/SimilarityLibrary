/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness.GlossMesures;

import SemanticResource.SemanticResourceHandler;
import ConceptRelatedness.Concept.ExtendedRelatedConceptsGenerator;
import ConceptRelatedness.Concept.Concept;

/**
 *
 * @author sobhy
 */
public class ExtendedGlossMasure extends GlossAlgorithm<ExtendedRelatedConceptsGenerator> {

    public ExtendedGlossMasure(Concept concept1, Concept concept2) {
        super(concept1, concept2);
        ExtendedRelatedConceptsGenerator generator = new ExtendedRelatedConceptsGenerator();
        this.setRelatedConceptsGenerator(generator);
    }

    public ExtendedGlossMasure() {
        super();
        ExtendedRelatedConceptsGenerator generator = new ExtendedRelatedConceptsGenerator();
        this.setRelatedConceptsGenerator(generator);
    }

}
