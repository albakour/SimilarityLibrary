/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WordSenseDisambiguation;

import ConceptRelatedness.Concept.Concept;
import ConceptRelatedness.ConceptsRelatednessAlgorithm;
import SemanticResource.SemanticResourceHandler;
import java.util.ArrayList;
import PosTagging.PartOfSpeech;
import PosTagging.PostHandler;

/**
 *
 * @author sobhy
 * @param <T>
 */
public class MaximizeRelatednessWSDAlgorithm extends WordSenseDisambiguationAlgorithm {

    ConceptsRelatednessAlgorithm relatednessMeasure;
    int firstWindowIndex;
    int lastWindowIndex;
    int targetWordIndex;

    public MaximizeRelatednessWSDAlgorithm(Word[] sentenceWords, Word target) {
        super(sentenceWords, target);

    }

    public MaximizeRelatednessWSDAlgorithm() {
        super();
    }

    /**
     * execute the algorithm
     */
    @Override
    public void execute() {
        setTargetWordIndex();
        if (targetWordIndex == -1) {
            return;
        }
        if (targetWord.partOfSpeech == PartOfSpeech.Type.other) {
            return;
        }
        setWindowIndexes();
        Concept[] possibleSenses = targetWord.getPossibleSenses();

        int len = possibleSenses.length;
        double scores[] = new double[len];
        double maxScore = -1;
        ArrayList<Concept> disambiguatedSenses = new ArrayList<>();
        // for each sense in possible senses
        for (int i = 0; i < len; i++) {
            scores[i] = getSenseScore(possibleSenses[i]);
            if (maxScore < scores[i]) {
                maxScore = scores[i];
            }
        }
        // add all senses that have the maximum score value
        // beacuse there is no difference between them 
        for (int i = 0; i < len; i++) {
            if (scores[i] == maxScore) {
                disambiguatedSenses.add(possibleSenses[i]);
            }
        }
        targetWord.setDisambiuatedSenses(disambiguatedSenses);

    }

    /**
     *
     * @param sense
     * @return the score of the sentence
     */
    private double getSenseScore(Concept sense) {
        double score = 0;
        for (int i = firstWindowIndex; i <= lastWindowIndex; i++) {
            if (i != targetWordIndex) {
                if (sentenceWords[i].isDisambiguated) {
                    score += getMaxRelatedness(sense, sentenceWords[i].getDisamiguatedSenses());
                } else {
                    score += getMaxRelatedness(sense, sentenceWords[i].getPossibleSenses());
                }
            }
        }
        return score;
    }

    /**
     *
     * @param targetSense
     * @param senses
     * @return the maximum relatedness between
     */
    private double getMaxRelatedness(Concept targetSense, Concept[] senses) {
        double max = -1;
        relatednessMeasure.setFirstConcept(targetSense);
        for (Concept sense : senses) {

            relatednessMeasure.setSecondConcept(sense);
            relatednessMeasure.execute();
            if (relatednessMeasure.getRelatedness() > max) {
                max = relatednessMeasure.getRelatedness();
            }

        }
        return max;
    }

    /**
     * set were widow begins and ends
     */
    private void setWindowIndexes() {
        if (windowSize >= sentenceWords.length) {
            firstWindowIndex = 0;
            lastWindowIndex = sentenceWords.length - 1;
            return;
        }
        firstWindowIndex = Math.max(0, targetWordIndex - windowSize / 2);
        lastWindowIndex = Math.min(sentenceWords.length - 1, firstWindowIndex + windowSize - 1);
        if (lastWindowIndex == sentenceWords.length - 1) {
            firstWindowIndex = Math.max(sentenceWords.length - windowSize, 0);
        }

    }

    private void setTargetWordIndex() {
        for (int i = 0; i < sentenceWords.length; i++) {
            if (targetWord.equals(sentenceWords[i])) {
                targetWordIndex = i;
                return;
            }
        }
        targetWordIndex = -1;

    }

    public void setRelatednessMeasure(ConceptsRelatednessAlgorithm algorithm) {
        this.relatednessMeasure = algorithm;
    }

}
