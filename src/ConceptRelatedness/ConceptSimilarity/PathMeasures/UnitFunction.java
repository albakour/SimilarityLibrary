/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness.ConceptSimilarity.PathMeasures;

import SemanticResource.SemanticResourceHandler;
import ConceptRelatedness.Concept.Concept;


/**
 *
 * @author sobhy
 */
public class UnitFunction implements WeightFunction {
    
    @Override
    public double function(Concept parent, Concept child){
        return 1.0;
    }
    @Override
    public String getFormula(){
        return "weight(parent,child)=1";
    }
    
}
