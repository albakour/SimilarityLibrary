/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness;

import ConceptRelatedness.ConceptSimilarity.SimilarityMeasureFactory;
import ConceptRelatedness.GlossMesures.*;

/**
 *
 * @author sobhy
 */
public class ConceptRelatednessMeasureFactory {
    /**
     * produce concept relatedness measures according to configuration file
     * @param type type of measure
     * @return  object represent a concept relatedness measure
     */

    public static ConceptsRelatednessAlgorithm produceObject(String type) {
        ConceptsRelatednessAlgorithm object;
        //SemanticResourceHandler resource = SemanticResource.SemanticResourceHandlerFactory.getCurrentObject();
        if (type.equals("traditional gloss")) {
            object = new TraditionalGlossMeasure();
            return object;
        }
        if (type.equals("extended gloss")) {
            object = new ExtendedGlossMasure();
            return object;
        }
        object = SimilarityMeasureFactory.produceObject(type);
        return object;

    }

}
