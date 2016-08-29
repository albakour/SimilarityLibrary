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
public abstract class  WeightFunction {
    public SemanticResourceHandler semanticResource;

    public WeightFunction(SemanticResourceHandler resource) {
        this.semanticResource =resource;
    }
    
    public abstract double function (Concept parent, Concept child);
    public abstract String getFormula();
    
}
