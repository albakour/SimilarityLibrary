/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentenceSimilarity;

import WordSenseDisambiguation.Word;
import ConceptRelatedness.ConceptsRelatednessAlgorithm;

/**
 *
 * @author sobhy
 */
public abstract class EdgeWeighter {

    ConceptsRelatednessAlgorithm relatednessMeasure;

    public EdgeWeighter(ConceptsRelatednessAlgorithm measure) {
        this.relatednessMeasure = measure;
    }
/**
 * 
 * @param word1
 * @param word2
 * @param sentence1 the sentence of word1
 * @param sentence2 sentence of word2
 * @return the weight of the edge between word 1 and word2 in matching algorithm
 */
    public abstract double calculateWeight(Word word1, Word word2,Word[] sentence1,Word[] sentence2);

    public void setRelatednessMeasure(ConceptsRelatednessAlgorithm measure) {
        this.relatednessMeasure = measure;
    }

}
