/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentenceSimilarity;

import ConceptRelatedness.ConceptsRelatednessAlgorithm;
import WordSenseDisambiguation.SentenceSenseDisambiguator;
import WordSenseDisambiguation.Word;
import WordSenseDisambiguation.WordSenseDisambiguationAlgorithm;
import PosTagging.PostHandler;

/**
 *
 * @author sobhy
 * @param <WSDA> the word sense algorithm that the sentence Disambiguator will use
 * @param <RelatAlgo> the Similarity measure that the sentence similarity will use in
 * calculating the similarity
 * @param <POST> Part Of Speech Tagger
 */
public abstract class VectorBasedSentenceSimilarityAlgorithm extends SentenceSimilarityAlgorithm{

    Vector vector1;
    Vector vector2;

    /**
     *
     * @param sentece1
     * @param sentence2
     * @param disambiguator
     * @param conceptRelatednessAlgorithm
     */
    public VectorBasedSentenceSimilarityAlgorithm(String sentece1, String sentence2, SentenceSenseDisambiguator disambiguator,ConceptsRelatednessAlgorithm conceptRelatednessAlgorithm) {
        super(sentece1, sentence2, disambiguator, conceptRelatednessAlgorithm);
    }
/**
 * 
 * @param sentence
 * @return the vector which represents the sentence in this algorithm 
 */
    public abstract Vector generateVector(Word[] sentence);

    /**
     *
     */
    @Override
    public void execute() {

        vector1 = generateVector(sentenceWords1);
        vector2 = generateVector(sentenceWords2);
        // generating the vectors is before  calculating similarity
        //because calculateSimilarity() uses the vectors 
        similarity = calculateSimilarity();

    }

    @Override
    public double calculateSimilarity() {
        double result = 0.0;

        try {
            result = vector1.cos(vector2);
        } catch (DimensionsDoNotMatchException ex) {
        }
        return result;
    }

}
