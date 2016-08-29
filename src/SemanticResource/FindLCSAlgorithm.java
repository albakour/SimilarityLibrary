/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SemanticResource;

import ConceptRelatedness.Concept.Concept;

/**
 *
 * @author sobhy
 */
public  interface FindLCSAlgorithm {
    
    /**
     *
     * @param concept1
     * @param concept2
     * @return
     */
    public Concept getLcs(Concept concept1,Concept concept2); 
}
