/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness.Concept;

import ConceptRelatedness.Concept.Concept;
import edu.smu.tspell.wordnet.*;
import edu.smu.tspell.wordnet.Synset;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import Helper.Helper;
import edu.smu.tspell.wordnet.AdjectiveSynset;
import java.util.ArrayList;
import PosTagging.PartOfSpeech;
import SemanticResource.WordNetHandler;

/**
 *
 * @author sobhy
 * @param <Synset>
 */
public class WordNetConcept extends Concept<Synset, WordNetHandler> {

    // the value here has a synset type
    public WordNetConcept(Synset value) {
        super(value);
        this.setSemanticResource(WordNetHandler.getInstance());
    }

    /**
     *
     * @return the object as string
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
     * @return the children nodes which are in the next level in the taxonomy
     */
    @Override
    public Concept[] getDirectAncestors() {
        // the result in synset form
        Synset[] synsets;
        // in case of noun concept
        if (this.partOfSpeech == PartOfSpeech.Type.noun) {
            synsets = ((NounSynset) this.getValue()).getHypernyms();
            int len = synsets.length;
            WordNetConcept[] result = new WordNetConcept[len];
            // converting form array of synset to array of concept
            for (int i = 0; i < len; i++) {
                WordNetConcept concept = new WordNetConcept(synsets[i]);
                // specify the part os speech for the ancestors
                concept.partOfSpeech = PartOfSpeech.Type.noun;
                result[i] = concept;
            }
            return result;
        }
        if (this.partOfSpeech == PartOfSpeech.Type.verb) {
            // in case of verb concept
            synsets = ((VerbSynset) this.getValue()).getHypernyms();
            int len = synsets.length;
            WordNetConcept[] result = new WordNetConcept[len];
            // converting form array of synset to array of concept
            for (int i = 0; i < len; i++) {
                WordNetConcept concept = new WordNetConcept(synsets[i]);
                // specify the part os speech for the ancestors
                concept.partOfSpeech = PartOfSpeech.Type.verb;
                result[i] = concept;
            }
            return result;
        }
        return new Concept[0];

    }

    /**
     *
     * @return the first level of parents (if there are more than on parent)
     */
    @Override
    public Concept[] getDirectSuccessors() {
        // the result in synset form
        Synset[] synsets;
        if (this.partOfSpeech == PartOfSpeech.Type.noun) {
            synsets = ((NounSynset) this.getValue()).getHyponyms();
            int len = synsets.length;
            WordNetConcept[] result = new WordNetConcept[len];
            // converting form array of synset to array of concept
            for (int i = 0; i < len; i++) {
                WordNetConcept concept = new WordNetConcept(synsets[i]);
                // specify the part os speech for the ancestors
                concept.partOfSpeech = PartOfSpeech.Type.noun;
                result[i] = concept;
            }
            return result;
        }
        if (this.partOfSpeech == PartOfSpeech.Type.verb) {
            // get successors on synset form
            synsets = ((VerbSynset) this.getValue()).getEntailments();
            int len = synsets.length;
            WordNetConcept[] result = new WordNetConcept[len];
            // converting form array of synset to array of concept
            for (int i = 0; i < len; i++) {
                WordNetConcept concept = new WordNetConcept(synsets[i]);
                // specify the part os speech for the ancestors
                concept.partOfSpeech = PartOfSpeech.Type.verb;
                result[i] = concept;
            }
            return result;
        }
        return new Concept[0];
    }

    /**
     * the length of the path from the root
     *
     * @return the length of the path from the root
     */
    @Override
    public int getDepth() {
        // the root of the taxonomy

        // we need to cast semanticResource here becaue findPathToSuccessor 
        // is a in just WordNetHandler and not overriden 
        // because it is not neccessary that the ontology which represent the semantic resource
        // to have taxonomy form , so it is not included in semantic resource interface
        List<Concept> list = semanticResource.findPathToSuccessor(semanticResource.getRoot(), this);
        return list.size();
    }

    /**
     *
     * @return the count of all successors of the concept in all levels
     */
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

    /**
     *
     * returns the count of all ancestors of this concept
     *
     * @return
     */
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

    /**
     *
     * @return the count of all the leaves that have this concept as an ancestor
     */
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

    /**
     *
     * @return the words listed in the gloss of the concept
     */
    @Override
    public String[] getDirectWordBag() {
        String[] result;
        Synset synset = this.value;
        String definition = synset.getDefinition();
        // the words from the definition of the concept itself
        result = Helper.getWordsFormSentence(definition);
        return result;
    }

    /**
     *
     * @return get concepts that contain this concept
     */
    @Override
    public Concept[] getContainingConcepts() {
        // just for nouns
        if (this.partOfSpeech == PartOfSpeech.Type.noun) {
            Synset[] synsets;
            synsets = ((NounSynset) value).getPartHolonyms();
            int len = synsets.length;
            // to be returned 
            WordNetConcept[] result = new WordNetConcept[len];
            // converting form array of synset to array of concept
            for (int i = 0; i < len; i++) {
                WordNetConcept concept = new WordNetConcept(synsets[i]);
                concept.setPartOfSpeech(PartOfSpeech.Type.noun);
                result[i] = concept;
            }
            return result;
        }
        return new Concept[0];

    }

    /**
     * get concepts that represent parts of this concept
     *
     * @return concepts that are parts of this concept
     */
    @Override
    public Concept[] getPartsConcepts() {
        // just for noun concepts
        if (this.partOfSpeech == PartOfSpeech.Type.noun) {
            Synset[] synsets = ((NounSynset) value).getPartMeronyms();
            int len = synsets.length;
            // to be returned 
            WordNetConcept[] result = new WordNetConcept[len];
            // converting form array of synset to array of concept
            for (int i = 0; i < len; i++) {
                WordNetConcept concept = new WordNetConcept(synsets[i]);
                concept.setPartOfSpeech(PartOfSpeech.Type.noun);
                result[i] = concept;
            }
            return result;
        }
        return new Concept[0];
    }

    /**
     *
     * @return return what semantic resource provide as similar senses
     */
    @Override
    public Concept[] getSimilarConcepts() {
        // the result on list form
        ArrayList<WordNetConcept> resultList = new ArrayList<>();
        Synset[] synsets;
        WordNetConcept concept;
        if (this.partOfSpeech == PartOfSpeech.Type.adjective) {
            // get similar concepts
            synsets = ((AdjectiveSynset) value).getSimilar();
            for (int i = 0; i < synsets.length; i++) {
                concept = new WordNetConcept(synsets[i]);
                concept.setPartOfSpeech(PartOfSpeech.Type.adjective);
                resultList.add(concept);
            }
            // get related concepts
            synsets = ((AdjectiveSynset) value).getRelated();
            for (int i = 0; i < synsets.length; i++) {
                concept = new WordNetConcept(synsets[i]);
                concept.setPartOfSpeech(PartOfSpeech.Type.adjective);
                resultList.add(concept);
            }
            // adverb case
        } else if (this.partOfSpeech == PartOfSpeech.Type.adverb) {
            // get similar concepts
            // usages are nouns
            synsets = ((AdverbSynset) value).getUsages();
            for (int i = 0; i < synsets.length; i++) {
                concept = new WordNetConcept(synsets[i]);
                concept.setPartOfSpeech(PartOfSpeech.Type.noun);
                resultList.add(concept);
            }
        }
        // there is no similar concepts provided by the 
        // ontology for nouns and verbs
        WordNetConcept[] result = new WordNetConcept[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            result[i] = resultList.get(i);
        }
        return result;
    }

    /**
     *
     * @return the concepts that can be categorized in the same group as this
     * concept
     */
    @Override
    public Concept[] getGroupConcepts() {
        // the result on list form
        ArrayList<WordNetConcept> resultList = new ArrayList<>();
        Synset[] synsets;
        WordNetConcept concept;
        if (this.partOfSpeech == PartOfSpeech.Type.adjective) {
            // get attributes that can be considered as group concepts
            synsets = ((AdjectiveSynset) value).getAttributes();
            for (int i = 0; i < synsets.length; i++) {
                concept = new WordNetConcept(synsets[i]);
                concept.setPartOfSpeech(PartOfSpeech.Type.noun);
                resultList.add(concept);
            }

            //  adverbs case
        } else if (this.partOfSpeech == PartOfSpeech.Type.adverb) {
            // return noun sysets
            // the topics of the adverb can be considered as its group of concepts
            synsets = ((AdverbSynset) value).getTopics();
            for (int i = 0; i < synsets.length; i++) {
                concept = new WordNetConcept(synsets[i]);
                concept.setPartOfSpeech(PartOfSpeech.Type.noun);
                resultList.add(concept);
            }
        }
        //  verbs case
        if (this.partOfSpeech == PartOfSpeech.Type.verb) {
            synsets = ((VerbSynset) value).getVerbGroup();
            for (int i = 0; i < synsets.length; i++) {
                concept = new WordNetConcept(synsets[i]);
                concept.setPartOfSpeech(PartOfSpeech.Type.verb);
                resultList.add(concept);
            }
        }
        // there is no such group for nouns 
        WordNetConcept[] result = new WordNetConcept[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            result[i] = resultList.get(i);
        }
        return result;

    }

    /**
     *
     * @return the definition of the concept
     */
    @Override
    public String getDefinition() {
        return value.getDefinition();

    }

}
