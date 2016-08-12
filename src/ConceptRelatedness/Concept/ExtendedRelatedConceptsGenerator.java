/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness.Concept;

import ConceptRelatedness.Concept.Concept;
import java.util.ArrayList;

/**
 *
 * @author sobhy
 */
public class ExtendedRelatedConceptsGenerator extends RelatedConceptsGenerator {

    @Override
    public ArrayList<Concept> generate(Concept concept) {
        ArrayList<Concept> result = new ArrayList<>();
        Concept[] containing = concept.getContainingConcepts();
        Concept[] parts = concept.getPartsConcepts();
        Concept[] parents = concept.getDirectAncestors();
        Concept[] children = concept.getDirectSuccessors();
        for (Concept cncpt : containing) {
            result.add(cncpt);
        }
        for (Concept cncpt : parts) {
            result.add(cncpt);
        }
        for (Concept cncpt : parents) {
            result.add(cncpt);
        }
        for (Concept cncpt : children) {
            result.add(cncpt);
        }
        return result;

    }

}
