/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package partOfSpeechTagger;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 *
 * @author sobhy
 */
public class StanfordPostHandler extends PostHandler<MaxentTagger> {

    String[] nounTags = {"NN", "NNS", "NNP", "NNPS"};

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
    public boolean[] getNouns(String taggedSentence) {
        String[] words = taggedSentence.split(" ");
        boolean[] result = new boolean[words.length];
        for (int i = 0; i < words.length; i++) {
            String[] wordTag = words[i].split("_");
            String tag = wordTag[1];
            //String word = wordTag[0];
            for (int j = 0; j < nounTags.length; j++) {
                if (tag.equals(nounTags[j])) {
                    // result.add(word);
                    result[i] = true;
                    continue;
                }
            }
        }
        return result;

    }

    /**
     *
     * @param sentence
     * @return
     */
    @Override
    public String getTaggedSentence(String sentence){
        String tagged=tagger.tagString(sentence);
        return tagged;
    }

}
