/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness;

import ConceptRelatedness.GlossMesures.*;
import ConceptRelatedness.SemanticResource.SemanticResourceHandler;

/**
 *
 * @author sobhy
 */
public class DefaultConceptRelatednessMeasureFactory {
    static ConceptsRelatednessAlgorithm currentObject;

    public static ConceptsRelatednessAlgorithm produceObject(String measureType, SemanticResourceHandler resource) {
        if (measureType.equals("traditional gloss")) {
            TraditionalGlossMeasure object = new TraditionalGlossMeasure(null, null, resource);
            currentObject=object;
            return object;
        }
        if (measureType.equals("extended gloss")) {
            ExtendedGlossMasure object = new ExtendedGlossMasure(null, null, resource);
            currentObject=object;
            return object;
        }
        if (measureType.equals("zero")) {
            ZeroMeasure object = new ZeroMeasure(null, null, resource);
            currentObject=object;
            return object;
        }
        ZeroMeasure object = new ZeroMeasure(null, null, resource);
        currentObject=object;
        return object;
    }
    public static ConceptsRelatednessAlgorithm getCurrentObject(){
        return currentObject;
    }

}
