/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WordSenseDisambiguation;

import ConceptRelatedness.SemanticResource.WordNetHandler;
import ConceptRelatedness.Concept.Concept;
import ConceptRelatedness.SemanticResource.SemanticResourceHandler;
import java.util.ArrayList;

/**
 *
 * @author sobhy
 */
public class Word {

    public String value;
    private Concept[] possibleSenses;
    // they are multiple because maybe more than one sense have the same score after 
    // applying Disambiuation Algorithm
    private Concept[] disambiguatedSenses;
    public boolean isDisambiguated;
    public boolean isNoun;
    private boolean areSensesGenerated;
    SemanticResourceHandler semanticResource;

    public Word(String value, SemanticResourceHandler resource) {
        this.value = value;
        this.semanticResource = resource;
        
    }

    public Concept[] getDisamiguatedSenses() {
        if (isDisambiguated) {
            return disambiguatedSenses;
        }
        return null;
    }

    public void setDisambiuatedSenses(ArrayList<Concept> disSenses) {
        int len = disSenses.size();
        Concept[] senses = new Concept[len];
        for (int i = 0; i < len; i++) {
            senses[i] = disSenses.get(i);
        }
        disambiguatedSenses = senses;
        isDisambiguated = true;
    }

    public void generatePossibleSenses() {
        possibleSenses = semanticResource.getWrappingConcepts(this);
        if(this.isNoun){
            for(int i=0;i<possibleSenses.length;i++){
                possibleSenses[i].inTaxonomy=true;
            }
        }

    }

    public Concept[] getPossibleSenses() {
        if (!areSensesGenerated) {
            generatePossibleSenses();
            areSensesGenerated=true;
        }
        return possibleSenses;
    }

    public boolean equals(Word word) {
        if (word.value.equals(this.value)) {
            return true;
        }
        return false;
    }

}
