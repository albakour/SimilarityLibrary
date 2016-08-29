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

    public static void setSemanticResource(SemanticResourceHandler resource) {
        semanticResource = resource;
    }

//    public InformationContentCalculatorFactory(SemanticResourceHandler resource) {
//        this.semanticResource = resource;
//    }
    public static InformationContentCalculator produceObject() {
        InformationContentCalculator object;
        //object=new SecoEtAlInformationContentFunction();
        //object = new SanchezIsernBatetInformationContentFuntion(semanticResource);
        object=new UnitInformationContentCalculator(semanticResource);
        return object;
    }

}
