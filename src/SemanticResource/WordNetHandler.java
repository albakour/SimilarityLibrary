/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SemanticResource;

import ConceptRelatedness.Concept.WordNetConcept;
import Helper.ConceptNode;
import ConceptRelatedness.Concept.Concept;
import WordSenseDisambiguation.Word;
import edu.smu.tspell.wordnet.*;
import PosTagging.PartOfSpeech;

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

    /**
     * for singleton pattern
     *
     * @return
     */
    public static synchronized WordNetHandler getInstance() {
        if (instance == null) {
            instance = new WordNetHandler();
            // do for one time 
            Synset[] synsets = database.getSynsets("entity", SynsetType.NOUN, true);
            root = new WordNetConcept(synsets[0]);
        }
        return instance;
    }

    /**
     * connect to the dictionary
     *
     * @param dictionaryPath
     */
    public void connect(String dictionaryPath) {
        System.setProperty("wordnet.database.dir", dictionaryPath);
        database = WordNetDatabase.getFileInstance();
    }

    public static void setDictionaryPath(String path) {
        dictionaryPath = path;
    }

    /**
     *
     * @param name the name of the algorithm to be used
     */
    public static void setFindLcsAlgorithm(String name) {
        lcsGetter = FindLCSALgorithmFactory.produceObject(name);
    }

    /**
     * calculate statistics
     */
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
     * @param word
     * @return the concepts that can be represented by the word
     */
    @Override
    public Concept[] getWrappingConcepts(Word word) {
        Synset[] synsets;
        ArrayList<WordNetConcept> resultList = new ArrayList<>();
        if (word.getPartOfSpeech() == PartOfSpeech.Type.noun) {
            synsets = database.getSynsets(word.value, SynsetType.NOUN, true);
            for (int i = 0; i < synsets.length; i++) {
                WordNetConcept concept = new WordNetConcept(synsets[i]);
                concept.setPartOfSpeech(PartOfSpeech.Type.noun);
                resultList.add(concept);
            }
        } else if (word.getPartOfSpeech() == PartOfSpeech.Type.verb) {
            synsets = database.getSynsets(word.value, SynsetType.VERB, true);
            for (int i = 0; i < synsets.length; i++) {
                WordNetConcept concept = new WordNetConcept(synsets[i]);
                concept.setPartOfSpeech(PartOfSpeech.Type.verb);
                resultList.add(concept);
            }
        } else if (word.getPartOfSpeech() == PartOfSpeech.Type.adjective) {
            synsets = database.getSynsets(word.value, SynsetType.ADJECTIVE, true);
            for (int i = 0; i < synsets.length; i++) {
                WordNetConcept concept = new WordNetConcept(synsets[i]);
                concept.setPartOfSpeech(PartOfSpeech.Type.adjective);
                resultList.add(concept);
            }
        } else if (word.getPartOfSpeech() == PartOfSpeech.Type.adverb) {
            synsets = database.getSynsets(word.value, SynsetType.ADVERB, true);
            for (int i = 0; i < synsets.length; i++) {
                WordNetConcept concept = new WordNetConcept(synsets[i]);
                concept.setPartOfSpeech(PartOfSpeech.Type.adverb);
                resultList.add(new WordNetConcept(synsets[i]));
            }
        } else if (word.getPartOfSpeech() == PartOfSpeech.Type.other) {
        }
        WordNetConcept[] result = new WordNetConcept[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            result[i] = resultList.get(i);
        }
        return result;
    }

    /**
     *
     * @return number of concepts in taxonomy
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

    /**
     * returns
     *
     * @return the number of leaves in the taxonomy
     */
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

    /**
     * returns
     *
     * @return the average number of direct children in the taxonomy
     */
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

//    /**
//     *
//     * @param concept1
//     * @param concept2
//     * @return
//     */
//    public Concept getLcsOld(Concept concept1, Concept concept2) {
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
//        Queue<Concept> queue1 = new LinkedList<>();
//        Queue<Concept> queue2 = new LinkedList<>();
//
//        // casting to get to the Synsets
//        queue1.add(concept1);
//        queue2.add(concept2);
//
//        // the current tree nodes to be expanded
//        Concept currentConcept1, currentConcept2;
//        currentConcept1 = concept1;
//        currentConcept2 = concept2;
//        // 
//        Concept[] currentParents1;
//        Concept[] currentParents2;
//        // lcs algorithm 
//        // double BFS without a prespecified target
//        boolean end = false;
//        while (!end) {
//
//            // until there are no nodes to be expanded
//            end = queue1.isEmpty() && queue2.isEmpty();
//            if (!queue1.isEmpty()) {
//                currentConcept1 = queue1.remove();
//            }
//            if (!queue2.isEmpty()) {
//                currentConcept2 = queue2.remove();
//            }
//            // reach the goal at the same time
//            if (currentConcept1.equals(currentConcept2)) {
//                return currentConcept1;
//            }
//            // the commn parent is already visited by the second path
//            //if the current node of the first path is visited by the second path
//            if (visitedParents2.get(currentConcept1.getValue()) != null) {
//                // the first common parent
//                return currentConcept1;
//            }
//            // the commn parent is already visited by the first path
//            //if the current node of the second path is visited by the first path
//            if (visitedParents1.get(currentConcept2.getValue()) != null) {
//                // the first common parent
//                return currentConcept2;
//            }
//
//            // to avoid visiting the same node twice by the first path
//            //if the current node of the first path is not visted 
//            if (visitedParents1.get(currentConcept1.getValue()) == null) {
//                // to remember the node
//                visitedParents1.put(currentConcept1.getValue(), true);
//                // expand node
//                currentParents1 = currentConcept1.getDirectAncestors();
//                // visiting on the parents of the current node in the first path 
//                for (Concept concept : currentParents1) {
//                    //if concept is not visited
//                    if (visitedParents1.get(concept.getValue()) == null) {
//                        queue1.add(concept);
//                    }
//                }
//            }
//
//            // to avoid visiting the same node twice by the second path
//            //if the current node of the second path is not visted 
//            if (visitedParents2.get(currentConcept2.getValue()) == null) {
//                // to remember the node
//                visitedParents2.put(currentConcept2.getValue(), true);
//                // expand node
//                currentParents2 = currentConcept2.getDirectAncestors();
//                // visiting on the parents of the current node in the second path 
//                for (Concept concept : currentParents2) {
//                    //if concept is not visited
//                    if (visitedParents2.get(concept.getValue()) == null) {
//                        queue2.add(concept);
//                    }
//                }
//            }
//
//        }
//        return null;
//
//    }
    /**
     *
     * @param concept1
     * @param concept2
     * @param lcs
     * @return the shortest path between the two concepts
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

}
