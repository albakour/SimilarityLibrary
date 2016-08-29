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
public class OrginalLeskAlgorithm  extends MaximizeRelatednessWSDAlgorithm <TraditionalGlossMeasure> {

    public OrginalLeskAlgorithm(Word[] sentenceWords, Word target,SemanticResourceHandler resource) {
        super(sentenceWords, target,resource);
        TraditionalGlossMeasure measure=new TraditionalGlossMeasure(null,null,resource);
        this.setRelatednessMeasure(measure);
    }

    
}
