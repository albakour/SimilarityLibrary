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
public class ExtendedLeskAlgorithm extends MaximizeRelatednessWSDAlgorithm {

    public ExtendedLeskAlgorithm(Word[] sentenceWords, Word target) {
        super(sentenceWords, target);
        ExtendedGlossMasure measure = new ExtendedGlossMasure();
        this.setRelatednessMeasure(measure);

    }

    public ExtendedLeskAlgorithm() {
        super();
        ExtendedGlossMasure measure = new ExtendedGlossMasure();
        this.setRelatednessMeasure(measure);
    }

}
