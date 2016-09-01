/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentenceSimilarity;

import ConceptRelatedness.Concept.Concept;
import WordSenseDisambiguation.Word;
import ConceptRelatedness.ConceptsRelatednessAlgorithm;

/**
 *
 * @author sobhy
 */
public class SimilarityWeighter extends EdgeWeighter {

    public SimilarityWeighter(ConceptsRelatednessAlgorithm measure) {
        super(measure);
    }

    /**
     * calculate the relatedness between words which is the max relatedness
     * between the senses
     *
     * @param word1
     * @param word2
     * @return
     */
    @Override
    public double calculateWeight(Word word1, Word word2, Word[] sentence1, Word[] sentence2) {
        double max = -1;
        if (!word1.isDisambiguated || !word2.isDisambiguated) {
            return 0;
        }
        if (word1.equals(word2) &&word1.getPartOfSpeech() == word2.getPartOfSpeech()) {
            return 1;
        }
        Concept[] senses1 = word1.getDisamiguatedSenses();
        Concept[] senses2 = word2.getDisamiguatedSenses();
        for (Concept sense1 : senses1) {
            for (Concept sense2 : senses2) {
                double relatedness;
                this.relatednessMeasure.setFirstConcept(sense1);
                this.relatednessMeasure.setSecondConcept(sense2);
                this.relatednessMeasure.execute();
                relatedness = this.relatednessMeasure.getNormalizedRelatedness();
                if (max < relatedness) {
                    max = relatedness;
                }
            }
        }
        return max;
    }

}
