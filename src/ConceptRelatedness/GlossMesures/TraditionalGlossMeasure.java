/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness.GlossMesures;

import SemanticResource.SemanticResourceHandler;
import ConceptRelatedness.Concept.Concept;
import ConceptRelatedness.Concept.TraditionalRelatedConceptsGenerator;

/**
 *
 * @author sobhy
 */
public class TraditionalGlossMeasure extends GlossAlgorithm<TraditionalRelatedConceptsGenerator> {

    public TraditionalGlossMeasure(Concept concept1, Concept concept2, SemanticResourceHandler resource) {
        super(concept1, concept2);
        TraditionalRelatedConceptsGenerator generator = new TraditionalRelatedConceptsGenerator();
        this.setRelatedConceptsGenerator(generator);
    }

    public TraditionalGlossMeasure() {
        super();
        TraditionalRelatedConceptsGenerator generator = new TraditionalRelatedConceptsGenerator();
        this.setRelatedConceptsGenerator(generator);
    }

}
