/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WordSenseDisambiguation;


import ConceptRelatedness.SemanticResource.SemanticResourceHandler;
import ConceptRelatedness.GlossMesures.TraditionalGlossMeasure;


/**
 *
 * @author sobhy
 */
public class OrginalLeskAlgorithm  extends MaximizeRelatednessWSDAlgorithm <TraditionalGlossMeasure> {

    public OrginalLeskAlgorithm(Word[] sentenceWords, Word target,SemanticResourceHandler resource) {
        super(sentenceWords, target,resource);
        this.setRelatednessMeasure(new TraditionalGlossMeasure(null,null,resource));
    }

    
}
