/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WordSenseDisambiguation;

import WordSenseDisambiguation.MaximizeRelatednessWSDAlgorithm;
import ConceptRelatedness.SemanticResource.SemanticResourceHandler;
import WordSenseDisambiguation.Word;

/**
 *
 * @author sobhy
 */
public class OrginalLeskAlgorithm extends MaximizeRelatednessWSDAlgorithm {

    public OrginalLeskAlgorithm(Word[] sentenceWords, Word target,SemanticResourceHandler resource) {
        super(sentenceWords, target,resource);
    }
    
}
