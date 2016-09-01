/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WordSenseDisambiguation;

import PosTagging.PartOfSpeech;
import ConceptRelatedness.Concept.Concept;
import ConceptRelatedness.ConceptSimilarity.InformationConctentMeasures.*;
import SemanticResource.SemanticResourceHandler;
import java.util.ArrayList;
import PosTagging.PartOfSpeech.Type;

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
    private boolean areSensesGenerated;
    SemanticResourceHandler semanticResource;
    private InformationContentCalculator icCalculator;
    private boolean isIcCalculated;
    private double informationContent;

    PartOfSpeech.Type partOfSpeech;

    public Word(String value, SemanticResourceHandler resource) {
        this.value = value;
        this.semanticResource = resource;

    }

    /**
     *
     * @return the result of disambiguation process
     */
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
        if (possibleSenses.length == 0 || disambiguatedSenses.length == 0) {
            this.isDisambiguated = false;
        }
    }
/**
 * generates the senses that can be represented by the word 
 */
    public void generatePossibleSenses() {
        possibleSenses = semanticResource.getWrappingConcepts(this);
        if (possibleSenses.length == 0) {
            this.isDisambiguated = false;
        }
        if (this.partOfSpeech == PartOfSpeech.Type.noun) {
            for (int i = 0; i < possibleSenses.length; i++) {
                possibleSenses[i].inTaxonomy = true;
            }
        }

    }
/**
 * 
 * @return  information  content of the word 
 */
    public double getIc() {
        InformationContentCalculator currentClaculator = InformationContentCalculatorFactory.produceObject();
        if (currentClaculator.equals(this.icCalculator)) {
            if (isIcCalculated) {
                return this.informationContent;
            }
        }
        this.icCalculator = currentClaculator;
        double result = 0;
        Concept[] senses;
        if (isDisambiguated) {
            senses = disambiguatedSenses;
        } else {
            senses = possibleSenses;
        }
        for (int i = 0; i < senses.length; i++) {
            result += senses[i].getIc();
        }
        if (senses.length == 0) {
            result = 0;
        } else {
            result /= senses.length;
        }
        this.informationContent = result;
        this.isIcCalculated = true;
        return result;
    }

    public Concept[] getPossibleSenses() {
        if (!areSensesGenerated) {
            generatePossibleSenses();
            areSensesGenerated = true;
        }
        return possibleSenses;
    }

    public boolean equals(Word word) {
        if (word.value.equals(this.value)) {
            return true;
        }
        return false;
    }

    public void setPArtOfSpeech(Type pos) {
        if (pos == Type.noun) {
            this.partOfSpeech = pos;
        }
    }

    public Type getPartOfSpeech() {
        return this.partOfSpeech;
    }

}
