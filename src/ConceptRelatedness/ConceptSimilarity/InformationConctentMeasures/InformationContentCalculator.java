/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness.ConceptSimilarity.InformationConctentMeasures;

import SemanticResource.SemanticResourceHandler;
import ConceptRelatedness.Concept.Concept;



/**
 *
 * @author sobhy
 */
public abstract class InformationContentCalculator {

    protected String formula;
    SemanticResourceHandler semanticResource;
    public InformationContentCalculator(SemanticResourceHandler resource){
        this.semanticResource=resource;
    }

    public void setFormula(String formula) {
        this.formula=formula;
    }
    public String getFormula(){
        return formula;
    }
    public abstract double calculateIC(Concept concept);

//    protected int countChildren(Synset concept) {
//        // to be returned
//        int result = 0;
//        // inicilizing Hashtable
//        // using Hash table reduce the complexity finding whether a node is visited or not,
//        // the complexity decreases from O(n) to O(1) the time to compute hash function
//        Hashtable visitedChildren = new Hashtable();
//
//        // the expansion result
//        Synset[] currentChildren;
//        // initializing the queue to order the visiting operation 
//        // it is not necessary to use queue
//        //any data structure with add and remove functions do the job 
//        // but linked list is the optimal data structure here
//        Queue<Synset> queue = new LinkedList<>();
//        queue.add(concept);
//
//        Synset currentConcept;
//
//        while (!queue.isEmpty()) {
//            currentConcept = queue.remove();
//            // expand current node
//            currentChildren = WordNetHandler.getChildrenConcepts(currentConcept);
//            // visiting the children of the current node
//            for (Synset child : currentChildren) {
//                // if child is not visited
//                if (visitedChildren.get(child) == null) {
//                    visitedChildren.put(child, true);
//                    queue.add(child);
//                    result++;
//                }
//            }
//        }
//
//        return result;
//
//    }
//
//    protected int countSubsumers(Synset concept) {
//
//        int result = 0;
//        // inicilizing Hashtable
//        // using Hash table reduce the complexity finding whether a node is visited or not,
//        // the complexity decreases from O(n) to O(1) the time to compute hash function
//        Hashtable visitedParents = new Hashtable();
//        // the expansion result
//        Synset[] currentParents;
//        // initializing the queue to order the visiting operation 
//        // it is not necessary to use queue
//        //any data structure with add and remove functions do the job 
//        // but linked list is the optimal data structure here
//        Queue<Synset> queue = new LinkedList<>();
//        queue.add(concept);
//
//        Synset currentConcept;
//
//        while (!queue.isEmpty()) {
//            currentConcept = queue.remove();
//            // expand current node
//            currentParents = WordNetHandler.getParentConcepts(currentConcept);
//            // visiting the parents of the current node
//            for (Synset parent : currentParents) {
//                // if parent is not visited
//                if (visitedParents.get(parent) == null) {
//                    visitedParents.put(parent, true);
//                    queue.add(parent);
//                    result++;
//                }
//            }
//        }
//
//        return result;
//
//    }
//
//    protected int countChildrenLeaves(Synset concept) {
//
//        int result = 0;
//        // inicilizing Hashtable
//        // using Hash table reduce the complexity finding whether a node is visited or not,
//        // the complexity decreases from O(n) to O(1) the time to compute hash function
//        Hashtable visitedChildren = new Hashtable();
//        // the expansion result
//        Synset[] currentChildren;
//        // initializing the queue to order the visiting operation 
//        // it is not necessary to use queue
//        //any data structure with add and remove functions do the job 
//        // but linked list is the optimal data structure here
//        Queue<Synset> queue = new LinkedList<>();
//        queue.add(concept);
//
//        Synset currentConcept;
//
//        while (!queue.isEmpty()) {
//            currentConcept = queue.remove();
//            // expand current node
//            currentChildren = WordNetHandler.getChildrenConcepts(currentConcept);
//            if (currentChildren.length == 0) {
//                result++;
//            }
//            // visiting the parents of the current node
//            for (Synset child : currentChildren) {
//                // if child is not visited
//                if (visitedChildren.get(child) == null) {
//                    visitedChildren.put(child, true);
//                    queue.add(child);
//                }
//            }
//        }
//        // to avoid counting the cosidered concept itself
//        result--;
//
//        return result;
//
//    }

    

}
