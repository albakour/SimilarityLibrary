/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness.Concept;

import java.util.ArrayList;

/**
 *
 * @author sobhy
 */
public interface RelatedConceptsGenerator {
    
    public abstract ArrayList<Concept> generate(Concept concept);
    
}
