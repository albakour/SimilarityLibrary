/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentenceSimilarity;

import ConceptRelatedness.Concept.Concept;
import ConceptRelatedness.ConceptsRelatednessAlgorithm;
import Helper.Helper;
import WordSenseDisambiguation.SentenceSenseDisambiguator;
import WordSenseDisambiguation.Word;
import WordSenseDisambiguation.WordSenseDisambiguationAlgorithm;
import java.util.ArrayList;
import partOfSpeechTagger.PostHandler;

/**
 *
 * @author sobhy
 * @param <WSDA> Word Sense Disambiguation Algorithm  
 * @param <RelatAlgo> Relatedness between Concepts Algorithm
 * @param <POST>   Part Of Speech Tagger
 */
public class WordOrderAlgorithm<WSDA extends WordSenseDisambiguationAlgorithm, RelatAlgo extends ConceptsRelatednessAlgorithm> extends SentenceSimilarityAlgorithm<WSDA, RelatAlgo> {

    Vector orderVector1;
    Vector orderVector2;
    // the combine result
    private Word[] combinedSentence;
    // is final because it would be assigned only once
    private final boolean isCombined;
    private double threshold;

    public WordOrderAlgorithm(String sentece1, String sentence2, SentenceSenseDisambiguator<WSDA> disambiguator,RelatAlgo conceptRelatednessAlgorithm) {
        super(sentece1, sentence2, disambiguator, conceptRelatednessAlgorithm);
        isCombined = false;
        threshold = 0.5;
    }

    @Override
    public void execute() {
        orderVector1 = generateVector(sentenceWords1);
        orderVector2 = generateVector(sentenceWords2);
        similarity = calculateSimilarity();

    }

    @Override
    public double calculateSimilarity() {
        double result = 0.0;
        try {
            Vector difference = orderVector1.subtract(orderVector2);
            Vector summation = orderVector1.add(orderVector2);
            // the formula 
            //         |v1-v2|    
            // sim=1- -----------
            //         |v1+v2|
            result = difference.euclidNorm();
            result /= summation.euclidNorm();
            result = 1 - result;
        } catch (DimensionsDoNotMatchException ex) {
        }
        return result;
    }

    /**
     * calculate the sentence vector
     *
     * @param sentence
     * @return
     */
    public Vector generateVector(Word[] sentence) {
        // to do the combination once at the most
        if (!isCombined) {
            combinedSentence = Helper.combineSentences(this.sentenceWords1, this.sentenceWords2);
        }
        // vector entries
        ArrayList<Double> projections = new ArrayList<>();
        // calculating vector entries
        for (int i = 0; i < combinedSentence.length; i++) {
            projections.add((double) calculateProjection(sentence, i));
        }
        Vector result = new Vector(projections);
        return result;
    }

    /**
     * to calculate the entry having the index i in a vector
     *
     * @param sentence
     * @param i
     * @return
     */
    private int calculateProjection(Word[] sentence, int i) {
        // do the combination just once
        // it is not neccessary to do the combination because it is certainly done
        if (!isCombined) {
            combinedSentence = Helper.combineSentences(this.sentenceWords1, this.sentenceWords2);
        }
        // word match case
        int index = Helper.SentenceContainsWord(sentence, combinedSentence[i]);
        if (index >= 0) {
            return index;
        }
        // search for the best similar concept and get the index
        return calculateWordEntry(combinedSentence[i], sentence);

    }

    /**
     * to calculate the entry of the vector when the word is not common between
     * the two sentences
     *
     * @param word
     * @param sentence
     * @return
     */
    private int calculateWordEntry(Word word, Word[] sentence) {
        // after applying the disambiguation algorithm, the word may still have multiple senses
        //so there is an array of senses
        Concept[] senses = word.getDisamiguatedSenses();

        // scores for each sense of the word to pick the highest
        double[] scores = new double[senses.length];
        // the indexes of the words that correspond to the best match with the senses
        int[] wordIndexes = new int[senses.length];
        for (int i = 0; i < senses.length; i++) {
            double max = -1;
            int indexMax = -1;
            // iterate to get the closest word to the current sense
            for (int j = 0; j < sentence.length; j++) {
                double tempMax;
                tempMax = getConceptWordScore(senses[i], sentence[j]);
                if (max < tempMax) {
                    max = tempMax;
                    indexMax = j;
                }
            }
            // the score of the current sense is the maximum relatedness with the words of the other senetnce 
            scores[i] = max;
            // the index of the word that makes the maximum relatedness
            wordIndexes[i] = indexMax;
        }
        double max = -1;
        int bestWordIndex = -1;
        // getting the best score 
        for (int i = 0; i < senses.length; i++) {
            if (scores[i] > max) {
                max = scores[i];
                bestWordIndex = wordIndexes[i];
            }
        }
        //if the relatedness is less than the threshold 
        //then the word is considered to not have any similar words in the other sentence
        if (max < threshold) {
            return 0;
        }
        // return the index of the best match with the cosidered word
        return bestWordIndex;

    }

    /**
     * get the relatedness between a word and a concept which is the maximum
     * relatedness with the senses of the word
     *
     * @param concept
     * @param word
     * @return
     */
    private double getConceptWordScore(Concept concept, Word word) {
        double max = -1;
        Concept[] senses = word.getDisamiguatedSenses();
        // getting the max relatedness
        for (Concept sense : senses) {
            double rel;
            // initialize the relatedness algorithm
            this.conceptRelatednessAlgorithm.setFirstConcept(concept);
            this.conceptRelatednessAlgorithm.setSecondConcept(sense);
            // excute the algorithm
            this.conceptRelatednessAlgorithm.execute();
            rel = this.conceptRelatednessAlgorithm.getNormalizedRelatedness();
            if (max < rel) {
                max = rel;
            }
        }
        return max;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

}
