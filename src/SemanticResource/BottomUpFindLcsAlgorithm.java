/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SemanticResource;

import ConceptRelatedness.Concept.Concept;
import Helper.ConceptNode;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author sobhy
 */
public class BottomUpFindLcsAlgorithm implements  FindLCSAlgorithm {

    /**
     *
     * @param concept1
     * @param concept2
     * @return the lowest common subsumer of the two concepts
     */
    @Override
    public Concept getLcs(Concept concept1,Concept concept2){
        
        ConceptNode result1, result2, tempResult1, tempResult2;
        //initializing the temp results to avoid not created compiler error 
        // these values is never used but the compiler thinks that the variables
        // will be called before initializing , which is guarenteed to not 
        //happen by the algorithm
        result1 = new ConceptNode(null, null, null);
        // the following value is used if no solution found , concepts belongs to two different taxonomy
        // which means differnt part of speach
        result2 = new ConceptNode(null, null, null);
        tempResult1 = new ConceptNode(null, null, null);
        tempResult2 = new ConceptNode(null, null, null);
        // inicilizing Hashtables 
        // using Hash tables reduce the complexity finding whether a node is visited or not
        // the complexity falls from O(n) to O(1) the time to compute hash function
        Hashtable visitedParents1, visitedParents2;
        visitedParents1 = new Hashtable();
        visitedParents2 = new Hashtable();

        // initializing queues
        // it is a double Breadth First Search (BFS) algorithm 
        // ConceptNode is used here instead of Concept because we need to 
        // store the level of the concept in (BFS)tree which is 
        // represented as level1 and level2 for the level of the node in BFS tree 
        // rooted on first concept ,and the symetric tree of the second one respectively
        Queue<ConceptNode> queue1 = new LinkedList<>();
        Queue<ConceptNode> queue2 = new LinkedList<>();
        // initializing the roots of both trees
        ConceptNode node1 = new ConceptNode(concept1, null, null);
        ConceptNode node2 = new ConceptNode(concept2, null, null);
        node1.setLevel1(0); // depth of root 1 is 0
        node2.setLevel2(0); // depth of root 2 is 0
        queue1.add(node1);
        queue2.add(node2);

        // the current tree nodes to be expanded
        ConceptNode currentConceptNode1, currentConceptNode2;
        currentConceptNode1 = node1;
        currentConceptNode2 = node2;
        // the current results of expanding nodes
        Concept[] currentParents1;
        Concept[] currentParents2;
        
        //if there is a solution in the current state of the tree 
        //there should at least one level in the first tree or in the second one 
        //that the taregt node is cerainly belongs to 
        // initialized -1 because it is impossible level
        //int guarenteedLevel1 = -1, guarenteedLevel2 = -1;
        
        // flags to tag that a solution found in the first tree or in the second
        boolean solutionFoundInPath1 = false;
        boolean solutionFoundInPath2 = false;
        // falgs to mak the end of search in each tree
        // set to ture when the current level is deeper than the level that guarentee the solution
        boolean endPath1 = false;
        boolean endPath2 = false;
        //the lengths of the shortest path found till now in each tree
        // it is set after discovering the first solution in the tree
        int shortestPathFoundLength1 = Integer.MAX_VALUE;
        int shortestPathFoundLength2 = Integer.MAX_VALUE;
        // the worst case length of path, where we should test more levels in a tree 
        //make sure that there are no shorter pathes this can be 

        // int worstCaseLookAhead1=Integer.MAX_VALUE;
        // int worstCaseLookAhead2=Integer.MAX_VALUE;
        // lcs algorithm 
        // double BFS without a prespecified target
        boolean end = false;
        while (!end) {

            // until there are no nodes to be expanded
            end = (queue1.isEmpty() && queue2.isEmpty()) || (endPath1 && endPath2);
            // if there are more nodes to expand
            if (!queue1.isEmpty()) {
                currentConceptNode1 = queue1.remove();
                // stop searching after passing the level where the first solution in the first tree is found 

//                if (guarenteedLevel1 >= 0) {// if guarenteedLevel1 is set 
//                    if (currentConceptNode1.level1 > guarenteedLevel1) {
//                        endPath1 = true;
//                    }
//                }
            }
            // if there are more nodes to expand
            if (!queue2.isEmpty()) {
                currentConceptNode2 = queue2.remove();
                // stop searching after passing the level where the first solution in the first tree is found 
//                
//                if (guarenteedLevel2 >= 0) {// if guarenteedLevel1 is set 
//                    if (currentConceptNode2.level2 > guarenteedLevel2) {
//                        endPath2 = true;
//                    }
//                }

            }
            // reach the target node at the same time
            // if the current nodes represent the same concepts
            if (currentConceptNode1.equals(currentConceptNode2)) {
                solutionFoundInPath1 = true;
                //store the soltion to cmpare it with other solutions in the current level
                // of the first tree that might be found
                tempResult1 = currentConceptNode1;
                tempResult1.setLevel2(currentConceptNode2.getLevel2());
                solutionFoundInPath2 = true;
                //store the soltion to cmpare it with other solutions in the current level
                // of the second tree that might be found
                tempResult2 = currentConceptNode2;
                tempResult2.setLevel1(currentConceptNode1.getLevel1());
            }
            // the common parent is already visited by the second path
            //if the current node of the first tree is visited by the second path 
            if (visitedParents2.get(currentConceptNode1.getConcept().getValue()) != null) {

                solutionFoundInPath1 = true;
                // the first common parent found
                // store it for later comparisons
                tempResult1 = currentConceptNode1;
                // level1 is already set because currentConceptNode1 belongs to the first tree
                // when the algorithm discover that this node is common (in value) between the two trees
                // level2 is not set yet so it should be set to the depth of the equivalent node form the second tree
                int level2=((ConceptNode) (visitedParents2.get(currentConceptNode1.getConcept().getValue()))).getLevel2();
                tempResult1.setLevel2(level2); 
//                if (guarenteedLevel1 < 0) {
//                    guarenteedLevel1 = currentConceptNode1.level1;
//                }
            }
            // the commn parent is already visited by the first path
            //if the current node of the second path is visited by the first path
            if (visitedParents1.get(currentConceptNode2.getConcept().getValue()) != null) {
                // the first common parent
                // store it for later comparisons
                solutionFoundInPath2 = true;
                tempResult2 = currentConceptNode2;
                // level2 is already set because currentConceptNode2 belongs to the second tree
                // when the algorithm discover that this node is common (in value) between the two trees
                // level1 is not set yet so it should be set to the depth of the equivalent node form the first tree
                int level1=((ConceptNode) (visitedParents1.get(currentConceptNode2.getConcept().getValue()))).getLevel1();
                tempResult2.setLevel1(level1); 
//                if (guarenteedLevel2 < 0) {
//                    guarenteedLevel2 = currentConceptNode2.level2;
//                }
            }
            // if there is no benefit from deeping more in the first tree
            if (!endPath1) {
                // if a common parent is found in the first tree
                if (solutionFoundInPath1) {
                    // the length of the path between the roots is the summation of the depthes of the 
                    //common parent found 
                    int pathLen = tempResult1.getLevel1() + tempResult1.getLevel2();
                    // keep the shortest path
                    if (shortestPathFoundLength1 > pathLen) {
                        shortestPathFoundLength1 = pathLen;
                        result1 = tempResult1;

                    }
                } else { // if no common parents found in the first tree

                    // to avoid visiting the same node twice by the first path
                    //if the current node of the first path is not visted 
                    if (visitedParents1.get(currentConceptNode1.getConcept().getValue()) == null) {
                        // to remember the node
                        // store the node itself as a value in hash table to use it later when 
                        // common parent is found
                        visitedParents1.put(currentConceptNode1.getConcept().getValue(), currentConceptNode1);
                        // expand node
                        currentParents1 = currentConceptNode1.getConcept().getDirectAncestors();
                        // visiting on the parents of the current node in the first path 
                        for (Concept concept : currentParents1) {
                            //if concept is not visited
                            if (visitedParents1.get(concept.getValue()) == null) {
                                //initialize parent node
                                ConceptNode node = new ConceptNode(concept, null, null);
                                // increment the level
                                node.setLevel1(currentConceptNode1.getLevel1()+1);
                               // node.level1 = currentConceptNode1.level1 + 1;
                                queue1.add(node);
                            }
                        }
                    }
                }
            }
            // if there is no benefit from deeping more in the second tree
            if (!endPath2) {
                // if a common parent is found in the second tree
                if (solutionFoundInPath2) {
                    // the length of the path between the roots is the summation of the depthes of the 
                    //common parent found 
                    int pathLen = tempResult2.getLevel1() + tempResult2.getLevel2();
                    // keep the shortest path
                    if (shortestPathFoundLength2 > pathLen) {
                        shortestPathFoundLength2 = pathLen;
                        result2 = tempResult2;

                    }
                } else {// if no common parents found in the second tree

                    // to avoid visiting the same node twice by the second path
                    //if the current node of the second path is not visted 
                    if (visitedParents2.get(currentConceptNode2.getConcept().getValue()) == null) {
                        // to remember the node
                        visitedParents2.put(currentConceptNode2.getConcept().getValue(), currentConceptNode2);
                        // expand node
                        currentParents2 = currentConceptNode2.getConcept().getDirectAncestors();
                        // visiting on the parents of the current node in the second path 
                        for (Concept concept : currentParents2) {
                            //if concept is not visited
                            if (visitedParents2.get(concept.getValue()) == null) {
                                //initialize parent node
                                ConceptNode node = new ConceptNode(concept, null, null);
                                // increment the level
                                node.setLevel2(currentConceptNode2.getLevel2()+1);
                                //node.level2 = currentConceptNode2.level2 + 1;
                                queue2.add(node);
                            }
                        }
                    }

                }
            }
        }
        //at the end of the loop at least one of the results result1 and result2 is set
        // take the solution which is shorter
        if (shortestPathFoundLength1 < shortestPathFoundLength2) {
            return result1.getConcept();
        }
        return result2.getConcept();
        //if niether of the results is set the algorithm returns null which is the value of result2.getConcept()

    }
    
}
