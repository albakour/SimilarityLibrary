/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness;

import ConceptRelatedness.GlossMesures.*;

/**
 *
 * @author sobhy
 */
public class DefaultConceptRelatednessMeasureFactory {
    static ConceptsRelatednessAlgorithm currentObject;
/***
 * determines the type of the object wanted
 * it exists because usually you need to specify the type on the application level 
 * not every where 
 * @param measureType the type of which the default measure needed
 */
    public static void setType(String measureType) {
        if (measureType.equals("traditional gloss")) {
            TraditionalGlossMeasure object = new TraditionalGlossMeasure();
            currentObject=object;
            return;
        }
        if (measureType.equals("extended gloss")) {
            ExtendedGlossMasure object = new ExtendedGlossMasure();
            currentObject=object;
            return;
        }
        if (measureType.equals("zero")) {
            ZeroMeasure object = new ZeroMeasure();
            currentObject=object;
            return;
        }
        ZeroMeasure object = new ZeroMeasure();
        currentObject=object;
    }
    /**
     * 
     * @return the current default measure according to configuration file
     */
    public static ConceptsRelatednessAlgorithm produceObject(){
        if(currentObject==null){
            setType("zero");
        }
        return currentObject;
    }

}
