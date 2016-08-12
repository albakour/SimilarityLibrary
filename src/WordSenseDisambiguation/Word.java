/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WordSenseDisambiguation;

import ConceptRelatedness.SemanticResource.WordNetHandler;
import ConceptRelatedness.Concept.Concept;
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

    public Word(String value) {
        this.value = value;
        generatePossibleSenses();
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
            senses[i]=disSenses.get(i);
        }
        disambiguatedSenses = senses;
        isDisambiguated = true;
    }

    public void generatePossibleSenses() {
        WordNetHandler resource = WordNetHandler.getInstance();
        possibleSenses = resource.getWrappingConcepts(value);

    }

    public Concept[] getPossibleSenses() {
        return possibleSenses;
    }

}
