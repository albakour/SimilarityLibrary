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
public interface WeightFunction {

    /**
     * edge weighter
     *
     * @param parent the parent concept
     * @param child the child concept
     * @return the value of the weight of the edge between the parent and the
     * child
     */

    public abstract double function(Concept parent, Concept child);

    public abstract String getFormula();

}
