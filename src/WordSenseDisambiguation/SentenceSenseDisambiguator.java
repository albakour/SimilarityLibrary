/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WordSenseDisambiguation;

import ConceptRelatedness.Concept.Concept;
import ConceptRelatedness.SemanticResource.SemanticResourceHandler;
import Helper.Helper;
import partOfSpeechTagger.PostHandler;

/**
 *
 * @author sobhy
 * @param <T>
 */
public class SentenceSenseDisambiguator<T extends WordSenseDisambiguationAlgorithm> {

    String sentence;
    Word[] sentenceWords;
    int windowSize;
    T wordSenseDisambiguator;
    boolean isDisambiguated;
    PostHandler posTagger;
    SemanticResourceHandler semanticResource;

    public SentenceSenseDisambiguator(String sentence, T wordSenseDisambiguator, SemanticResourceHandler resource, PostHandler tagger) {
        // remove punctuation
        this.sentence = Helper.prepare(sentence);
        this.wordSenseDisambiguator = wordSenseDisambiguator;
        this.posTagger = tagger;
        this.semanticResource = resource;
    }

    /**
     *
     * @param windowSize
     */
    public void execute(int windowSize) {

        sentenceWords = generateSentenceWords();
        wordSenseDisambiguator.setWindowSize(windowSize);
        wordSenseDisambiguator.setSentenceWords(sentenceWords);
        for (int i = 0; i < sentenceWords.length; i++) {
            wordSenseDisambiguator.setTargetWord(sentenceWords[i]);
            wordSenseDisambiguator.execute();
            sentenceWords[i] = wordSenseDisambiguator.getDisambiguatedWord();
            wordSenseDisambiguator.setSentenceWords(sentenceWords);
        }
        isDisambiguated = true;
    }

    public Word[] getDisambiguatedWords() {
        if (isDisambiguated) {
            return sentenceWords;
        }
        return null;
    }

    private Word[] generateSentenceWords() {
        String[] strings = Helper.getWordsFormSentence(sentence);
        String taggedSentence = posTagger.getTaggedSentence(sentence);
        boolean[] nounMarker = this.posTagger.getNouns(taggedSentence);
        Word[] result = new Word[strings.length];
        for (int i = 0; i < strings.length; i++) {
            Word word = new Word(strings[i], this.semanticResource);
            word.isNoun = nounMarker[i];
            result[i] = word;
        }
        return result;
    }

    public void setSentence(String sentence) {
        this.sentence = Helper.prepare(sentence);
        isDisambiguated = false;
    }

    public void setWidowSize(int size) {
        this.windowSize = size;
        isDisambiguated = false;
    }

    public void setWordSenseDisambiguator(T disambiguator) {
        this.wordSenseDisambiguator = disambiguator;
        isDisambiguated = false;
    }

    public void setPosTagger(PostHandler tagger) {
        this.posTagger = tagger;
    }

    public void printDisambiguatedWords() {
        if (!isDisambiguated) {
            System.out.println("not disambiguated!!");
        }
        System.out.println("Disambiguating the sentence (" + sentence + "):");
        System.out.println("");
        for (Word word : sentenceWords) {
            System.out.println("Disambiguating (" + word.value + "):");

            Concept[] senses = word.getDisamiguatedSenses();
            for (int i = 0; i < senses.length; i++) {
                System.out.println((i + 1) + "-" + senses[i].getDefinition());
            }
            System.out.println("");
        }
    }

}
