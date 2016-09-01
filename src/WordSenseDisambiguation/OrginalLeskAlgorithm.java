/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WordSenseDisambiguation;

import SemanticResource.SemanticResourceHandler;
import ConceptRelatedness.GlossMesures.TraditionalGlossMeasure;

/**
 *
 * @author sobhy
 */
public class OrginalLeskAlgorithm extends MaximizeRelatednessWSDAlgorithm {

    public OrginalLeskAlgorithm(Word[] sentenceWords, Word target) {
        super(sentenceWords, target);
        TraditionalGlossMeasure measure = new TraditionalGlossMeasure();
        this.setRelatednessMeasure(measure);
    }

    public OrginalLeskAlgorithm() {
        super();
        TraditionalGlossMeasure measure = new TraditionalGlossMeasure();
        this.setRelatednessMeasure(measure);
    }

}
