/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness.ConceptSimilarity.InformationConctentMeasures;

import SemanticResource.SemanticResourceHandler;

/**
 *
 * @author sobhy
 */
public class InformationContentCalculatorFactory {

    private static SemanticResourceHandler semanticResource;
    private static String type;
    private static InformationContentCalculator unitObject;
    private static InformationContentCalculator secoObject;
    private static InformationContentCalculator sanchezObject;

    public static void setSemanticResource(SemanticResourceHandler resource) {
        semanticResource = resource;
        unitObject = new UnitInformationContentCalculator(semanticResource);
        secoObject = new SecoEtAlInformationContentFunction(semanticResource);
        sanchezObject = new SanchezIsernBatetInformationContentFuntion(semanticResource);
    }

    /**
     *
     * @return the current information content calculator object 
     * used in the application 
     */
    public static InformationContentCalculator produceObject() {
        if (type.equals("unit")) {
            return unitObject;
        }
        if (type.equals("seco")) {
            return secoObject;
        }
        if (type.equals("sanchez")) {
            return sanchezObject;
        }
        return unitObject;
    }

    public static void setType(String t) {
        type = t;
    }

}
