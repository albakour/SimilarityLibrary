/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WordSenseDisambiguation;

import ConceptRelatedness.GlossMesures.*;
import SemanticResource.SemanticResourceHandler;
import PosTagging.PostHandler;

/**
 *
 * @author sobhy
 */
public class ExtendedLeskAlgorithm extends MaximizeRelatednessWSDAlgorithm<ExtendedGlossMasure> {

    public ExtendedLeskAlgorithm(Word[] sentenceWords, Word target, SemanticResourceHandler resource) {
        super(sentenceWords, target, resource);
        ExtendedGlossMasure measure = new ExtendedGlossMasure(null, null, resource);
        this.setRelatednessMeasure(measure);

    }

}
