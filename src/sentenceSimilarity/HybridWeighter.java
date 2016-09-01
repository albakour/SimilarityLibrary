/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentenceSimilarity;

import ConceptRelatedness.ConceptsRelatednessAlgorithm;
import Helper.Helper;
import WordSenseDisambiguation.Word;

/**
 *
 * @author sobhy
 */
public class HybridWeighter extends EdgeWeighter {

    public HybridWeighter(ConceptsRelatednessAlgorithm measure) {
        super(measure);
    }

    /**
     *
     * @param word1
     * @param word2
     * @param sentence1
     * @param sentence2
     * @return the weight of the edge between the two words
     */
    @Override
    public double calculateWeight(Word word1, Word word2, Word[] sentence1, Word[] sentence2) {
        //
        // weight=sim(w1,w2)*(Ic(w1)+Ic(w2))
        //
        //
        SimilarityWeighter similarityWeighter = new SimilarityWeighter(relatednessMeasure);
        double result = similarityWeighter.calculateWeight(word1, word2, sentence1, sentence2);
        double c1, c2;
        if (result == 0) {
            return 0;
        }
        c1 = contributionInSense(word1, sentence1);
        c2 = contributionInSense(word2, sentence2);
        result *= (c1 + c2);
        return result;
    }

    /**
     *
     * @param word
     * @param sentence
     * @return the contribution of the word in the sense of the sentence
     */
    private double contributionInSense(Word word, Word[] sentence) {
        double sense = 0;
        for (int i = 0; i < sentence.length; i++) {
            if (!Helper.ignore(sentence[i].value)) {
                sense += sentence[i].getIc();
            }
        }
        return word.getIc() / sense;
    }

}
