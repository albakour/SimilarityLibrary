/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness.SemanticResource;

import Helper.ConceptNode;
import ConceptRelatedness.Concept.Concept;
import edu.smu.tspell.wordnet.*;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author sobhy
 */
public class WordNetHandler extends SemanticResourceHandler {

    private static WordNetDatabase database;
    private static String dictionaryPath;
    public static String findLcsAlgorithmName;
    // instance for applying singleton pattern
    private static WordNetHandler instance = null;

    public static int maxDepth = 20;
    public static int numberOfConcepts = 74374;// in statistics 82115;
    public static int maxLeaves = 57691;
    public static double averageNumberOfDirectSuccessors = 9.003596475454055;

    private WordNetHandler() {
        connect(dictionaryPath);
        init();

    }

    public static synchronized WordNetHandler getInstance() {
        if (instance == null) {
            instance = new WordNetHandler();
            // do for one time 
            Synset[] synsets = database.getSynsets("entity", SynsetType.NOUN, true);
            root = new WordNetConcept(synsets[0]);
        }
        return instance;
    }

    public void connect(String dictionaryPath) {
        System.setProperty("wordnet.database.dir", dictionaryPath);
        database = WordNetDatabase.getFileInstance();
    }

    public static void setDictionaryPath(String path) {
        dictionaryPath = path;
    }

    public static void setFindLcsAlgorithm(String name) {
        lcsGetter = FindLCSALgorithmFactory.produceObject(name);
    }

    @Override
    public void init() {

        // collecting WordNet statistics
        numberOfConcepts = getNumberOfConcepts();
        //countNounLeaves() before 
        maxLeaves = getMaxLeaves();
        maxDepth = getMaxDepth();
        averageNumberOfDirectSuccessors = getAverageNumberOfDirectSuccessors();

    }

    /**
     *
     * @param wordForm
     * @return
     */
    @Override
    public Concept[] getWrappingConcepts(String wordForm) {
        Synset[] synsets = database.getSynsets(wordForm, SynsetType.NOUN, true);
        int len = synsets.length;
        WordNetConcept[] result = new WordNetConcept[len];
        for (int i = 0; i < len; i++) {
            result[i] = new WordNetConcept(synsets[i]);
        }
        return result;
    }

    /**
     *
     * @param concept
     * @return
     */
//    @Override
//    public int countSuccessors(Concept concept) {
//        // to be returned
//        int result = 0;
//        // inicilizing Hashtable
//        // using Hash table reduce the complexity finding whether a node is visited or not,
//        // the complexity decreases from O(n) to O(1) the time to compute hash function
//        Hashtable visitedChildren = new Hashtable();
//
//        // the expansion result
//        Concept[] currentChildren;
//        // initializing the queue to order the visiting operation 
//        // it is not necessary to use queue
//        //any data structure with add and remove functions do the job 
//        // but linked list is the optimal data structure here
//        Queue<Concept> queue = new LinkedList<>();
//        // cast the concept to a WordNet Concept to deal with synsets
//        queue.add(concept);
//
//        Concept currentConcept;
//
//        while (!queue.isEmpty()) {
//            currentConcept = queue.remove();
//            // expand current node
//            currentChildren = currentConcept.getDirectSuccessors();
//            // visiting the children of the current node
//            for (Concept child : currentChildren) {
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
//    /**
//     *
//     * @param concept
//     * @return
//     */
//    @Override
//    public int countAncestors(Concept concept) {
//
//        int result = 0;
//        // inicilizing Hashtable
//        // using Hash table reduce the complexity finding whether a node is visited or not,
//        // the complexity decreases from O(n) to O(1) the time to compute hash function
//        Hashtable visitedParents = new Hashtable();
//        // the expansion result
//        Concept[] currentParents;
//        // initializing the queue to order the visiting operation 
//        // it is not necessary to use queue
//        //any data structure with add and remove functions do the job 
//        // but linked list is the optimal data structure here
//        Queue<Concept> queue = new LinkedList<>();
//        // cast the concept to a WordNet Concept to deal with synsets
//        queue.add(concept);
//
//        Concept currentConcept;
//
//        while (!queue.isEmpty()) {
//            currentConcept = queue.remove();
//            // expand current node
//            currentParents = currentConcept.getDirectAncestors();
//            // visiting the parents of the current node
//            for (Concept parent : currentParents) {
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
//    @Override
//    public int countSuccessorLeaves(Concept concept) {
//        int result = 0;
//        // inicilizing Hashtable
//        // using Hash table reduce the complexity finding whether a node is visited or not,
//        // the complexity decreases from O(n) to O(1) the time to compute hash function
//        Hashtable visitedChildren = new Hashtable();
//        // the expansion result
//        Concept[] currentChildren;
//        // initializing the queue to order the visiting operation 
//        // it is not necessary to use queue
//        //any data structure with add and remove functions do the job 
//        // but linked list is the optimal data structure here
//        Queue<Concept> queue = new LinkedList<>();
//        // cast the concept to a WordNet Concept to deal with synsets
//        queue.add(concept);
//
//        Concept currentConcept;
//
//        while (!queue.isEmpty()) {
//            currentConcept = queue.remove();
//            // expand current node
//            currentChildren = currentConcept.getDirectSuccessors();
//            if (currentChildren.length == 0) {
//                result++;
//            }
//            // visiting the parents of the current node
//            for (Concept child : currentChildren) {
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
//    }
    /**
     *
     * @return
     */
    @Override
    public int getNumberOfConcepts() {
        // if calculated before 
        if (numberOfConcepts != 0) {
            return numberOfConcepts;
        }
        // the root of the noun taxonomy is entity
        //Concept entity;
        //entity = getWrappingConcepts("entity")[0];
        int result = this.getRoot().countSuccessors() + 1;
        return result;
    }

    @Override
    public int getMaxLeaves() {
        //if calculated before
        if (maxLeaves != 0) {
            return maxLeaves;
        }
//        Concept entity;
//        entity = getWrappingConcepts("entity")[0];

        int result = this.getRoot().countSuccessorLeaves();
        return result;
    }

    @Override
    public double getAverageNumberOfDirectSuccessors() {
        // if calsulated before 
        if (averageNumberOfDirectSuccessors != 0) {
            return averageNumberOfDirectSuccessors;
        }
        // to be returned
        double result = 0;
        // inicilizing Hashtable
        // using Hash table reduce the complexity finding whether a node is visited or not,
        // the complexity decreases from O(n) to O(1) the time to compute hash function
        Hashtable visitedChildren = new Hashtable();

        // the expansion result
        Concept[] currentChildren;
        // initializing the queue to order the visiting operation 
        // it is not necessary to use queue
        //any data structure with add and remove functions do the job 
        // but linked list is the optimal data structure here
        Queue<Concept> queue = new LinkedList<>();
//        Concept entity;
//        entity = getWrappingConcepts("entity")[0];
        queue.add(this.getRoot());

        Concept currentConcept;

        while (!queue.isEmpty()) {
            currentConcept = queue.remove();
            // expand current node
            currentChildren = currentConcept.getDirectSuccessors();
            result += currentChildren.length;
            // visiting the children of the current node
            for (Concept child : currentChildren) {
                // if child is not visited
                // the memorization is implemented using the value attribute
                // beacuse getDirectSuccessors creates new concepts to return so any 
                // repetition will not be caught 
                if (visitedChildren.get(child.getValue()) == null) {
                    visitedChildren.put(child.getValue(), true);
                    queue.add(child);
                    result++;
                }
            }
        }
        // getting the average  (number of child edges per node) 
        result = (double) result / (getNumberOfConcepts() - getMaxLeaves());
        return result;
    }

    /**
     * this method is a member in just WordNetHandler and not overriden because
     * it is not necessary that the ontology which represent the semantic
     * resource to have taxonomy form , so it is not included in semantic
     * resource interface
     *
     * @param parent
     * @param child
     * @return
     */
    public ArrayList<Concept> findPathToSuccessor(Concept parent, Concept child) {

        // queue fo BFS algorithm
        Queue<ConceptNode> queue = new LinkedList<>();

        // the result of the expansion
        Concept[] currerntParents;

        // to be returned
        ArrayList<Concept> result = new ArrayList<>();

        // the core node
        ConceptNode currentNode = new ConceptNode(child, null, null);
        queue.add(currentNode);

        while (!queue.isEmpty()) {
            currentNode = queue.remove();
            if (currentNode.getConcept().equals(parent)) {
                break;
            }
            currerntParents = currentNode.getConcept().getDirectAncestors();
            for (Concept concept : currerntParents) {
                ConceptNode parentNode = new ConceptNode(concept, currentNode, null);
                queue.add(parentNode);
            }
        }
        while (currentNode != null) {
            result.add(currentNode.getConcept());
            currentNode = currentNode.getChild();
        }
        return result;
    }

    /**
     *
     * @param concept
     * @return //
     */
//    @Override
//    public int getDepth(Concept concept) {
//        Concept entity;
//        entity = getWrappingConcepts("entity")[0];
//        List<Concept> list = findPathToSuccessor(entity, concept);
//        return list.size();
//    }
    @Override
    public int getMaxDepth() {

        // if calculated before
        if (maxDepth != 0) {
            return maxDepth;
        }
        // queue for BFS algorithm
        Queue<ConceptNode> queue = new LinkedList<>();

        // the result of the expansion
        Concept[] currerntChildren;

        // to be returned
        ArrayList<Concept> result = new ArrayList<>();

        // the core node
//        Concept entity;
//        entity = getWrappingConcepts("entity")[0];
        ConceptNode currentNode = new ConceptNode(this.getRoot(), null, null);
        currentNode.setDepth(1);
        queue.add(currentNode);
        int max = 1;
        ConceptNode maxNode = currentNode;

        while (!queue.isEmpty()) {
            currentNode = queue.remove();
            currerntChildren = currentNode.getConcept().getDirectSuccessors();
            for (Concept concept : currerntChildren) {
                // child is null and the parent is te current node
                ConceptNode childNode = new ConceptNode(concept, null, currentNode);
                childNode.setDepth(currentNode.getDepth() + 1);
                //childNode.depth = currentNode.depth + 1;
                if (max < childNode.getDepth()) {
                    max = childNode.getDepth();
                    maxNode = childNode;
                }
                queue.add(childNode);
            }
        }
        currentNode = maxNode;
        while (currentNode != null) {
            result.add(currentNode.getConcept());
            currentNode = currentNode.getParent();
        }

        return result.size();

    }

    /**
     *
     * @param concept1
     * @param concept2
     * @return
     */
    //@Override
    /*public Concept getLcs(Concept concept1, Concept concept2) {

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

     }*/
    /**
     *
     * @param concept1
     * @param concept2
     * @return
     */
    public Concept getLcsOld(Concept concept1, Concept concept2) {

        // inicilizing Hashtables 
        // using Hash tables reduce the complexity finding whether a node is visited or not
        // the complexity falls from O(n) to O(1) the time to compute hash function
        Hashtable visitedParents1, visitedParents2;
        visitedParents1 = new Hashtable();
        visitedParents2 = new Hashtable();

        // initializing queues
        // it is a double Breadth First Search (BFS) algorithm 
        Queue<Concept> queue1 = new LinkedList<>();
        Queue<Concept> queue2 = new LinkedList<>();

        // casting to get to the Synsets
        queue1.add(concept1);
        queue2.add(concept2);

        // the current tree nodes to be expanded
        Concept currentConcept1, currentConcept2;
        currentConcept1 = concept1;
        currentConcept2 = concept2;
        // 
        Concept[] currentParents1;
        Concept[] currentParents2;
        // lcs algorithm 
        // double BFS without a prespecified target
        boolean end = false;
        while (!end) {

            // until there are no nodes to be expanded
            end = queue1.isEmpty() && queue2.isEmpty();
            if (!queue1.isEmpty()) {
                currentConcept1 = queue1.remove();
            }
            if (!queue2.isEmpty()) {
                currentConcept2 = queue2.remove();
            }
            // reach the goal at the same time
            if (currentConcept1.equals(currentConcept2)) {
                return currentConcept1;
            }
            // the commn parent is already visited by the second path
            //if the current node of the first path is visited by the second path
            if (visitedParents2.get(currentConcept1.getValue()) != null) {
                // the first common parent
                return currentConcept1;
            }
            // the commn parent is already visited by the first path
            //if the current node of the second path is visited by the first path
            if (visitedParents1.get(currentConcept2.getValue()) != null) {
                // the first common parent
                return currentConcept2;
            }

            // to avoid visiting the same node twice by the first path
            //if the current node of the first path is not visted 
            if (visitedParents1.get(currentConcept1.getValue()) == null) {
                // to remember the node
                visitedParents1.put(currentConcept1.getValue(), true);
                // expand node
                currentParents1 = currentConcept1.getDirectAncestors();
                // visiting on the parents of the current node in the first path 
                for (Concept concept : currentParents1) {
                    //if concept is not visited
                    if (visitedParents1.get(concept.getValue()) == null) {
                        queue1.add(concept);
                    }
                }
            }

            // to avoid visiting the same node twice by the second path
            //if the current node of the second path is not visted 
            if (visitedParents2.get(currentConcept2.getValue()) == null) {
                // to remember the node
                visitedParents2.put(currentConcept2.getValue(), true);
                // expand node
                currentParents2 = currentConcept2.getDirectAncestors();
                // visiting on the parents of the current node in the second path 
                for (Concept concept : currentParents2) {
                    //if concept is not visited
                    if (visitedParents2.get(concept.getValue()) == null) {
                        queue2.add(concept);
                    }
                }
            }

        }
        return null;

    }

    /**
     *
     * @param concept1
     * @param concept2
     * @param lcs
     * @return
     */
    @Override
    public ArrayList<Concept> getShortestPath(Concept concept1, Concept concept2, Concept lcs) {

        // the LCS is a node in the shortest path
        // initializing lists 
        // to be returned 
        ArrayList<Concept> result = new ArrayList<>();

        // the path form LCS to the first concept
        ArrayList<Concept> list1;
        list1 = findPathToSuccessor(lcs, concept1);

        // the path form LCS to the escond concept
        ArrayList<Concept> list2;
        list2 = findPathToSuccessor(lcs, concept2);

        int len1;
        len1 = list1.size();
        // use for not foreach because the members are an inverse order
        for (int i = len1 - 1; i >= 0; i--) {
            result.add(list1.remove(i));
        }

        // to avoid repeating the LCS in the result
        list2.remove(0);
        for (Concept concept : list2) {
            result.add(concept);
        }
        return result;
    }

//    private static double calculateEdegeAverageNouns() {
//        // to be returned
//        double result = 0;
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
//        NounSynset entity;
//        entity = (NounSynset) WordNetHandler.getWrappingConcepts("entity")[0];
//        queue.add(entity);
//
//        Synset currentConcept;
//
//        while (!queue.isEmpty()) {
//            currentConcept = queue.remove();
//            // expand current node
//            currentChildren = ((NounSynset) currentConcept).getHyponyms();
//            result += currentChildren.length;
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
//        // getting the average  (number of child edges per node) 
//        result = (double) result / (numberOfNounConcepts - numberOfNounLeaves);
//        return result;
//
//    }
//    public static int countChildEdges(Synset concept) {
//
//        int result = getChildrenConcepts(concept).length;
//        return result;
//    }
//    public String getConceptWordForms(Synset concept) {
//        String[] wordForms = concept.getWordForms();
//        String result = "{";
//        boolean first = true;
//        for (String word : wordForms) {
//            if (first) {
//                result += word;
//                first = false;
//            } else {
//                result += "," + word;
//            }
//        }
//        result += "}";
//        return result;
//
//    }
//    public static double calculateParentEdegeAverageNouns() {
//        // to be returned
//        double result = 0;
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
//        NounSynset entity;
//        entity = (NounSynset) WordNetHandler.getWrappingConcepts("entity")[0];
//        queue.add(entity);
//
//        Synset currentConcept;
//
//        while (!queue.isEmpty()) {
//            currentConcept = queue.remove();
//            // expand current node
//            currentChildren = ((NounSynset) currentConcept).getHyponyms();
//            //result += currentChildren.length;
//            // visiting the children of the current node
//            for (Synset child : currentChildren) {
//                // if child is not visited
//                if (visitedChildren.get(child) == null) {
//                    visitedChildren.put(child, true);
//                    queue.add(child);
//                    result += ((NounSynset) child).getHyponyms().length;
//                    //result++;
//                }
//            }
//        }
//        // getting the average  (number of child edges per node) 
//        result = (double) result / numberOfNounConcepts;
//        return result;
//
//    }
}
