/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness.GlossMesures;

import SemanticResource.SemanticResourceHandler;
import ConceptRelatedness.Concept.*;
import ConceptRelatedness.ConceptsRelatednessAlgorithm;
import java.util.ArrayList;
import Helper.Helper;

/**
 *
 * @author sobhy
 * @param <T>
 */
public abstract class GlossAlgorithm<T extends RelatedConceptsGenerator> extends ConceptsRelatednessAlgorithm {

    protected ArrayList<String> wordsBag1;
    protected ArrayList<String> wordsBag2;
    protected T relatedConceptsGenerator;


    public GlossAlgorithm(Concept concept1, Concept concept2, SemanticResourceHandler resource) {
        super(concept1, concept2, resource);
    }

    /**
     *
     */
    @Override
    public void execute() {
        wordsBag1 = firstConcept.getWordBag(this);
        wordsBag2 = secondConcept.getWordBag(this);
        relatedness = calculateRelatedness();
        normalizedRelatedness=Math.tanh(relatedness/25);

    }

    /**
     *
     * @return
     */
    @Override
    protected double calculateRelatedness() {
        double result = 0;
        for (String word : wordsBag1) {
            if (!Helper.ignore(word)) {
                result += countRepetition(word, wordsBag2);
            }
        }
        return result;
    }

    private int countRepetition(String word, ArrayList<String> wordsBag) {
        int result = 0;
        int len = wordsBag.size();
        for (int i = 0; i < len; i++) {
            if (wordsBag.get(i).equals(word)) {
                result++;
            }
        }
        return result;

    }
    public void setRelatedConceptsGenerator(T generator){
        this.relatedConceptsGenerator=generator;
    }
    public T getRelatedConceptsGenerator(){
        return this.relatedConceptsGenerator;
    }



}
