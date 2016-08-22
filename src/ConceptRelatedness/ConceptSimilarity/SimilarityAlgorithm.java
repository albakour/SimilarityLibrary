/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness.ConceptSimilarity;

import ConceptRelatedness.Concept.Concept;
import ConceptRelatedness.*;
import ConceptRelatedness.SemanticResource.SemanticResourceHandler;

/**
 *
 * @author sobhy
 */
public abstract class SimilarityAlgorithm extends ConceptsRelatednessAlgorithm {

    // Lowest Common Subsummer

    protected Concept lcs;

    public SimilarityAlgorithm(Concept concept1, Concept concept2, SemanticResourceHandler semanticResource) {
        super(concept1, concept2, semanticResource);
    }

    /**
     *
     */
    @Override
    public abstract void execute();

    @Override
    protected abstract double calculateRelatedness();

    protected void defaultExecution() {
        ConceptsRelatednessAlgorithm defaultMeasure = DefaultConceptRelatednessMeasureFactory.getCurrentObject();
        defaultMeasure.setFirstConcept(firstConcept);
        defaultMeasure.setFirstConcept(secondConcept);
        defaultMeasure.execute();
        this.relatedness = defaultMeasure.getRelatedness();
        this.normalizedRelatedness = defaultMeasure.getNormalizedRelatedness();
        this.explanation = "\nDefault Measure Result\n" + defaultMeasure.getExplanation();
        this.formula = "\n Default Measure Formula\n" + defaultMeasure.getFormula();
        //this.maximum = defaultMeasure.getMaximum();
        //this.minimum = defaultMeasure.getMinimum();

    }

//    protected Synset findLcs() {
//
//        // inicilizing Hashtables 
//        // using Hash tables reduce the complexity finding whether a node is visited or not
//        // the complexity falls from O(n) to O(1) the time to compute hash function
//        Hashtable visitedParents1, visitedParents2;
//        visitedParents1 = new Hashtable();
//        visitedParents2 = new Hashtable();
//
//        // initializing queues
//        // it is a double Breadth First Search (BFS) algorithm 
//        Queue<Synset> queue1 = new LinkedList<>();
//        Queue<Synset> queue2 = new LinkedList<>();
//        queue1.add(firstConcept);
//        queue2.add(secondConcept);
//
//        // the current tree nodes to be expanded
//        Synset currentConcept1, currentConcept2;
//        currentConcept1 = firstConcept;
//        currentConcept2 = secondConcept;
//        // 
//        Synset[] currentParents1;
//        Synset[] currentParents2;
//        // lcs algorithm 
//        // double BFS without a prespecified target
//        boolean end = false;
//        while (!end) {
//
//            // until there are no nodes to be expanded
//            end = queue1.isEmpty() && queue2.isEmpty();
//            if (!queue1.isEmpty()) {
//                currentConcept1 = (Synset) queue1.remove();
//            }
//            if (!queue2.isEmpty()) {
//                currentConcept2 = (Synset) queue2.remove();
//            }
//            // reach the goal at the same time
//            if (currentConcept1.equals(currentConcept2)) {
//                return currentConcept1;
//            }
//            // the commn parent is already visited by the second path
//            //if the current node of the first path is visited by the second path
//            if (visitedParents2.get(currentConcept1) != null) {
//                // the first common parent
//                return currentConcept1;
//            }
//            // the commn parent is already visited by the first path
//            //if the current node of the second path is visited by the first path
//            if (visitedParents1.get(currentConcept2) != null) {
//                // the first common parent
//                return currentConcept2;
//            }
//
//            // to avoid visiting the same node twice by the first path
//            //if the current node of the first path is not visted 
//            if (visitedParents1.get(currentConcept1) == null) {
//                // to remember the node
//                visitedParents1.put(currentConcept1, true);
//                // expand node
//                currentParents1 = WordNetHandler.getParentConcepts(currentConcept1);
//                // visiting on the parents of the current node in the first path 
//                for (Synset concept : currentParents1) {
//                    //if concept is not visited
//                    if (visitedParents1.get(concept) == null) {
//                        queue1.add(concept);
//                    }
//                }
//            }
//
//            // to avoid visiting the same node twice by the second path
//            //if the current node of the second path is not visted 
//            if (visitedParents2.get(currentConcept2) == null) {
//                // to remember the node
//                visitedParents2.put(currentConcept2, true);
//                // expand node
//                currentParents2 = WordNetHandler.getParentConcepts(currentConcept2);
//                // visiting on the parents of the current node in the second path 
//                for (Synset concept : currentParents2) {
//                    //if concept is not visited
//                    if (visitedParents2.get(concept) == null) {
//                        queue2.add(concept);
//                    }
//                }
//            }
//
//        }
//        return null;
//
//    }
}
