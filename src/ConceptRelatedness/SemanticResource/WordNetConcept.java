/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness.SemanticResource;

import ConceptRelatedness.Concept.Concept;
import edu.smu.tspell.wordnet.NounSynset;
import edu.smu.tspell.wordnet.Synset;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import Helper.Helper;

/**
 *
 * @author sobhy
 * @param <Synset>
 */
public class WordNetConcept extends Concept<Synset,WordNetHandler> {

    // the value here has a synset type
    public WordNetConcept( Synset value) {
        super(value);
        this.setSemanticResource(WordNetHandler.getInstance());
    }

    /**
     *
     * @return
     */
    @Override
    public String representAsString() {
        String[] wordForms = this.getValue().getWordForms();
        String result = "{";
        boolean first = true;
        for (String word : wordForms) {
            if (first) {
                result += word;
                first = false;
            } else {
                result += "," + word;
            }
        }
        result += "}";
        return result;
    }

    /**
     *
     * @return
     */
    @Override
    public Concept[] getDirectAncestors() {
        // the result in synset form
        Synset[] synsets = ((NounSynset)this.getValue()).getHypernyms();
        int len = synsets.length;
        // to be returned 
        WordNetConcept[] result = new WordNetConcept[len];
        // converting form array of synset to array of concept
        for (int i = 0; i < len; i++) {
            result[i] = new WordNetConcept(synsets[i]);
        }
        return result;

    }

    /**
     *
     * @return
     */
    @Override
    public Concept[] getDirectSuccessors() {
        // the result in synset form
        Synset[] synsets = ((NounSynset) this.getValue()).getHyponyms();
        int len = synsets.length;
        // to be returned 
        WordNetConcept[] result = new WordNetConcept[len];
        // converting form array of synset to array of concept
        for (int i = 0; i < len; i++) {
            result[i] = new WordNetConcept(synsets[i]);
        }
        return result;
    }

    @Override
    public int getDepth() {
        // the root of the taxonomy
        Concept entity;
        entity = this.getSemanticResource().getWrappingConcepts("entity")[0];
        
        // we need to cast semanticResource here becaue findPathToSuccessor 
        // is a in just WordNetHandler and not overriden 
        // because it is not neccessary that the ontology which represent the semantic resource
        // to have taxonomy form , so it is not included in semantic resource interface
        List<Concept> list = semanticResource.findPathToSuccessor(entity, this);
        return list.size();
    }

    @Override
    public int countSuccessors() {
        // to be returned
        int result = 0;
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
        // cast the concept to a WordNet Concept to deal with synsets
        queue.add(this);

        Concept currentConcept;

        while (!queue.isEmpty()) {
            currentConcept = queue.remove();
            // expand current node
            currentChildren = currentConcept.getDirectSuccessors();
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

        return result;

    }

    @Override
    public int countAncestors() {

        int result = 0;
        // inicilizing Hashtable
        // using Hash table reduce the complexity finding whether a node is visited or not,
        // the complexity decreases from O(n) to O(1) the time to compute hash function
        Hashtable visitedParents = new Hashtable();
        // the expansion result
        Concept[] currentParents;
        // initializing the queue to order the visiting operation 
        // it is not necessary to use queue
        //any data structure with add and remove functions do the job 
        // but linked list is the optimal data structure here
        Queue<Concept> queue = new LinkedList<>();
        // cast the concept to a WordNet Concept to deal with synsets
        queue.add(this);

        Concept currentConcept;

        while (!queue.isEmpty()) {
            currentConcept = queue.remove();
            // expand current node
            currentParents = currentConcept.getDirectAncestors();
            // visiting the parents of the current node
            for (Concept parent : currentParents) {
                // if parent is not visited
                // the memorization is implemented using the value attribute
                // beacuse getDirectSuccessors creates new concepts to return so any 
                // repetition will not be caught 
                if (visitedParents.get(parent.getValue()) == null) {
                    visitedParents.put(parent.getValue(), true);
                    queue.add(parent);
                    result++;
                }
            }
        }

        return result;

    }

    @Override
    public int countSuccessorLeaves() {
        int result = 0;
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
        // cast the concept to a WordNet Concept to deal with synsets
        queue.add(this);

        Concept currentConcept;

        while (!queue.isEmpty()) {
            currentConcept = queue.remove();
            // expand current node
            currentChildren = currentConcept.getDirectSuccessors();
            if (currentChildren.length == 0) {
                result++;
            }
            // visiting the parents of the current node
            for (Concept child : currentChildren) {
                // if child is not visited
                // the memorization is implemented using the value attribute
                // beacuse getDirectSuccessors creates new concepts to return so any 
                // repetition will not be caught 
                if (visitedChildren.get(child.getValue()) == null) {
                    visitedChildren.put(child.getValue(), true);
                    queue.add(child);
                }
            }
        }
        // to avoid counting the cosidered concept itself
        result--;

        return result;
    }

    @Override
    public boolean equals(Concept concept) {
        // if the values are the same
        if (this.value.equals(concept.getValue())) {
            return true;
        }
        return false;

    }

    @Override
    public String[] getDirectWordBag() {
        String[] result;
        Synset synset =  this.value;
        String definition = synset.getDefinition();
        result = Helper.getWordsFormSentence(definition);
        return result;
    }


    public Concept[] getContainingConcepts() {
        Synset[] synsets = ((NounSynset) value).getPartHolonyms();
        int len = synsets.length;
        // to be returned 
        WordNetConcept[] result = new WordNetConcept[len];
        // converting form array of synset to array of concept
        for (int i = 0; i < len; i++) {
            result[i] = new WordNetConcept(synsets[i]);
        }
        return result;
    }

    @Override
    public Concept[] getPartsConcepts() {
        Synset[] synsets = ((NounSynset) value).getPartMeronyms();
        int len = synsets.length;
        // to be returned 
        WordNetConcept[] result = new WordNetConcept[len];
        // converting form array of synset to array of concept
        for (int i = 0; i < len; i++) {
            result[i] = new WordNetConcept(synsets[i]);
        }
        return result;
    }

    /**
     *
     * @return
     */
    @Override
    public String getDefinition(){
        return value.getDefinition();
        
    }
    

}
