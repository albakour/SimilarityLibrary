/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentenceSimilarity;

import ConceptRelatedness.Concept.Concept;
import ConceptRelatedness.ConceptSimilarity.InformationConctentMeasures.InformationContentCalculator;
import WordSenseDisambiguation.*;
import ConceptRelatedness.ConceptsRelatednessAlgorithm;
import Helper.Helper;
import java.util.ArrayList;
import partOfSpeechTagger.PostHandler;

/**
 *
 * @author sobhy
 * @param <WSDA>
 * @param <RelatAlgo>
 * @param <POST>
 */
public class LiVectorBasedSentenceSimilarityAlgorithm<WSDA extends WordSenseDisambiguationAlgorithm, RelatAlgo extends ConceptsRelatednessAlgorithm> extends VectorBasedSentenceSimilarityAlgorithm<WSDA,RelatAlgo> {

    // the combine result
    private Word[] combinedSentence;
    // is final because it would be assigned only once
    private final boolean isCombined;
    private final InformationContentCalculator informationContentCalculator;
    private double threshold;
    // internal member to contact between functions
    // used as global variable useful for code organization 
    //
    private Concept matchedSense;

    public LiVectorBasedSentenceSimilarityAlgorithm(String sentence1, String sentence2, SentenceSenseDisambiguator<WSDA> disambiguator, RelatAlgo relatednessAlgorithm, InformationContentCalculator informationContentCalculator) {

        super(sentence1, sentence2, disambiguator, relatednessAlgorithm);
        isCombined = false;
        this.informationContentCalculator = informationContentCalculator;
        threshold=0.1;
    }

    /**
     * calculate the sentence vector
     * @param sentence
     * @return
     */
    @Override
    public Vector generateVector(Word[] sentence) {
        // to do the combination once at the most
        if (!isCombined) {
            combinedSentence = Helper.combineSentences(this.sentenceWords1, this.sentenceWords2);
        }
        // vector entries
        ArrayList<Double> projections = new ArrayList<>();
        // calculating vector entries
        for (int i = 0; i < combinedSentence.length; i++) {
            projections.add(calculateProjection(sentence, i));
        }
        Vector result=new Vector(projections);
        return result;
    }


    /**
     *  to calculate the entry having the index i in a vector
     * @param sentence
     * @param i
     * @return 
     */

    private double calculateProjection(Word[] sentence, int i) {
        // do the combination just once
        // it is not neccessary to do the combination because it is certainly done
        if (!isCombined) {
            combinedSentence = Helper.combineSentences(this.sentenceWords1, this.sentenceWords2);
        }
        // toataly similar
        if (Helper.SentenceContainsWord(sentence, combinedSentence[i])>=0) {
            return 1.0;
        }
        // search for the best similar concept and get the weight
        return calculateWordEntry(combinedSentence[i], sentence);

    }
    /**
     * to calculate the entry of the vector when the word is not common between the two sentences
     * @param word
     * @param sentence
     * @return 
     */

    private double calculateWordEntry(Word word, Word[] sentence) {
        // after applying the disambiguation algorithm, the word may still have multiple senses
        //so there is an array of senses
        Concept[] senses = word.getDisamiguatedSenses();
        //the variable theat holds the closest sense to the target word senses
        // this value is just for initializing it is never used;
        Concept[] closestSenses =new Concept[senses.length] ;

        // scores for each sense of the word to pick the highest
        double[] scores = new double[senses.length];
        for (int i = 0; i < senses.length; i++) {
            double max = -1;
            Concept closestSense=null;
            // iterate to get the closest word to the current sense
            for (int j = 0; j < sentence.length; j++) {
                double tempMax;
                tempMax = getConceptWordScore(senses[i], sentence[j]);
                if (max < tempMax) {
                    max = tempMax;
                    closestSense = matchedSense;
                }
            }
            // the score of the current sense is the maximum relatedness with the words of the other senetnce 
            scores[i] = max;
            // store the sense that make the maximum relatedness
            closestSenses[i]=closestSense;
        }
        double max = -1;
        int senseIndex = -1;
        // getting the best score and the corresponding sense to 
        for (int i = 0; i < senses.length; i++) {
            if (scores[i] > max) {
                max = scores[i];
                senseIndex = i;
            }
        }
        // the formula is 
        //vector[i]=relatedness(ith word,combined sentence)*IC(senes(ith word))*IC(closestSense(ith word))
        double weight = scores[senseIndex];
        if(weight<threshold){
            return 0.0;
        }
        weight *= this.informationContentCalculator.calculateIC(senses[senseIndex]);
        weight *= this.informationContentCalculator.calculateIC(closestSenses[senseIndex]);
        return weight;

    }
/**
 * get the relatedness between a word and a concept which is 
 * the maximum relatedness with the senses of the word
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
                matchedSense = sense;
            }
        }
        return max;
    }

    public void setThreshold(double threshold){
        this.threshold=threshold;
    }

}
