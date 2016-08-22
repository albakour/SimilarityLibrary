/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentenceSimilarity;

import ConceptRelatedness.Concept.Concept;
import ConceptRelatedness.ConceptsRelatednessAlgorithm;
import WordSenseDisambiguation.SentenceSenseDisambiguator;
import WordSenseDisambiguation.Word;
import WordSenseDisambiguation.WordSenseDisambiguationAlgorithm;

/**
 *
 * @author sobhy
 */
public class OptimalGraphMatchingBasedSimilarityAlgorithm<OMAlgo extends OptimalGraphMatchingAlgorithm, WSDA extends WordSenseDisambiguationAlgorithm, RelatAlgo extends ConceptsRelatednessAlgorithm> extends SentenceSimilarityAlgorithm<WSDA, RelatAlgo> {

    private final OMAlgo matchingAlgorithm;
    private double[][] bipartiteGraph;

    public OptimalGraphMatchingBasedSimilarityAlgorithm(String sentece1, String sentence2, OMAlgo matchingAlgorithm, SentenceSenseDisambiguator<WSDA> disambiguator, RelatAlgo conceptRelatednessAlgorithm) {
        super(sentece1, sentence2, disambiguator, conceptRelatednessAlgorithm);
        this.matchingAlgorithm = matchingAlgorithm;
    }

    /**
     *
     */
    @Override
    public void execute() {
        this.bipartiteGraph = generateBipartiteGraph();
        this.matchingAlgorithm.setGraph(bipartiteGraph);
        this.matchingAlgorithm.execute();
        this.similarity = calculateSimilarity();

    }

    @Override
    public double calculateSimilarity() {
        double meanLength = this.sentenceWords1.length + this.sentenceWords2.length;
        meanLength /= 2;
        double result = this.matchingAlgorithm.getOptimalMatchWeight() / meanLength;
        return result;
    }

    /**
     * generate the graph of relatedness between words of the two sentences
     *
     * @return
     */

    private double[][] generateBipartiteGraph() {
        int dim1 = this.sentenceWords1.length;
        int dim2 = this.sentenceWords2.length;
        double[][] graph = new double[dim1][dim2];
        for (int i = 0; i < dim1; i++) {
            for (int j = 0; j < dim2; j++) {
                if (sentenceWords1[i].equals(sentenceWords2[j])) {
                    graph[i][j] = 1.0;
                } else {
                    graph[i][j] = wordWordSimilarity(sentenceWords1[i], sentenceWords2[j]);
                }
            }
        }
        return graph;
    }

    /**
     * calculate the relatedness between words which is the max relatedness
     * between the senses
     *
     * @param word1
     * @param word2
     * @return
     */

    private double wordWordSimilarity(Word word1, Word word2) {
        double max = -1;
        if (!word1.isDisambiguated || !word2.isDisambiguated) {
            return 0;
        }
        Concept[] senses1 = word1.getDisamiguatedSenses();
        Concept[] senses2 = word2.getDisamiguatedSenses();
        for (Concept sense1 : senses1) {
            for (Concept sense2 : senses2) {
                double relatedness;
                this.conceptRelatednessAlgorithm.setFirstConcept(sense1);
                this.conceptRelatednessAlgorithm.setSecondConcept(sense2);
                this.conceptRelatednessAlgorithm.execute();
                relatedness = this.conceptRelatednessAlgorithm.getNormalizedRelatedness();
                if (max < relatedness) {
                    max = relatedness;
                }
            }
        }
        return max;
    }

}
