/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness.Concept;

import PosTagging.PartOfSpeech;
import java.util.ArrayList;

/**
 *
 * @author sobhy
 */
public class ExtendedRelatedConceptsGenerator implements RelatedConceptsGenerator {

    @Override
    public ArrayList<Concept> generate(Concept concept) {
        ArrayList<Concept> result = new ArrayList<>();
        result.add(concept);
        if (concept.partOfSpeech == PartOfSpeech.Type.noun) {
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
        } else if (concept.partOfSpeech == PartOfSpeech.Type.verb) {
            Concept[] group = concept.getGroupConcepts();
            Concept[] parents = concept.getDirectAncestors();
            Concept[] children = concept.getDirectSuccessors();
            for (Concept cncpt : group) {
                result.add(cncpt);
            }
            for (Concept cncpt : parents) {
                result.add(cncpt);
            }
            for (Concept cncpt : children) {
                result.add(cncpt);
            }

        } else if (concept.partOfSpeech == PartOfSpeech.Type.adjective ||concept.partOfSpeech == PartOfSpeech.Type.adverb) {
            Concept[] group = concept.getGroupConcepts();
            Concept[] similars = concept.getSimilarConcepts();
            for (Concept cncpt : group) {
                result.add(cncpt);
            }
            for (Concept cncpt : similars) {
                result.add(cncpt);
            }

        }
        return result;

    }

}
