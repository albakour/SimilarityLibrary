/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WordSenseDisambiguation;

import SemanticResource.SemanticResourceHandler;
import PosTagging.PostHandler;

/**
 *
 * @author sobhy
 */
public abstract class WordSenseDisambiguationAlgorithm {

    protected Word[] sentenceWords;
    protected Word targetWord;
    SemanticResourceHandler semanticResource;
    int windowSize;

    public WordSenseDisambiguationAlgorithm(Word[] sentenceWords, Word target, SemanticResourceHandler resource) {
        this.sentenceWords = sentenceWords;
        this.targetWord = target;
        this.semanticResource = resource;
        windowSize=10;
    }

    public abstract void execute();

    public Word getDisambiguatedWord() {
        if (targetWord.isDisambiguated) {
            return targetWord;
        }
        return null;
    }

    public void setWindowSize(int size) {
        this.windowSize = size;
        //targetWord.isDisambiguated=false;
    }
    public void setSentenceWords(Word[] words){
        this.sentenceWords=words;
        //targetWord.isDisambiguated=false;
    }
    public void setTargetWord(Word target){
        this.targetWord=target;
       targetWord.isDisambiguated=false;
    }
    public SemanticResourceHandler getSemanticResource(){
        return this.semanticResource;
    }

}
