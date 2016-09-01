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
import Helper.*;

/**
 *
 * @author sobhy
 */
public abstract class OptimalGraphMatchingBasedSimilarityAlgorithm extends SentenceSimilarityAlgorithm {

    protected OptimalGraphMatchingAlgorithm matchingAlgorithm;
    protected double[][] bipartiteGraph;
    protected double[] optimalWeights;
    protected EdgeWeighter edgeWeighter;
    protected String matchedWords;

    public OptimalGraphMatchingBasedSimilarityAlgorithm(String sentece1, String sentence2, OptimalGraphMatchingAlgorithm matchingAlgorithm, SentenceSenseDisambiguator disambiguator, ConceptsRelatednessAlgorithm conceptRelatednessAlgorithm) {
        super(sentece1, sentence2, disambiguator, conceptRelatednessAlgorithm);
        this.matchingAlgorithm = matchingAlgorithm;

        this.setEdgeWeighter(new SimilarityWeighter(conceptRelatednessAlgorithm));

    }

    /**
     * execute the algorithm
     */
    @Override
    public void execute() {
        this.bipartiteGraph = generateBipartiteGraph();
        this.matchingAlgorithm.setGraph(bipartiteGraph);
        this.matchingAlgorithm.execute();
        this.similarity = calculateSimilarity();
        SetMatching();

    }

    @Override
    public abstract double calculateSimilarity();

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
                //ignoring stop words
                if (Helper.ignore(sentenceWords1[i].value) || Helper.ignore(sentenceWords2[j].value)) {
                    graph[i][j] = 0;
                } else {
                    // delegating the operation of calculating the edge weight
                    graph[i][j] = edgeWeighter.calculateWeight(sentenceWords1[i], sentenceWords2[j], sentenceWords1, sentenceWords2);

                }
            }
        }
        return graph;
    }

    /**
     *
     * @return array [n,2] where pairs of matched words are listed
     */
    private String[][] getMatchedWords() {

        int min = Math.min(sentenceWords1.length, sentenceWords2.length);
        String[][] result = new String[min][2];
        optimalWeights = new double[min];
        double[][] optimalMatching = this.matchingAlgorithm.getOptimalMatching();
        int count = 0;
        for (int i = 0; i < optimalMatching.length; i++) {
            for (int j = 0; j < optimalMatching[i].length; j++) {
                if (optimalMatching[i][j] != -1) {
                    result[count][0] = sentenceWords1[i].value;
                    result[count][1] = sentenceWords2[j].value;
                    optimalWeights[count] = optimalMatching[i][j];
                    count++;
                }
            }
        }
        return result;

    }

    /**
     *
     * @return string representing the matched words with the value of the edge
     * between them
     */
    public String getMatching() {
        return this.matchedWords;
    }

    /**
     *
     * generate  string representing the matched words with the value of the edge
     * between them
     */
    private void SetMatching() {
        String result = "";
        String[][] matchedWords = getMatchedWords();
        for (int i = 0; i < matchedWords.length; i++) {
            result += matchedWords[i][0] + "    ->    " + matchedWords[i][1] + "      " + optimalWeights[i] + "\n";
        }
        this.matchedWords = result;
    }

    public void setOptimalMatchingAlgorithm(OptimalGraphMatchingAlgorithm algo) {
        this.matchingAlgorithm = algo;
    }

    public void setEdgeWeighter(EdgeWeighter weighter) {
        this.edgeWeighter = weighter;
        if (edgeWeighter != null) {
            this.edgeWeighter.setRelatednessMeasure(conceptRelatednessAlgorithm);
        }
    }

}
