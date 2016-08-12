/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SemanticSimilarity;

import ConceptRelatedness.Concept.Concept;
import WordSenseDisambiguation.*;

import ConceptRelatedness.ConceptSimilarity.InformationConctentMeasures.*;
import ConceptRelatedness.SemanticResource.SemanticResourceHandler;

import ConceptRelatedness.ConceptSimilarity.PathMeasures.*;
import ConceptRelatedness.SemanticResource.*;


/**
 *
 * @author sobhy
 */
public class WordSimilarity {

    /**
     * @param args the command line arguments
     */
    public static String dictionaryPath = "C:\\Users\\sobhy\\Documents\\NetBeansProjects\\4th year project libs\\WordNet-3.0\\dict";

    public static void main(String[] args) {
        //System.out.println("collecting WordNet statistics ... ");
        SemanticResourceHandler semanticResource = SemanticResourceHandlerFactory.produceObject("wordnet","",dictionaryPath);
        // WordNetHandler semanticResource2 = (WordNetHandler) SemanticResourceHandlerFactory.produceObject("wordnet", dictionaryPath);
        InformationContentCalculatorFactory.setSemanticResource(semanticResource);
        

//        // init to collect statistics 
//        //semanticResource.init();
//        System.out.println("number of concepts : " + semanticResource.getNumberOfConcepts());
//        System.out.println("max depth : " + semanticResource.getMaxDepth());
//        System.out.println("number of leaves : " + semanticResource.getMaxLeaves());
//        System.out.println("average number of direct successors : " + semanticResource.getAverageNumberOfDirectSuccessors());
//        // System.out.println("average number of parent edges : " + WordNetHandler.calculateParentEdegeAverageNouns());
//        System.out.println("");
        String wordForm1, wordForm2;
        Concept concept1, concept2;
//        String sentence = "building bank ";
//        int windowSize = 3;
//
//        ExtendedLeskAlgorithm leskE = new ExtendedLeskAlgorithm(null, null, semanticResource);
//        OrginalLeskAlgorithm lesk = new OrginalLeskAlgorithm(null, null, semanticResource);
//        SentenceSenseDisambiguator sentenceDisambiguator = new SentenceSenseDisambiguator(sentence, lesk);
//        System.out.println("Orginal Lesk Algorithm : ");
//        sentenceDisambiguator.execute(windowSize);
//        sentenceDisambiguator.printDisambiguatedWords();
//        sentenceDisambiguator.setWordSenseDisambiguator(leskE);
//        System.out.println("");
//        System.out.println("Extended Lesk Algorithm : ");
//        sentenceDisambiguator.execute(windowSize);
//        sentenceDisambiguator.printDisambiguatedWords();

        wordForm1 = "mail";
        wordForm2 = "tiger";
        Word[] words = new Word[2];
        Word word1 = new Word(wordForm1);
        Word word2 = new Word(wordForm2);
        words[0] = word1;
        words[1] = word2;
//
//        System.out.println("Disambiguating the word(" + wordForm2 + ") in (" + wordForm1 + " " + wordForm2 + ").");
//        OrginalLeskAlgorithm lesk = new OrginalLeskAlgorithm(words, word2, semanticResource);
//        System.out.println("Orginal Lesk Algorithm : ");
//        lesk.execute();
//        System.out.println("Definitions : ");
//        Concept[] senses = lesk.getDisambiguatedWord().getDisamiguatedSenses();
//        for (int i=0;i<senses.length;i++) {
//            System.out.println(i+"-"+senses[i].getDefinition());
//        }
//
//        System.out.println("");
//        ExtendedLeskAlgorithm leskE = new ExtendedLeskAlgorithm(words, word2, semanticResource);
//        System.out.println("Extended Lesk Algorithm : ");
//        lesk.execute();
//        System.out.println("Definition : ");
//         senses = leskE.getDisambiguatedWord().getDisamiguatedSenses();
//        for (int i=0;i<senses.length;i++) {
//            System.out.println(i+"-"+senses[i].getDefinition());
//        }
        concept1 = ((WordNetHandler) semanticResource).getWrappingConcepts(wordForm1)[0];
        concept2 = ((WordNetHandler) semanticResource).getWrappingConcepts(wordForm2)[0];
//
//        GlossAlgorithm g = new TraditionalGlossMeasure(concept1, concept2, semanticResource);
//        g.execute();
//        System.out.println("gloss measure : " + g.relatedness);
//        System.out.println("Normalized Similarity : " + g.normalizedRelatedness);
//
//        GlossAlgorithm e = new ExtendedGlossMasure(concept1, concept2, semanticResource);
//        e.execute();
//        System.out.println("gloss measure : " + e.relatedness);
//        System.out.println("Normalized Similarity : " + e.normalizedRelatedness);
//        System.out.println("measuring the similarity between (" + word1 + ") and (" + word2 + ") : ");
//        System.out.println("Path Measures");
//        System.out.println("");
////        System.out.println("edited");
////        Concept lcsEdit = semanticResource2.getLcsEdited(concept1, concept2);
////        System.out.println(lcsEdit.representAsString());
////        System.out.println("");
////        ArrayList<Concept> listEdit = semanticResource2.getShortestPath(concept1, concept2, lcsEdit);
////        int len = listEdit.size();
////        for (int i = 0; i < len; i++) {
////            System.out.println(i + "-" + listEdit.get(i).representAsString());
////        }
//
////        System.out.println("old");
////        Concept lcs = semanticResource2.getLcs(concept1, concept2);
////
////        System.out.println(lcs.representAsString());
////        System.out.println("");
////        ArrayList<Concept> list = semanticResource2.getShortestPath(concept1, concept2, lcs);
////        len = list.size();
////        for (int i = 0; i < len; i++) {
////            System.out.println(i + "-" + list.get(i).representAsString());
////        }
        PathMeasure pM = new PathMeasure(concept1, concept2, semanticResource);
        pM.execute();
        System.out.println("Path Measure : " + pM.getRelatedness());
        System.out.println("Normalized Similarity : " + pM.getNormalizedRelatedness());
        System.out.println("Maximum Similarity : " + pM.getMaximum());
        System.out.println("Minimum Similarity : " + pM.getMinimum());
        System.out.println("Explanation : ");
        System.out.println(pM.getExplanation());
        System.out.println("");

        WuPalmerMeasure wpM = new WuPalmerMeasure(concept1, concept2, semanticResource);
        wpM.execute();
        System.out.println("Wu Palmer's Measure : " + wpM.getRelatedness());
        System.out.println("Normalized Similarity : " + wpM.getNormalizedRelatedness());
        System.out.println("Maximum Similarity : " + wpM.getMaximum());
        System.out.println("Minimum Similarity : " + wpM.getMinimum());
        System.out.println("Explanation : ");
        System.out.println(wpM.getExplanation());
        System.out.println("");

        LeakcockChodorowMeasure lcM = new LeakcockChodorowMeasure(concept1, concept2, semanticResource);
        lcM.execute();
        System.out.println("Leakcock & Chodorow's Measure : " + lcM.getRelatedness());
        System.out.println("Normalized Similarity : " + lcM.getNormalizedRelatedness());
        System.out.println("Maximum Similarity : " + lcM.getMaximum());
        System.out.println("Minimum Similarity : " + lcM.getMinimum());
        System.out.println("Explanation : ");
        System.out.println(lcM.getExplanation());
        System.out.println("");

        double alpha, beta;
        // optimal parameters experimentally
        alpha = 0.2;
        beta = 0.6;
        LiMeasure lM = new LiMeasure(alpha, beta, concept1, concept2, semanticResource);
        lM.execute();
        System.out.println("Li's Measure : " + lM.getRelatedness());
        System.out.println("Normalized Similarity : " + lM.getNormalizedRelatedness());
        System.out.println("Maximum Similarity : " + lM.getMaximum());
        System.out.println("Minimum Similarity : " + lM.getMinimum());
        System.out.println("Explanation : ");
        System.out.println(lM.getExplanation());

        System.out.println("____________________________");
        System.out.println("Information Content Measures");
        System.out.println("");

        ReniskMeasure rM = new ReniskMeasure(concept1, concept2, semanticResource);
        rM.execute();
        System.out.println("Renisk's Measure : " + rM.getRelatedness());
        System.out.println("Normalized Similarity : " + rM.getNormalizedRelatedness());
        System.out.println("Maximum Similarity : " + rM.getMaximum());
        System.out.println("Minimum Similarity : " + rM.getMinimum());
        System.out.println("Explanation : ");
        System.out.println(rM.getExplanation());

        LinMeasure linM = new LinMeasure(concept1, concept2, semanticResource);
        linM.execute();
        System.out.println("Lin's Measure : " + linM.getRelatedness());
        System.out.println("Normalized Similarity : " + linM.getNormalizedRelatedness());
        System.out.println("Maximum Similarity : " + linM.getMaximum());
        System.out.println("Minimum Similarity : " + linM.getMinimum());
        System.out.println("Explanation : ");
        System.out.println(linM.getExplanation());

        JiangMeasure jM = new JiangMeasure(concept1, concept2, semanticResource);
        jM.execute();
        System.out.println("Jiang's Meausure : " + jM.getRelatedness());
        System.out.println("Normalized Similarity : " + jM.getNormalizedRelatedness());
        System.out.println("Maximum Similarity : " + jM.getMaximum());
        System.out.println("Minimum Similarity : " + jM.getMinimum());
        System.out.println("Explanation : ");
        System.out.println(jM.getExplanation());

        System.out.println("____________________________");
        System.out.println("Hybrid Measures");
        System.out.println("");
        // the experimantal optimal values
        alpha = 0.5;
        beta = 0.3;
        JiangCornathMeasure jcM = new JiangCornathMeasure(concept1, concept2, alpha, beta, semanticResource);
        jcM.execute();
        System.out.println("Jiang & Cornath's Measure : " + jcM.getRelatedness());
        System.out.println("Normalized Similarity : " + jcM.getNormalizedRelatedness());
        System.out.println("Maximum Similarity : " + jcM.getMaximum());
        System.out.println("Minimum Similarity : " + jcM.getMinimum());
        System.out.println("Explanation : ");
        System.out.println(jcM.getExplanation());
    }

}
