/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentenceSimilarity;

import ConceptRelatedness.ConceptsRelatednessAlgorithm;
import Helper.Helper;
import WordSenseDisambiguation.SentenceSenseDisambiguator;

/**
 *
 * @author sobhy
 */
public class MouhibiMatchingMeasure extends OptimalGraphMatchingBasedSimilarityAlgorithm {

    public MouhibiMatchingMeasure(String sentece1, String sentence2, EdgeWeighter weighter, OptimalGraphMatchingAlgorithm matchingAlgorithm, SentenceSenseDisambiguator disambiguator, ConceptsRelatednessAlgorithm conceptRelatednessAlgorithm) {
        super(sentece1, sentence2, matchingAlgorithm, disambiguator, conceptRelatednessAlgorithm);
        SimilarityWeighter simWeighter = new SimilarityWeighter(conceptRelatednessAlgorithm);
        this.setEdgeWeighter(simWeighter);
    }

    @Override
    public double calculateSimilarity() {
        double meanLength;//= this.sentenceWords1.length + this.sentenceWords2.length;
        int length = 0;
        //ignoring stop words
        for (int i = 0; i < sentenceWords1.length; i++) {
            if (!Helper.ignore(sentenceWords1[i].value)) {
                length++;
            }
        }
        for (int i = 0; i < sentenceWords2.length; i++) {
            if (!Helper.ignore(sentenceWords2[i].value)) {
                length++;
            }
        }
        meanLength = length;
        double result = (2 * this.matchingAlgorithm.getOptimalMatchWeight()) / meanLength;
        return result;
    }

}
