/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness.ConceptSimilarity.PathMeasures;

import ConceptRelatedness.SemanticResource.SemanticResourceHandler;
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

    public PathAlgorithm(Concept concept1, Concept concept2, SemanticResourceHandler resource) {
        super(concept1, concept2, resource);
    }

    @Override
    public void execute() {
        if (!this.firstConcept.inTaxonomy || !this.secondConcept.inTaxonomy) {
            defaultExecution();
            return;
        }
        lcs = semanticResource.getLcs(firstConcept, secondConcept);
        // findLcs() should be called before findShortestPath() bcause the secod uses the first

        //
        path = semanticResource.getShortestPath(firstConcept, secondConcept, lcs);

        // findShortestPath() should be called before calculatePathLength() bcause the secod uses the first
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

//    private ArrayList<Synset> findShortestPath() {
//
//        // the LCS is a node in the shortest path
//        // findLcs() should be called before this funtion in execute() to have LCS found
//        Synset commonParent = lcs;
//
//        // initializing lists 
//        // to be returned 
//        ArrayList<Synset> result = new ArrayList<>();
//
//        // the path form LCS to the first concept
//        ArrayList<Synset> list1;
//        list1 = WordNetHandler.findPathToChild(commonParent, firstConcept);
//
//        // the path form LCS to the escond concept
//        ArrayList<Synset> list2;
//        list2 = WordNetHandler.findPathToChild(commonParent, secondConcept);
//
//        int len1;
//        len1 = list1.size();
//        // use for not foreach because the members are an inverse order
//        for (int i = len1 - 1; i >= 0; i--) {
//            result.add(list1.remove(i));
//        }
//
//        // to avoid repeating the LCS in the result
//        list2.remove(0);
//        for (Synset c : list2) {
//            result.add(c);
//        }
//        return result;
//
//    }
//    protected int depth(Synset concept) {
//        NounSynset entity;
//        entity = (NounSynset) WordNetHandler.getWrappingConcepts("entity")[0];
//        List<Synset> list = findPathToChild(entity, concept);
//        return list.size();
//    }
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
