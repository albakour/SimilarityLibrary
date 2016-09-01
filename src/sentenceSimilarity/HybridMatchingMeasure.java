/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentenceSimilarity;

import ConceptRelatedness.ConceptsRelatednessAlgorithm;
import WordSenseDisambiguation.SentenceSenseDisambiguator;
import WordSenseDisambiguation.Word;
import Helper.Helper;

/**
 *
 * @author sobhy
 */
public class HybridMatchingMeasure extends OptimalGraphMatchingBasedSimilarityAlgorithm {

    public HybridMatchingMeasure(String sentece1, String sentence2, EdgeWeighter weighter, OptimalGraphMatchingAlgorithm matchingAlgorithm, SentenceSenseDisambiguator disambiguator, ConceptsRelatednessAlgorithm conceptRelatednessAlgorithm) {
        super(sentece1, sentence2, matchingAlgorithm, disambiguator, conceptRelatednessAlgorithm);
        EdgeWeighter hybridWeighter = new HybridWeighter(conceptRelatednessAlgorithm);
        this.setEdgeWeighter(hybridWeighter);
    }

    @Override
    public double calculateSimilarity() {
        double result;

        result = this.matchingAlgorithm.getOptimalMatchWeight() / 2;
        return result;

    }

    /**
     *
     * @param sentence
     * @return the the sum of information contents of the sentence words
     */
    private double calculateSentenceSense(Word[] sentence) {
        double result = 0;
        for (int i = 0; i < sentence.length; i++) {
            if (!Helper.ignore(sentence[i].value)) {
                result += sentence[i].getIc();
            }
        }
        return result;
    }

}
