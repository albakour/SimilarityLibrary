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
 */
public class StanfordPostHandler extends PostHandler<MaxentTagger> {

    String[] nounTags = {"NN", "NNS", "NNP", "NNPS"};
    String[] adjectiveTags = {"JJ", "JJR", "JJS"};
    String[] adverbTags = {"RB", "RBR", "RBS"};
    String[] verbTags = {"VB", "VBD", "VBG", "VBN", "VBP", "VBZ"};

    /**
     *
     * @param path
     */
    @Override
    public void connect(String path) {
        taggerPath = path;
        tagger = new MaxentTagger(taggerPath);
    }

    /**
     *
     * @param taggedSentence
     * @return 
     */
    @Override
    public PartOfSpeech.Type[] getPosTypes(String taggedSentence) {
        String[] words = taggedSentence.split(" ");
        PartOfSpeech.Type[] result = new PartOfSpeech.Type[words.length];
        boolean doneWord = false;
        for (int i = 0; i < words.length; i++) {
            String[] wordTag = words[i].split("_");
            String tag = wordTag[1];
            // tag if noun 
            for (int j = 0; j < nounTags.length; j++) {
                if (tag.equals(nounTags[j])) {
                    // result.add(word);
                    result[i] = PartOfSpeech.Type.noun;
                    doneWord = true;
                    break;
                }
            }
            // continue if tagged
            if (doneWord) {
                doneWord = false;
                continue;
            }
            // tag if verb 
            for (int j = 0; j < verbTags.length; j++) {
                if (tag.equals(verbTags[j])) {
                    // result.add(word);
                    result[i] = PartOfSpeech.Type.verb;
                    doneWord = true;
                    break;
                }
            }
            //continue if tagged
            if (doneWord) {
                doneWord = false;
                continue;
            }
            // tag if adjective 
            for (int j = 0; j < adjectiveTags.length; j++) {
                if (tag.equals(adjectiveTags[j])) {
                    // result.add(word);
                    result[i] = PartOfSpeech.Type.adjective;
                    doneWord = true;
                    break;
                }
            }
            // continue if tagged
            if (doneWord) {
                doneWord = false;
                continue;
            }
            // tag if adverb
            for (int j = 0; j < adverbTags.length; j++) {
                if (tag.equals(adverbTags[j])) {
                    // result.add(word);
                    result[i] = PartOfSpeech.Type.adverb;
                    doneWord = true;
                    break;
                }
            }
            // continue if tagged
            if (doneWord) {
                doneWord = false;
                continue;
            }
            result[i]=PartOfSpeech.Type.other;
        }
        return result;

    }

    /**
     *
     * @param sentence
     * @return
     */
    @Override
    public String getTaggedSentence(String sentence) {
        String tagged = tagger.tagString(sentence);
        return tagged;
    }

}
