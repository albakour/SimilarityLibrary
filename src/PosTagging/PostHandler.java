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

    public abstract void connect(String path);

    public void setTaggerPath(String path) {
        taggerPath = path;
    }

    public abstract PartOfSpeech.Type[] getPosTypes(String sentence);

    public abstract String getTaggedSentence(String sentence);

}