/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SemanticSimilarity;

import PosTagging.StanfordPostHandler;
import PosTagging.PartOfSpeech;
import SemanticResource.SemanticResourceHandlerFactory;
import WordSenseDisambiguation.*;
import ConceptRelatedness.*;
import ConceptRelatedness.ConceptSimilarity.InformationConctentMeasures.*;
import SemanticResource.SemanticResourceHandler;
import ConceptRelatedness.GlossMesures.*;
import Helper.StatisticsHelper;

import ConceptRelatedness.ConceptSimilarity.PathMeasures.*;
import sentenceSimilarity.*;
import ConceptRelatedness.Concept.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author sobhy
 */
public class WordSimilarity {

    /**
     * @param args the command line arguments
     */
    public static String dictionaryPath = "wordnet\\WordNet-3.0\\dict";
    public static String englishTaggerPath = "stanfordTagger\\models\\wsj-0-18-left3words-distsim.tagger";

    public static void main(String[] args) throws FileNotFoundException, IOException {
        //System.out.println("collecting WordNet statistics ... ");
        SemanticResourceHandler semanticResource = SemanticResourceHandlerFactory.produceObject("wordnet", "", dictionaryPath);
        // WordNetHandler semanticResource2 = (WordNetHandler) SemanticResourceHandlerFactory.produceObject("wordnet", dictionaryPath);
        InformationContentCalculatorFactory.setSemanticResource(semanticResource);
        DefaultConceptRelatednessMeasureFactory.produceObject("traditional gloss", semanticResource);
        StanfordPostHandler posTagger = new StanfordPostHandler();
        posTagger.connect(englishTaggerPath);
        //___________________________________
        //     test data set
        //___________________________________
        PrintWriter writer = new PrintWriter("out.txt");
        double alpha, beta;
        alpha = 0.2;
        beta = 0.6;
        String[] defaultMeasure = {"zero","traditional gloss","extended gloss"};
        PathMeasure pMeasure = new PathMeasure(null, null, semanticResource);
        WuPalmerMeasure wMeasure = new WuPalmerMeasure(null, null, semanticResource);
        LeakcockChodorowMeasure LMeasure = new LeakcockChodorowMeasure(null, null, semanticResource);
        LiMeasure LiMeasure = new LiMeasure(alpha, beta, null, null, semanticResource);
        ConceptsRelatednessAlgorithm[] measures = {pMeasure,wMeasure,LMeasure,LiMeasure};
        TestDataSet test = new TestDataSet("dataset.txt", defaultMeasure, measures, posTagger, semanticResource);
        String result = test.test();
        writer.println(result);
        writer.close();
        //System.out.println(result);

//        String sentence1, sentence2;
//        sentence1 = "pure hearts can see the beauty of the nature";//"worldwide 123 sars cases and 543 deaths have been reported in 342 countries";//
//        sentence2 = "honest people feel the beauty of the earth";//"Tiwan reported 125 new cases for a 234 total of with 432 deaths";
//        double alpha, beta;
//        alpha = 0.2;
//        beta = 0.6;
//        ExtendedLeskAlgorithm leskE = new ExtendedLeskAlgorithm(null, null, semanticResource);
//        leskE.setWindowSize(10);
//        SentenceSenseDisambiguator sentenceDisambiguator = new SentenceSenseDisambiguator("", leskE, semanticResource, posTagger);
////
//        // PathMeasure measure = new PathMeasure(null, null, semanticResource);
//        //JiangMeasure measure =new JiangMeasure(null, null, semanticResource);
//        // WuPalmerMeasure measure =new WuPalmerMeasure(null, null, semanticResource);
//        // LeakcockChodorowMeasure measure = new LeakcockChodorowMeasure(null, null, semanticResource);
//        LiMeasure measure = new LiMeasure(alpha, beta, null, null, semanticResource);
//        BufferedReader br = new BufferedReader(new FileReader("dataset.txt"));
//        double[] humanSimArray = new double[65];
//        double[] vectorArray = new double[65];
//        double[] omArray = new double[65];
//        int count = 0;
//        double humanSim;
//        double diffVec = 0;
//        double diffOm = 0;
//        try {
//            br.readLine();
//            sentence1 = br.readLine();
//            sentence2 = br.readLine();
//            String str = br.readLine();
//            humanSim = Double.parseDouble(str);
//            humanSim /= 4;
//            humanSimArray[count] = humanSim;
//            br.readLine();
//            while (true) {
//                System.out.println(count);
//                System.out.println("measuring Similsrity between :");
//                System.out.println(sentence1);
//                System.out.println(sentence2);
//                System.out.println("");
//                //InformationContentCalculator icCalc = InformationContentCalculatorFactory.produceObject();
//                LiVectorBasedSentenceSimilarityAlgorithm vecAlgo = new LiVectorBasedSentenceSimilarityAlgorithm(sentence1, sentence2, sentenceDisambiguator, measure);
//                vecAlgo.execute();
//                WordOrderAlgorithm wordOrderAlgo = new WordOrderAlgorithm(sentence1, sentence2, sentenceDisambiguator, measure);
//                wordOrderAlgo.execute();
//                double w = wordOrderAlgo.getSimilarity();
//                double sim = 0.85 * vecAlgo.getSimilarity() + 0.15 * wordOrderAlgo.getSimilarity();
//                vectorArray[count] = sim;
//                diffVec += Math.abs(humanSim - sim);
//                System.out.println("Vector Similarity Algorithm : " + sim);
//
//                BipertiteGraphOptimalMatchingAlgorithm omAlgo = new BipertiteGraphOptimalMatchingAlgorithm(null);
//                OptimalGraphMatchingBasedSimilarityAlgorithm omSimAlgo = new OptimalGraphMatchingBasedSimilarityAlgorithm(sentence1, sentence2, omAlgo, sentenceDisambiguator, measure);
//                omSimAlgo.execute();
//                sim = 0.8 * omSimAlgo.getSimilarity() + 0.2 * wordOrderAlgo.getSimilarity();
//                System.out.println("Optimal Graph Matching Similarity Algorithm : " + sim);
//                if ((sim >= 0.6 && humanSim >= 0.6) || (sim < 0.6 && humanSim < 0.6)) {
//                    System.out.println("*******************");
//                } else {
//                    System.out.println("---------------");
//                }
//
//                omArray[count] = sim;
//                diffOm += Math.abs(humanSim - omSimAlgo.getSimilarity());
//                System.out.println("");
//                System.out.println(omSimAlgo.getMatching());
//                System.out.println("___________________________");
//                br.readLine();
//                sentence1 = br.readLine();
//                if (sentence1 == null) {
//                    break;
//                }
//                sentence2 = br.readLine();
//                str = br.readLine();
//                count++;
//                humanSim = Double.parseDouble(str);
//                humanSim /= 4;
//                humanSimArray[count] = humanSim;
//                br.readLine();
//
//            }
//
//        } finally {
//            br.close();
//        }
//        System.out.println("Vector Mean Differnce : " + diffVec / (count));
//        System.out.println("Optimal Matching Mean Differnce : " + diffOm / (count));
//        StatisticsHelper helper = new StatisticsHelper(humanSimArray, vectorArray, 0.6);
//        helper.execute();
//        //double corr=correlation(vectorArray, humanSimArray);
//        System.out.println("correlation between vector algorithm and human estimation : " + helper.correlation());
//        System.out.println("precision : " + helper.precision());
//        System.out.println("recall : " + helper.recall());
//        System.out.println("rejection : " + helper.rejection());
//        System.out.println("accuracy : " + helper.accuracy());
//        System.out.println("F Measure : " + helper.fMeasure(1));
//        //corr=correlation(omArray, humanSimArray);
//        System.out.println("");
//        helper.setEstimatedData(omArray);
//        helper.execute();
//        System.out.println("correlation between om  algorithm and human estimation : " + helper.correlation());
//        System.out.println("precision : " + helper.precision());
//        System.out.println("recall : " + helper.recall());
//        System.out.println("rejection : " + helper.rejection());
//        System.out.println("accuracy : " + helper.accuracy());
//        System.out.println("F Measure : " + helper.fMeasure(1));
        //__________________________________________
        //            end test data set
        //__________________________________________
//        InformationContentCalculator icCalc = InformationContentCalculatorFactory.produceObject();
//        LiVectorBasedSentenceSimilarityAlgorithm vecAlgo = new LiVectorBasedSentenceSimilarityAlgorithm(sentence1, sentence2, sentenceDisambiguator, measure, icCalc);
//        System.out.println("Vector Similarity Algorithm");
//        vecAlgo.execute();
//        System.out.println(vecAlgo.getSimilarity());
//        System.out.println("Optimal Graph Matching Similarity Algorithm");
//        BipertiteGraphOptimalMatchingAlgorithm omAlgo = new BipertiteGraphOptimalMatchingAlgorithm(null);
//        OptimalGraphMatchingBasedSimilarityAlgorithm omSimAlgo = new OptimalGraphMatchingBasedSimilarityAlgorithm(sentence1, sentence2, omAlgo, sentenceDisambiguator, measure);
//        omSimAlgo.execute();
//        System.out.println(omSimAlgo.getSimilarity());
//        WordOrderAlgorithm wordOrderAlgo = new WordOrderAlgorithm(sentence1, sentence2, sentenceDisambiguator, measure);
//        wordOrderAlgo.execute();
//        System.out.println("WordOrder Algorithm");
//        System.out.println(wordOrderAlgo.getSimilarity());
//        System.out.println("Combined Algorithm Vector & Word Order :");
//        double sim = 0.8 * vecAlgo.getSimilarity() + 0.2 * wordOrderAlgo.getSimilarity();
//        System.out.println(sim);
//        System.out.println("Combined Algorithm Optimal Matching & Word Order :");
//        sim = 0.8 * omSimAlgo.getSimilarity() + 0.2 * wordOrderAlgo.getSimilarity();
//        System.out.println(sim);
        // init to collect statistics 
        //        //semanticResource.init();
        //        System.out.println("number of concepts : " + semanticResource.getNumberOfConcepts());
        //        System.out.println("max depth : " + semanticResource.getMaxDepth());
        //        System.out.println("number of leaves : " + semanticResource.getMaxLeaves());
        //        System.out.println("average number of direct successors : " + semanticResource.getAverageNumberOfDirectSuccessors());
        //        // System.out.println("average number of parent edges : " + WordNetHandler.calculateParentEdegeAverageNouns());
        //        System.out.println("");
        //_________________________________________________________
        // test Disambiguation
        //_________________________________________________________
//        int windowSize=10;
//        String sentence="the river bank is slope  ";
//        ExtendedLeskAlgorithm leskE = new ExtendedLeskAlgorithm(null, null, semanticResource);
//        OrginalLeskAlgorithm lesk = new OrginalLeskAlgorithm(null, null, semanticResource);
//       SentenceSenseDisambiguator sentenceDisambiguator = new SentenceSenseDisambiguator(sentence, lesk, semanticResource, posTagger);
//        System.out.println("Orginal Lesk Algorithm : ");
//        sentenceDisambiguator.execute(windowSize);
//        sentenceDisambiguator.printDisambiguatedWords();
//        sentenceDisambiguator.setWordSenseDisambiguator(leskE);
//        System.out.println("");
//        System.out.println("Extended Lesk Algorithm : ");
//        sentenceDisambiguator.execute(windowSize);
//        sentenceDisambiguator.printDisambiguatedWords();
//        String wordForm1 = "man";
//        String wordForm2 = "guy";
//        Word[] words = new Word[2];
//        Word word1 = new Word(wordForm1, semanticResource);
//        Word word2 = new Word(wordForm2, semanticResource);
//        word1.setPArtOfSpeech(PartOfSpeech.Type.noun);
//        word2.setPArtOfSpeech(PartOfSpeech.Type.noun);
//        words[0] = word1;
//        words[1] = word2;
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
//        Concept concept1 = ((WordNetHandler) semanticResource).getWrappingConcepts(word1)[8];
//        Concept concept2 = ((WordNetHandler) semanticResource).getWrappingConcepts(word2)[0];
//        concept1.inTaxonomy = true;
//        concept1.setPartOfSpeech(PartOfSpeech.Type.noun);
//        concept2.inTaxonomy = true;
//        concept2.setPartOfSpeech(PartOfSpeech.Type.noun);
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
//                PathMeasure pM = new PathMeasure(concept1, concept2, semanticResource);
//                pM.execute();
//                System.out.println("Path Measure : " + pM.getRelatedness());
//                System.out.println("Normalized Similarity : " + pM.getNormalizedRelatedness());
//                System.out.println("Maximum Similarity : " + pM.getMaximum());
//                System.out.println("Minimum Similarity : " + pM.getMinimum());
//                System.out.println("Explanation : ");
//                System.out.println(pM.getExplanation());
//                System.out.println("");
//        
//                WuPalmerMeasure wpM = new WuPalmerMeasure(concept1, concept2, semanticResource);
//                wpM.execute();
//                System.out.println("Wu Palmer's Measure : " + wpM.getRelatedness());
//                System.out.println("Normalized Similarity : " + wpM.getNormalizedRelatedness());
//                System.out.println("Maximum Similarity : " + wpM.getMaximum());
//                System.out.println("Minimum Similarity : " + wpM.getMinimum());
//                System.out.println("Explanation : ");
//                System.out.println(wpM.getExplanation());
//                System.out.println("");
//        
//                LeakcockChodorowMeasure lcM = new LeakcockChodorowMeasure(concept1, concept2, semanticResource);
//                lcM.execute();
//                System.out.println("Leakcock & Chodorow's Measure : " + lcM.getRelatedness());
//                System.out.println("Normalized Similarity : " + lcM.getNormalizedRelatedness());
//                System.out.println("Maximum Similarity : " + lcM.getMaximum());
//                System.out.println("Minimum Similarity : " + lcM.getMinimum());
//                System.out.println("Explanation : ");
//                System.out.println(lcM.getExplanation());
//                System.out.println("");
//        
//                //double alpha, beta;
//                // optimal parameters experimentally
//                alpha = 0.2;
//                beta = 0.6;
//                LiMeasure lM = new LiMeasure(alpha, beta, concept1, concept2, semanticResource);
//                lM.execute();
//                System.out.println("Li's Measure : " + lM.getRelatedness());
//                System.out.println("Normalized Similarity : " + lM.getNormalizedRelatedness());
//                System.out.println("Maximum Similarity : " + lM.getMaximum());
//                System.out.println("Minimum Similarity : " + lM.getMinimum());
//                System.out.println("Explanation : ");
//                System.out.println(lM.getExplanation());
//        
//                System.out.println("____________________________");
//                System.out.println("Information Content Measures");
//                System.out.println("");
//        
//                ReniskMeasure rM = new ReniskMeasure(concept1, concept2, semanticResource);
//                rM.execute();
//                System.out.println("Renisk's Measure : " + rM.getRelatedness());
//                System.out.println("Normalized Similarity : " + rM.getNormalizedRelatedness());
//                System.out.println("Maximum Similarity : " + rM.getMaximum());
//                System.out.println("Minimum Similarity : " + rM.getMinimum());
//                System.out.println("Explanation : ");
//                System.out.println(rM.getExplanation());
//        
//                LinMeasure linM = new LinMeasure(concept1, concept2, semanticResource);
//                linM.execute();
//                System.out.println("Lin's Measure : " + linM.getRelatedness());
//                System.out.println("Normalized Similarity : " + linM.getNormalizedRelatedness());
//                System.out.println("Maximum Similarity : " + linM.getMaximum());
//                System.out.println("Minimum Similarity : " + linM.getMinimum());
//                System.out.println("Explanation : ");
//                System.out.println(linM.getExplanation());
//        
//                JiangMeasure jM = new JiangMeasure(concept1, concept2, semanticResource);
//                jM.execute();
//                System.out.println("Jiang's Meausure : " + jM.getRelatedness());
//                System.out.println("Normalized Similarity : " + jM.getNormalizedRelatedness());
//                System.out.println("Maximum Similarity : " + jM.getMaximum());
//                System.out.println("Minimum Similarity : " + jM.getMinimum());
//                System.out.println("Explanation : ");
//                System.out.println(jM.getExplanation());
//        
//                System.out.println("____________________________");
//                System.out.println("Hybrid Measures");
//                System.out.println("");
//                // the experimantal optimal values
//                alpha = 0.5;
//                beta = 0.3;
//                JiangCornathMeasure jcM = new JiangCornathMeasure(concept1, concept2, alpha, beta, semanticResource);
//                jcM.execute();
//                System.out.println("Jiang & Cornath's Measure : " + jcM.getRelatedness());
//                System.out.println("Normalized Similarity : " + jcM.getNormalizedRelatedness());
//                System.out.println("Maximum Similarity : " + jcM.getMaximum());
//                System.out.println("Minimum Similarity : " + jcM.getMinimum());
//                System.out.println("Explanation : ");
//                System.out.println(jcM.getExplanation());
    }

    public static double correlation(double[] x, double[] y) {
        if (x.length != y.length) {
            return 0;
        }
        double xBar = 0, yBar = 0;
        for (int i = 0; i < x.length; i++) {
            xBar += x[i];
            yBar += y[i];
        }
        xBar /= x.length;
        yBar /= y.length;
        double sxy = 0, sx = 0, sy = 0;
        for (int i = 0; i < x.length; i++) {
            sxy += (x[i] - xBar) * (y[i] - yBar);
            sx += (x[i] - xBar) * (x[i] - xBar);
            sy += (y[i] - yBar) * (y[i] - yBar);
        }
        double result = sxy / Math.sqrt(sx * sy);
        return result;

    }

    private static void readDataSet() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("dataset.txt"));
        ArrayList<Double> actualValues = new ArrayList<>();
        ArrayList<String> first = new ArrayList<>();
        ArrayList<String> second = new ArrayList<>();
        String sentence1, sentence2, str, line;
        int count = 0;
        double actualValue;
        try {
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                sentence1 = br.readLine();
                sentence2 = br.readLine();
                str = br.readLine();
                first.add(sentence1);
                second.add(sentence2);
                actualValue = Double.parseDouble(str);
                actualValue /= 4;
                actualValues.add(actualValue);
                br.readLine();
            }
        } finally {
            br.close();
        }
    }

}
