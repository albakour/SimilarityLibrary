/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WordSenseDisambiguation;

import PosTagging.PartOfSpeech;
import ConceptRelatedness.Concept.Concept;
import ConceptRelatedness.GlossMesures.ExtendedGlossMasure;
import SemanticResource.SemanticResourceHandler;
import Helper.Helper;
import PosTagging.PostHandler;

/**
 *
 * @author sobhy
 * @param <T>
 */
public class SentenceSenseDisambiguator/*<T extends WordSenseDisambiguationAlgorithm>*/ {

    String sentence;
    Word[] sentenceWords;
    int windowSize;
    WordSenseDisambiguationAlgorithm wordSenseDisambiguator;
    boolean isDisambiguated;
    PostHandler posTagger;
    SemanticResourceHandler semanticResource;

    public SentenceSenseDisambiguator(String sentence, WordSenseDisambiguationAlgorithm wordSenseDisambiguator, SemanticResourceHandler resource, PostHandler tagger) {
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
            // because the disambiguator may set null the word
            // if it has to be ignored becauese of the part of speech
            if (wordSenseDisambiguator.getDisambiguatedWord() != null) {
                sentenceWords[i] = wordSenseDisambiguator.getDisambiguatedWord();
            }
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
        PartOfSpeech.Type[] types = this.posTagger.getPosTypes(taggedSentence);
        Word[] result = new Word[strings.length];
        for (int i = 0; i < strings.length; i++) {
            Word word = new Word(strings[i], this.semanticResource);
            word.partOfSpeech = types[i];
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

    public void setWordSenseDisambiguator(WordSenseDisambiguationAlgorithm disambiguator) {
        this.wordSenseDisambiguator = disambiguator;
        isDisambiguated = false;
    }

    public void setPosTagger(PostHandler tagger) {
        this.posTagger = tagger;
    }

    public String resultToString() {
        String result="";
        if (!isDisambiguated) {
           // System.out.println("not disambiguated!!");
            result+="not disambiguated!!\n";
        }
        //System.out.println("Disambiguating the sentence (" + sentence + "):");
        //System.out.println("");
        result+="Disambiguating the sentence (" + sentence + "):\n________\n";
        for (Word word : sentenceWords) {
            Concept[] senses = word.getDisamiguatedSenses();
            if (senses != null) {
                result+="Disambiguating (" + word.value + ") :  ( "+senses.length+" ) out of ("+word.getPossibleSenses().length+" ) possible senses\n\n";
                //System.out.println("Disambiguating (" + word.value + ") : which has "+word.getPossibleSenses().length+" possible senses");
                
                for (int i = 0; i < senses.length; i++) {
                    result+=(i + 1) + "-" + senses[i].getDefinition()+"\n";
                    //System.out.println((i + 1) + "-" + senses[i].getDefinition());
                   // System.out.println(senses[i].getWordBag(new ExtendedGlossMasure(null, null, semanticResource)));
                }
                
            }
            result+="\n";
            //System.out.println("");
        }
        return result;
    }

}
