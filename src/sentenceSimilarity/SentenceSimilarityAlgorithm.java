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
import partOfSpeechTagger.PostHandler;

/**
 *
 * @author sobhy
 * @param <WSDA> Word Sense Disambiguation Algorithm  
 * @param <RelatAlgo> Relatedness between Concepts Algorithm
 * @param <POST>   Part Of Speech Tagger

 */
public abstract class SentenceSimilarityAlgorithm<WSDA extends WordSenseDisambiguationAlgorithm, RelatAlgo extends ConceptsRelatednessAlgorithm> {

    protected String sentence1;
    protected String sentence2;
    protected Word[] sentenceWords1;
    protected Word[] sentenceWords2;
    protected SentenceSenseDisambiguator<WSDA> sentenceSenseisambiguator;
    protected RelatAlgo conceptRelatednessAlgorithm;
    protected double similarity;
    // default 10
    protected int windowSize;

    public SentenceSimilarityAlgorithm(String sentence1, String sentence2, SentenceSenseDisambiguator<WSDA> disambiguator, RelatAlgo conceptRelatednessAlgorithm) {
        this.sentence1 = sentence1;
        this.sentence2 = sentence2;
        this.windowSize = 10;
        this.sentenceSenseisambiguator = disambiguator;
        this.conceptRelatednessAlgorithm = conceptRelatednessAlgorithm;
        this.sentenceWords1 = getSenteceWords(sentence1);
        this.sentenceWords2 = getSenteceWords(sentence2);
    }

    public abstract void execute();
    public abstract double calculateSimilarity();

    public void setWindowSize(int size) {
        this.windowSize = size;
    }

    private Word[] getSenteceWords(String sentence) {
        this.sentenceSenseisambiguator.setSentence(sentence);
        this.sentenceSenseisambiguator.execute(windowSize);
        return this.sentenceSenseisambiguator.getDisambiguatedWords();
    }
    public double getSimilarity(){
        return this.similarity;
    }

}
