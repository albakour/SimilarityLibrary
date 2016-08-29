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
import PosTagging.PostHandler;
import java.util.ArrayList;

public class TestDataSet {

    SemanticResourceHandler semanticResource;
    PostHandler tagger;
    ConceptsRelatednessAlgorithm[] measures;
    String[] defaultMeasureTypes;
    String inputFilePath;
    double[] actaulValues;
    String[] firstSentences;
    String[] secondSentences;

    public TestDataSet(String input, String[] defaultMeasures, ConceptsRelatednessAlgorithm[] measures, PostHandler tagger, SemanticResourceHandler res) {
        this.inputFilePath = input;
        this.tagger = tagger;
        this.semanticResource = res;
        this.measures = measures;
        this.defaultMeasureTypes = defaultMeasures;
    }

    public String test() throws IOException {
        String result = "";
        readDataSet();
        double delta = 0.15;
        ExtendedLeskAlgorithm leskE = new ExtendedLeskAlgorithm(null, null, semanticResource);
        leskE.setWindowSize(10);
        SentenceSenseDisambiguator sentenceDisambiguator = new SentenceSenseDisambiguator("", leskE, semanticResource, tagger);
        double[] vecArray = new double[actaulValues.length];
        double[] omArray = new double[actaulValues.length];
        double[] vecOrderArray = new double[actaulValues.length];
        double[] omOrderArray = new double[actaulValues.length];
        double threshold = 0.4;
        double step = 0.1;
        int count = 0;
        while (threshold <= 0.6) {
            threshold += step;
            count++;
            result += "_______________________________\r\n";
            result += "threshold = " + threshold + "\r\n";
            result += "_______________________________\r\n";
            for (int i = 0; i < defaultMeasureTypes.length; i++) {
                double bestPrecision = -1;
                double bestRecall = -1;
                double bestRejection = -1;
                double bestAccuracy = -1;
                double bestFmeasure = -1;
                double bestCorrelation = -1;
                int bestPrecisionIndex = 0;
                int bestRecallIndex = 0;
                int bestRejectionIndex = 0;
                int bestAccuracyIndex = 0;
                int bestFmeasureIndex = 0;
                int bestCorrelationIndex = 0;

                String bestPrecisionAlgo = "";
                String bestRecallAlgo = "";
                String bestRejectionAlgo = "";
                String bestAccuracyAlgo = "";
                String bestFmeasureAlgo = "";
                String bestCorrelationAlgo = "";
                result += "+++++++++++++++++++++++++++\r\n";
                result += "measure for verbs : " + defaultMeasureTypes[i] + "\r\n";
                result += "+++++++++++++++++++++++++++\r\n";
                DefaultConceptRelatednessMeasureFactory.produceObject(defaultMeasureTypes[i], semanticResource);
                for (int j = 0; j < measures.length; j++) {
                    result += "##########################\r\n";
                    result += measures[j].getClass() + "\r\n";
                    result += "##########################\r\n";
                    for (int k = 0; k < firstSentences.length; k++) {
                        System.out.println(count + " " + i + " " + j + " " + k);
                        LiVectorBasedSentenceSimilarityAlgorithm vecAlgo = new LiVectorBasedSentenceSimilarityAlgorithm(firstSentences[k], secondSentences[k], sentenceDisambiguator, measures[j]);
                        vecAlgo.execute();
                        WordOrderAlgorithm wordOrderAlgo = new WordOrderAlgorithm(firstSentences[k], secondSentences[k], sentenceDisambiguator, measures[j]);
                        wordOrderAlgo.execute();
                        BipertiteGraphOptimalMatchingAlgorithm omAlgo = new BipertiteGraphOptimalMatchingAlgorithm(null);
                        OptimalGraphMatchingBasedSimilarityAlgorithm omSimAlgo = new OptimalGraphMatchingBasedSimilarityAlgorithm(firstSentences[k], secondSentences[k], omAlgo, sentenceDisambiguator, measures[j]);
                        omSimAlgo.execute();
                        vecArray[k] = vecAlgo.getSimilarity();
                        vecOrderArray[k] = (1 - delta) * vecAlgo.getSimilarity() + delta * wordOrderAlgo.getSimilarity();
                        omArray[k] = omSimAlgo.getSimilarity();
                        omOrderArray[k] = (1 - delta) * omSimAlgo.getSimilarity() + delta * wordOrderAlgo.getSimilarity();
                    }
                    StatisticsHelper helper = new StatisticsHelper(actaulValues, vecArray, threshold);
                    helper.execute();
                    result += "correlation between vector algorithm and human estimation : " + helper.correlation() + "\r\n";
                    result += "precision : " + helper.precision() + "\r\n";
                    result += "recall : " + helper.recall() + "\r\n";
                    result += "rejection : " + helper.rejection() + "\r\n";
                    result += "accuracy : " + helper.accuracy() + "\r\n";
                    result += "F Measure : " + helper.fMeasure(1) + "\r\n\r\n";
                    if (bestCorrelation < helper.correlation()) {
                        bestCorrelation = helper.correlation();
                        bestCorrelationIndex = j;
                        bestCorrelationAlgo = "vctor";
                    }
                    if (bestPrecision < helper.precision()) {
                        bestPrecision = helper.precision();
                        bestPrecisionIndex = j;
                        bestPrecisionAlgo = "vector";
                    }
                    if (bestRecall < helper.recall()) {
                        bestRecall = helper.recall();
                        bestRecallIndex = j;
                        bestRecallAlgo = "vector";
                    }
                    if (bestRejection < helper.rejection()) {
                        bestRejection = helper.rejection();
                        bestRejectionIndex = j;
                        bestRejectionAlgo = "vector";
                    }
                    if (bestAccuracy < helper.accuracy()) {
                        bestAccuracy = helper.accuracy();
                        bestAccuracyIndex = j;
                        bestAccuracyAlgo = "vector";
                    }
                    if (bestFmeasure < helper.fMeasure(1)) {
                        bestFmeasure = helper.fMeasure(1);
                        bestFmeasureIndex = j;
                        bestFmeasureAlgo = "vector";
                    }
                    helper.setEstimatedData(vecOrderArray);
                    helper.execute();
                    result += "correlation between vector with order algorithm and human estimation : " + helper.correlation() + "\r\n";
                    result += "precision : " + helper.precision() + "\r\n";
                    result += "recall : " + helper.recall() + "\r\n";
                    result += "rejection : " + helper.rejection() + "\r\n";
                    result += "accuracy : " + helper.accuracy() + "\r\n";
                    result += "F Measure : " + helper.fMeasure(1) + "\r\n\r\n";
                    if (bestCorrelation < helper.correlation()) {
                        bestCorrelation = helper.correlation();
                        bestCorrelationIndex = j;
                        bestCorrelationAlgo = "vector with order";
                    }
                    if (bestPrecision < helper.precision()) {
                        bestPrecision = helper.precision();
                        bestPrecisionIndex = j;
                        bestPrecisionAlgo = "vector with order";
                    }
                    if (bestRecall < helper.recall()) {
                        bestRecall = helper.recall();
                        bestRecallIndex = j;
                        bestRecallAlgo = "vector with order";
                    }
                    if (bestRejection < helper.rejection()) {
                        bestRejection = helper.rejection();
                        bestRejectionIndex = j;
                        bestRejectionAlgo = "vector with order";
                    }
                    if (bestAccuracy < helper.accuracy()) {
                        bestAccuracy = helper.accuracy();
                        bestAccuracyIndex = j;
                        bestAccuracyAlgo = "vector with order";
                    }
                    if (bestFmeasure < helper.fMeasure(1)) {
                        bestFmeasure = helper.fMeasure(1);
                        bestFmeasureIndex = j;
                        bestFmeasureAlgo = "vector with order";
                    }
                    helper.setEstimatedData(omArray);
                    helper.execute();
                    result += "correlation between om  algorithm and human estimation : " + helper.correlation() + "\r\n";
                    result += "precision : " + helper.precision() + "\r\n";
                    result += "recall : " + helper.recall() + "\r\n";
                    result += "rejection : " + helper.rejection() + "\r\n";
                    result += "accuracy : " + helper.accuracy() + "\r\n";
                    result += "F Measure : " + helper.fMeasure(1) + "\r\n\r\n";
                    if (bestCorrelation < helper.correlation()) {
                        bestCorrelation = helper.correlation();
                        bestCorrelationIndex = j;
                        bestCorrelationAlgo = "optimal mtching";
                    }
                    if (bestPrecision < helper.precision()) {
                        bestPrecision = helper.precision();
                        bestPrecisionIndex = j;
                        bestPrecisionAlgo = "optimal mtching";
                    }
                    if (bestRecall < helper.recall()) {
                        bestRecall = helper.recall();
                        bestRecallIndex = j;
                        bestRecallAlgo = "optimal mtching";
                    }
                    if (bestRejection < helper.rejection()) {
                        bestRejection = helper.rejection();
                        bestRejectionIndex = j;
                        bestRejectionAlgo = "optimal mtching";
                    }
                    if (bestAccuracy < helper.accuracy()) {
                        bestAccuracy = helper.accuracy();
                        bestAccuracyIndex = j;
                        bestAccuracyAlgo = "optimal mtching";
                    }
                    if (bestFmeasure < helper.fMeasure(1)) {
                        bestFmeasure = helper.fMeasure(1);
                        bestFmeasureIndex = j;
                        bestFmeasureAlgo = "optimal mtching";
                    }
                    helper.setEstimatedData(omOrderArray);
                    helper.execute();
                    result += "correlation between om with order algorithm and human estimation : " + helper.correlation() + "\r\n";
                    result += "precision : " + helper.precision() + "\r\n";
                    result += "recall : " + helper.recall() + "\r\n";
                    result += "rejection : " + helper.rejection() + "\r\n";
                    result += "accuracy : " + helper.accuracy() + "\r\n";
                    result += "F Measure : " + helper.fMeasure(1) + "\r\n\r\n";
                    if (bestCorrelation < helper.correlation()) {
                        bestCorrelation = helper.correlation();
                        bestCorrelationIndex = j;
                        bestCorrelationAlgo = "optimal mtching with order";
                    }
                    if (bestPrecision < helper.precision()) {
                        bestPrecision = helper.precision();
                        bestPrecisionIndex = j;
                        bestPrecisionAlgo = "optimal mtching with order";
                    }
                    if (bestRecall < helper.recall()) {
                        bestRecall = helper.recall();
                        bestRecallIndex = j;
                        bestRecallAlgo = "optimal mtching with order";
                    }
                    if (bestRejection < helper.rejection()) {
                        bestRejection = helper.rejection();
                        bestRejectionIndex = j;
                        bestRejectionAlgo = "optimal mtching with order";
                    }
                    if (bestAccuracy < helper.accuracy()) {
                        bestAccuracy = helper.accuracy();
                        bestAccuracyIndex = j;
                        bestAccuracyAlgo = "optimal mtching with order";
                    }
                    if (bestFmeasure < helper.fMeasure(1)) {
                        bestFmeasure = helper.fMeasure(1);
                        bestFmeasureIndex = j;
                        bestFmeasureAlgo = "optimal mtching with order";
                    }

                }
                result += "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n";
                result += "best accuracy :" + bestAccuracy + "\r\n";
                result += "measure : " + measures[bestAccuracyIndex].getClass() + "\r\n";
                result += "best algorithm :" + bestAccuracyAlgo + "\r\n";
                result += "^^^^^^^^^^^^^^^^\r\n";
                result += "best precision :" + bestPrecision + "\r\n";
                result += "measure : " + measures[bestPrecisionIndex].getClass() + "\r\n";
                result += "best algorithm :" + bestPrecisionAlgo + "\r\n";
                result += "^^^^^^^^^^^^^^^^\r\n";
                result += "best recall :" + bestRecall + "\r\n";
                result += "measure : " + measures[bestRecallIndex].getClass() + "\r\n";
                result += "best algorithm :" + bestRecallAlgo + "\r\n";
                result += "^^^^^^^^^^^^^^^^\r\n";
                result += "best rejection :" + bestRejection + "\r\n";
                result += "measure : " + measures[bestRejectionIndex].getClass() + "\r\n";
                result += "best algorithm :" + bestRejectionAlgo + "\r\n";
                result += "^^^^^^^^^^^^^^^^\r\n";
                result += "best f mesure :" + bestFmeasure + "\r\n";
                result += "measure : " + measures[bestFmeasureIndex].getClass() + "\r\n";
                result += "best algorithm :" + bestFmeasureAlgo + "\r\n";
                result += "best correlation :" + bestCorrelation + "\r\n";
                result += "measure : " + measures[bestCorrelationIndex].getClass() + "\r\n";
                result += "best algorithm :" + bestCorrelationAlgo + "\r\n";
            }

        }
        return result;
    }

    private void readDataSet() throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(inputFilePath));
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
        int len = actualValues.size();
        this.actaulValues = new double[len];
        this.firstSentences = new String[len];
        this.secondSentences = new String[len];
        for (int i = 0; i < len; i++) {
            this.actaulValues[i] = actualValues.get(i);
            this.firstSentences[i] = first.get(i);
            this.secondSentences[i] = second.get(i);
        }
    }

}
