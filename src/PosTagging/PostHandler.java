/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PosTagging;

import WordSenseDisambiguation.Word;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 *
 * @author sobhy
 * @param <T>
 */
public abstract class PostHandler<T> {

    String taggerPath;
    T tagger;

    /**
     * connect to the knowledge base
     *
     * @param path path of knowledge base file
     */
    public abstract void connect(String path);

    public void setTaggerPath(String path) {
        taggerPath = path;
    }

    /**
     *
     * @param sentence target sentence 
     * @return the part of speech types of the word in order
     */
    public abstract PartOfSpeech.Type[] getPosTypes(String sentence);
/**
 *  
 * @param sentence target sentence
 * @return sentence with part of speech tags
 */
    public abstract String getTaggedSentence(String sentence);

}
