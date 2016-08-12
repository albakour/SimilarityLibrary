/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness;

import ConceptRelatedness.GlossMesures.TraditionalGlossMeasure;
import WordSenseDisambiguation.OrginalLeskAlgorithm;
import WordSenseDisambiguation.MaximizeRelatednessWSDAlgorithm;



/**
 *
 * @author sobhy
 */
public class ConceptsRelatednessAlgorithmFactory {

    public static ConceptsRelatednessAlgorithm produceObject(MaximizeRelatednessWSDAlgorithm algorithm) {
        if (algorithm instanceof OrginalLeskAlgorithm) {
            TraditionalGlossMeasure object = new TraditionalGlossMeasure(null, null, algorithm.getSemanticResource());
            return object;
        }

        //default path
        TraditionalGlossMeasure object = new TraditionalGlossMeasure(null, null, algorithm.getSemanticResource());
        return object;
    }

}
