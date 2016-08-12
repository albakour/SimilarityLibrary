/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WordSenseDisambiguation;

import ConceptRelatedness.Concept.Concept;
import java.util.ArrayList;
import Helper.Helper;

/**
 *
 * @author sobhy
 */
public class SentenceSenseDisambiguator {

    String sentence;
    Word[] sentenceWords;
    int windowSize;
    WordSenseDisambiguationAlgorithm wordSenseDisambiguator;
    boolean isDisambiguated;

    public SentenceSenseDisambiguator(String sentence, WordSenseDisambiguationAlgorithm wordSenseDisambiguator) {
        this.sentence = sentence;
        this.wordSenseDisambiguator = wordSenseDisambiguator;
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
            sentenceWords[i]=wordSenseDisambiguator.getDisambiguatedWord();
            wordSenseDisambiguator.setSentenceWords(sentenceWords);
        }
        isDisambiguated=true;
    }

    public Word[] getDisambiguatedWords() {
        if (isDisambiguated) {
            return sentenceWords;
        }
        return null;
    }

    private Word[] generateSentenceWords() {
        String[] strings = Helper.getWordsFormSentence(sentence);
        ArrayList<Word> words = new ArrayList<>();
        for (String str : strings) {
            if (!Helper.ignore(str)) {
                Word word = new Word(str);
                words.add(word);
            }
        }
        int size = words.size();
        Word[] result = new Word[size];
        for (int i = 0; i < size; i++) {
            result[i] = words.get(i);

        }
        return result;

    }
    public void setSentence(String sentence){
        this.sentence=sentence;
        isDisambiguated=false;
    }
    public void setWidowSize(int size){
        this.windowSize=size;
        isDisambiguated=false;
    }
    public void setWordSenseDisambiguator( WordSenseDisambiguationAlgorithm disambiguator){
        this.wordSenseDisambiguator=disambiguator;
        isDisambiguated=false;
    }
    public void printDisambiguatedWords(){
        if(!isDisambiguated){
            System.out.println("not disambiguated!!");
        }
        System.out.println("Disambiguating the sentence ("+ sentence+"):");
        System.out.println("");
        for(Word word: sentenceWords){
            System.out.println("Disambiguating ("+ word.value+"):");
            
            Concept[] senses=word.getDisamiguatedSenses();
            for(int i=0;i<senses.length;i++){
                System.out.println((i+1)+"-"+senses[i].getDefinition());
            }
            System.out.println("");
        }
    }

}
