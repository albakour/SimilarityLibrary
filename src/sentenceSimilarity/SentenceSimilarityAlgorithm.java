/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentenceSimilarity;

import ConceptRelatedness.ConceptsRelatednessAlgorithm;
import WordSenseDisambiguation.SentenceSenseDisambiguator;
import WordSenseDisambiguation.Word;

/**
 *
 * @author sobhy
 * @param <WSDA> Word Sense Disambiguation Algorithm
 * @param <RelatAlgo> Relatedness between Concepts Algorithm
 * @param <POST> Part Of Speech Tagger
 *
 */
public abstract class SentenceSimilarityAlgorithm {

    protected String sentence1;
    protected String sentence2;
    protected Word[] sentenceWords1;
    protected Word[] sentenceWords2;
    protected SentenceSenseDisambiguator sentenceSensedisambiguator;
    protected ConceptsRelatednessAlgorithm conceptRelatednessAlgorithm;
    protected double similarity;
    // default 10
    protected int windowSize;

    public SentenceSimilarityAlgorithm(String sentence1, String sentence2, SentenceSenseDisambiguator disambiguator, ConceptsRelatednessAlgorithm conceptRelatednessAlgorithm) {
        this.sentence1 = sentence1;
        this.sentence2 = sentence2;
        this.windowSize = 10;
        this.sentenceSensedisambiguator = disambiguator;
        this.conceptRelatednessAlgorithm = conceptRelatednessAlgorithm;
        this.sentenceWords1 = getSenteceWords(sentence1);
        this.sentenceWords2 = getSenteceWords(sentence2);
    }

    /**
     * execute the algorithm
     */
    public abstract void execute();

    /**
     * calculate and return similarity between the two sentences
     * @return similarity between the two sentences
     */
    public abstract double calculateSimilarity();

    /**
     *
     * @param size the size of the window of disambiguation process
     */
    public void setWindowSize(int size) {
        this.windowSize = size;
    }

    private Word[] getSenteceWords(String sentence) {
        this.sentenceSensedisambiguator.setSentence(sentence);
        this.sentenceSensedisambiguator.execute(windowSize);
        return this.sentenceSensedisambiguator.getDisambiguatedWords();
    }

    /**
     *
     * @return similarity between the two sentences
     */
    public double getSimilarity() {
        return this.similarity;
    }

    public void setSentenceSensedsambiguator(SentenceSenseDisambiguator sentenceSensedisambiguator) {
        this.sentenceSensedisambiguator = sentenceSensedisambiguator;
    }

    public void setConceptRelatednessAlgorithm(ConceptsRelatednessAlgorithm algo) {
        this.conceptRelatednessAlgorithm = algo;

    }
}
