/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness.ConceptSimilarity.PathMeasures;

import SemanticResource.SemanticResourceHandler;
import ConceptRelatedness.Concept.Concept;
import ConceptRelatedness.*;
import ConceptRelatedness.ConceptSimilarity.SimilarityAlgorithm;
import ConceptRelatedness.ConceptsRelatednessAlgorithm;
import java.util.ArrayList;

/**
 *
 * @author sobhy
 */
public abstract class PathAlgorithm<W extends WeightFunction> extends SimilarityAlgorithm {

    protected double pathLength;
    protected ArrayList<Concept> path;
    protected WeightFunction weighter;

    public PathAlgorithm(Concept concept1, Concept concept2) {
        super(concept1, concept2);
    }

    public PathAlgorithm() {
        super();
    }

    /**
     * execute the algorithm
     */
    @Override
    public void execute() {
        //in case of differsnt part of speech
        if (this.firstConcept.getPartOfSpeech() != this.secondConcept.getPartOfSpeech()) {
            relatedness = 0;
            normalizedRelatedness = 0;
            explanation = "lcs not found";
            //defaultExecution();
            return;
        }
        //the concepts are not in the same taxonomy
        if (this.firstConcept.getPartOfSpeech() != PosTagging.PartOfSpeech.Type.noun || this.secondConcept.getPartOfSpeech() != PosTagging.PartOfSpeech.Type.noun) {
            defaultExecution();
            return;
        }
        lcs = semanticResource.getLcs(firstConcept, secondConcept);
        // rder notice : findLcs() should be called before findShortestPath() bcause the secod uses the first
        if (lcs == null) {
            relatedness = 0;
            normalizedRelatedness = 0;
            explanation = "lcs not found";
            return;
        }
        //
        path = semanticResource.getShortestPath(firstConcept, secondConcept, lcs);

        // order notice : findShortestPath() should be called before calculatePathLength() bcause the secod uses the first
        pathLength = calculatePathLength();

        relatedness = calculateRelatedness();
        normalizedRelatedness = normalizeRelatedness();

        // dealing with the explanation
        explanation += "Lowest Common Subsummer : \n";
        explanation += lcs.representAsString();
        explanation += "\ndepth of lcs : " + lcs.getDepth();

        // print the path on the expalnation
        explanation += "\nSohrtest Path : \n";
        int count = 1;
        for (Concept concept : path) {
            explanation += count + "-" + concept.representAsString() + "\n";
            count++;
        }
        explanation += "\nShortest Path Length : " + pathLength;
        explanation += "\nweight function formula: " + weighter.getFormula();
        explanation += "\nFormula : " + formula;

    }

    @Override
    protected abstract double calculateRelatedness();

    private double calculatePathLength() {
        //  the lenght of the path list is 1
        if (firstConcept.equals(secondConcept)) {

            return 0;
        }

        // to weight the edges
        //  weighter = WeightFunctionFactory.produceObject(this);sd;
        // for variables
        Concept parent, child;
        int len = path.size();

        // there are at least tow members in the shortest path list 
        child = path.get(0);
        parent = path.get(1);

        double result = 0.0;

        // flag to mark that we reached the LCS so the path is dwonwards
        boolean reverse = false;

        for (int i = 1; i < len; i++) {
            // when reaching the Lowest Common Subsumer reverse the direction
            if (child.equals(lcs)) {
                reverse = true;
            }
            if (reverse) {
                // the downwarsds direction
                result += weighter.function(child, parent);
            } else {
                // the upwards direction
                result += weighter.function(parent, child);
            }
            child = parent;
            // to avoid out of index exception 
            if (i < len - 1) {
                parent = path.get(i + 1);
            }

        }
        return result;

    }
}
