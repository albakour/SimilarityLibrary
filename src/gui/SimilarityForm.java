/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import PosTagging.StanfordPostHandler;
import PosTagging.PartOfSpeech;
import SemanticResource.SemanticResourceHandlerFactory;
import WordSenseDisambiguation.*;
import ConceptRelatedness.*;
import ConceptRelatedness.ConceptSimilarity.InformationConctentMeasures.*;
import SemanticResource.SemanticResourceHandler;

import ConceptRelatedness.ConceptSimilarity.PathMeasures.*;
import ConceptRelatedness.ConceptSimilarity.InformationConctentMeasures.*;
import sentenceSimilarity.*;
import ConceptRelatedness.Concept.*;
import static SemanticSimilarity.WordSimilarity.dictionaryPath;
import static SemanticSimilarity.WordSimilarity.englishTaggerPath;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.naming.spi.DirStateFactory;

public class SimilarityForm extends javax.swing.JFrame {

    String firstSentence;
    String secondSentence;
    int SimilarityWindowSize = 10;
    public static String dictionaryPath = "wordnet\\WordNet-3.0\\dict";
    public static String englishTaggerPath = "stanfordTagger\\models\\wsj-0-18-left3words-distsim.tagger";
    SemanticResourceHandler semanticResource;
    StanfordPostHandler posTagger;
    String similarityWordDisambiguator;
    SentenceSenseDisambiguator similaritySentenceDisambiguator;
    ConceptsRelatednessAlgorithm conceptMeasure;
    OptimalGraphMatchingAlgorithm omAlgo;
    SentenceSimilarityAlgorithm senSimAlgo;
    String similarityResult;
    String sentenceToDisiambiguate;
    SentenceSenseDisambiguator sentenceDisambiguator;
    MaximizeRelatednessWSDAlgorithm wordSenseDisambiguator;
    int disambiguationWindowSize = 10;
    String disambiguationResult;
    WordOrderAlgorithm wordOrderAlgo;
    double structureWeight = 0.15;
    String defaultMeasure = "zero";
    String informationContentFunction = "unit";

    ConceptsRelatednessAlgorithm disambiguationConceptMeasure;

    String sentenceSimilarityWordDisambiguatorType = "lesk";
    String similarityconceptRelatednessType = "path";
    String disambiguationConceptMeasureType = "extended gloss";
    boolean withStructure = false;
    // boolean isPath = true;

    public SimilarityForm() {
        initComponents();
        init();
        initSimilarity();
        initDisambiguation();

    }

    private void init() {
        //System.out.println("collecting WordNet statistics ... ");
        semanticResource = SemanticResourceHandlerFactory.setConfiguration("wordnet", "", dictionaryPath);
        // WordNetHandler semanticResource2 = (WordNetHandler) SemanticResourceHandlerFactory.produceObject("wordnet", dictionaryPath);
        InformationContentCalculatorFactory.setSemanticResource(semanticResource);
        InformationContentCalculatorFactory.setType(informationContentFunction);
        posTagger = new StanfordPostHandler();
        posTagger.connect(englishTaggerPath);
        firstSentence = "pure hearts can see the beauty of the nature";
        secondSentence = "honest people feel the beauty of the earth";
        firstSentenceTxtAr.setText(firstSentence);
        secondSentneceTxtAr.setText(secondSentence);

    }

    private void initSimilarity() {
        setSimilarityDisambiguator();
        setConceptMeasure();
        DefaultConceptRelatednessMeasureFactory.setType(defaultMeasure);
        //setSimilarityAlgorithm();

//        similarityWordDisambiguator = new ExtendedLeskAlgorithm(null, null, semanticResource);
//        similarityWordDisambiguator.setWindowSize(SimilarityWindowSize);
//        similaritySentenceDisambiguator = new SentenceSenseDisambiguator("", similarityWordDisambiguator, semanticResource, posTagger);
//        conceptMeasure = new WuPalmerMeasure(null, null, semanticResource);
//        omAlgo = new BipertiteGraphOptimalMatchingAlgorithm(null);
    }

    private void setSimilarityDisambiguator() {
        WordSenseDisambiguationAlgorithm wordDisambiguator = WordSenseDisambiguation.WSDAlgorithmFactory.produceObject(sentenceSimilarityWordDisambiguatorType);
        wordDisambiguator.setWindowSize(SimilarityWindowSize);
        similaritySentenceDisambiguator = new SentenceSenseDisambiguator("", wordDisambiguator, semanticResource, posTagger);
        similaritySentenceDisambiguator.setWidowSize(SimilarityWindowSize);
    }

//    private void setSimilarityAlgorithm() {
//        if (sentenceSimilarityType == 0) {
//            omAlgo = new BipertiteGraphOptimalMatchingAlgorithm(null);
//            senSimAlgo = new MouhibiMatchingMeasure(firstSentence, secondSentence, null, omAlgo, similaritySentenceDisambiguator, conceptMeasure);
//        } else if (sentenceSimilarityType == 1) {
//            senSimAlgo = new LiVectorMeasure(firstSentence, secondSentence, similaritySentenceDisambiguator, conceptMeasure);
//        } else {
//            // HybridWeighter weighter=new HybridWeighter(null);
//            senSimAlgo = new HybridMatchingMeasure(firstSentence, secondSentence, null, omAlgo, sentenceDisambiguator, conceptMeasure);
//        }
//        wordOrderAlgo = new WordOrderAlgorithm(firstSentence, secondSentence, similaritySentenceDisambiguator, conceptMeasure);
//    }
    private void setConceptMeasure() {
        conceptMeasure = ConceptRelatednessMeasureFactory.produceObject(similarityconceptRelatednessType);
//        if (isPath) {
//            if (similarityconceptRelatednessType == 0) {
//                conceptMeasure = new PathMeasure();
//                return;
//            }
//            if (similarityconceptRelatednessType == 1) {
//                double alpha, beta;
//                alpha = 0.2;
//                beta = 0.6;
//                conceptMeasure = new LiMeasure(alpha, beta);
//                return;
//            }
//            if (similarityconceptRelatednessType == 2) {
//                conceptMeasure = new WuPalmerMeasure();
//                return;
//            }
//            if (similarityconceptRelatednessType == 3) {
//                conceptMeasure = new LeakcockChodorowMeasure();
//                return;
//            }
//            if (similarityconceptRelatednessType == 4) {
//                double alpha, beta;
//                alpha = 0.5;
//                beta = 0.3;
//                conceptMeasure = new JiangCornathMeasure(null, null, alpha, beta);
//            }
//
//        } else {
//            if (similarityconceptRelatednessType == 0) {
//                conceptMeasure = new ResnikkMeasure();
//                return;
//            }
//            if (similarityconceptRelatednessType == 1) {
////                double alpha, beta;
////                alpha = 0.2;
////                beta = 0.6;
//                conceptMeasure = new LinMeasure();
//                return;
//            }
//            if (similarityconceptRelatednessType == 2) {
//                conceptMeasure = new JiangMeasure();
//            }
//
//        }

    }

    private void initDisambiguation() {
//        setDisambiguationConceptMeasure();
//        wordSenseDisambiguator = new MaximizeRelatednessWSDAlgorithm();
//        wordSenseDisambiguator = new ExtendedLeskAlgorithm();
//        wordSenseDisambiguator.setWindowSize(disambiguationWindowSize);
        //sentenceDisambiguator = new SentenceSenseDisambiguator("", wordSenseDisambiguator, semanticResource, posTagger);
        setSentenceDisambiguator();
        sentenceToDisiambiguate = "pure hearts can see the beauty of the nature";
        sentencetoDisambiguateTxtAr.setText(sentenceToDisiambiguate);

    }

    private void setDisambiguationConceptMeasure() {
        disambiguationConceptMeasure = ConceptRelatednessMeasureFactory.produceObject(disambiguationConceptMeasureType);
        wordSenseDisambiguator.setRelatednessMeasure(disambiguationConceptMeasure);
    }

    private void setSentenceDisambiguator() {
        wordSenseDisambiguator = new MaximizeRelatednessWSDAlgorithm();
        setDisambiguationConceptMeasure();
        wordSenseDisambiguator.setRelatednessMeasure(disambiguationConceptMeasure);
        wordSenseDisambiguator.setWindowSize(disambiguationWindowSize);
        sentenceDisambiguator = new SentenceSenseDisambiguator("", wordSenseDisambiguator, semanticResource, posTagger);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        firstSentenceTxtAr = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        secondSentneceTxtAr = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        mouhibiTxtAr = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        calculateSimilarityBtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        newMethodTxtAr = new javax.swing.JTextArea();
        jScrollPane7 = new javax.swing.JScrollPane();
        vectorMeasureTxtAr = new javax.swing.JTextArea();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        sentencetoDisambiguateTxtAr = new javax.swing.JTextArea();
        disAmbiguateBtn = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        disambiguateResultTxtAr = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        withStructureRadioBtn = new javax.swing.JRadioButton();
        similarityDisambiguationAlgorithmComBx = new javax.swing.JComboBox();
        jlabel7 = new javax.swing.JLabel();
        pathBasedComBx = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        informationContentComBx = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        nonNounsSimilarityMeasureComBx = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        informationContentFunctionComBx = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        informationContentComBx1 = new javax.swing.JComboBox();
        pathBasedComBx1 = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        glossBasedMeasureComBx = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        firstSentenceTxtAr.setColumns(20);
        firstSentenceTxtAr.setRows(5);
        jScrollPane1.setViewportView(firstSentenceTxtAr);

        jLabel1.setText("First Sentence");

        secondSentneceTxtAr.setColumns(20);
        secondSentneceTxtAr.setRows(5);
        jScrollPane2.setViewportView(secondSentneceTxtAr);

        mouhibiTxtAr.setColumns(20);
        mouhibiTxtAr.setRows(5);
        jScrollPane3.setViewportView(mouhibiTxtAr);

        jLabel2.setText("Second Sentence");

        calculateSimilarityBtn.setText("Calculat Similarity");
        calculateSimilarityBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateSimilarityBtnActionPerformed(evt);
            }
        });

        jLabel3.setText("optimal matching result");

        newMethodTxtAr.setColumns(20);
        newMethodTxtAr.setRows(5);
        jScrollPane6.setViewportView(newMethodTxtAr);

        vectorMeasureTxtAr.setColumns(20);
        vectorMeasureTxtAr.setRows(5);
        jScrollPane7.setViewportView(vectorMeasureTxtAr);

        jLabel14.setText("new method result");

        jLabel15.setText("vector result");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(348, 348, 348)
                                .addComponent(calculateSimilarityBtn))
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(9, 9, 9)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(calculateSimilarityBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel14)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Sentence Similarity", jPanel1);

        sentencetoDisambiguateTxtAr.setColumns(20);
        sentencetoDisambiguateTxtAr.setRows(5);
        jScrollPane4.setViewportView(sentencetoDisambiguateTxtAr);

        disAmbiguateBtn.setText("Disambiguate Sentence");
        disAmbiguateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disAmbiguateBtnActionPerformed(evt);
            }
        });

        jLabel4.setText("Sentence");

        disambiguateResultTxtAr.setColumns(20);
        disambiguateResultTxtAr.setRows(5);
        jScrollPane5.setViewportView(disambiguateResultTxtAr);

        jLabel5.setText("Result");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel4))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel5)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(312, 312, 312)
                .addComponent(disAmbiguateBtn)
                .addGap(0, 455, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(disAmbiguateBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Sentence Sense Disambiguation", jPanel2);

        withStructureRadioBtn.setText("with structure similarity?");
        withStructureRadioBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                withStructureRadioBtnActionPerformed(evt);
            }
        });

        similarityDisambiguationAlgorithmComBx.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "lesk-e", "lesk" }));
        similarityDisambiguationAlgorithmComBx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                similarityDisambiguationAlgorithmComBxActionPerformed(evt);
            }
        });

        jlabel7.setText("Disambiguation Algorithm");

        pathBasedComBx.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "path", "li", "wu", "leakcok", "jiang conrath" }));
        pathBasedComBx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pathBasedComBxActionPerformed(evt);
            }
        });

        jLabel7.setText("Path Based");

        jLabel8.setText("InformationContent Based");

        informationContentComBx.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "resnik", "lin", "jiang" }));
        informationContentComBx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                informationContentComBxActionPerformed(evt);
            }
        });

        jLabel12.setText("Concept Relatedness Algorithm for Sentece Similarity");

        jLabel13.setText("non nouns Similarity Measure");

        nonNounsSimilarityMeasureComBx.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "zero", "traditional gloss", "extended gloss" }));
        nonNounsSimilarityMeasureComBx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nonNounsSimilarityMeasureComBxActionPerformed(evt);
            }
        });

        jLabel6.setText("information content function");

        informationContentFunctionComBx.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "unit", "seco", "sanchez" }));
        informationContentFunctionComBx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                informationContentFunctionComBxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(pathBasedComBx, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(74, 74, 74)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(informationContentComBx, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8)))
                            .addComponent(jlabel7)
                            .addComponent(withStructureRadioBtn))
                        .addGap(0, 577, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(similarityDisambiguationAlgorithmComBx, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(nonNounsSimilarityMeasureComBx, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(237, 237, 237)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(informationContentFunctionComBx, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(nonNounsSimilarityMeasureComBx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(26, 26, 26)
                .addComponent(informationContentFunctionComBx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(withStructureRadioBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlabel7)
                .addGap(18, 18, 18)
                .addComponent(similarityDisambiguationAlgorithmComBx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84)
                .addComponent(jLabel12)
                .addGap(34, 34, 34)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(informationContentComBx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pathBasedComBx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(177, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Senetence Similarity", jPanel3);

        informationContentComBx1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "resnik", "lin", "jiang" }));
        informationContentComBx1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                informationContentComBx1ActionPerformed(evt);
            }
        });

        pathBasedComBx1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "path", "li", "wu", "leakcok", "jiang conrath" }));
        pathBasedComBx1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pathBasedComBx1ActionPerformed(evt);
            }
        });

        jLabel9.setText("path based");

        jLabel10.setText("information content ");

        jLabel11.setText("Concept Relatedness Algorithm");

        jLabel16.setText("gloss based");

        glossBasedMeasureComBx.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "traditional gloss", "extended gloss" }));
        glossBasedMeasureComBx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                glossBasedMeasureComBxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel10)
                    .addComponent(informationContentComBx1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel9)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(glossBasedMeasureComBx, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pathBasedComBx1, javax.swing.GroupLayout.Alignment.LEADING, 0, 119, Short.MAX_VALUE)))
                .addContainerGap(734, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel11)
                .addGap(28, 28, 28)
                .addComponent(jLabel16)
                .addGap(26, 26, 26)
                .addComponent(glossBasedMeasureComBx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(pathBasedComBx1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114)
                .addComponent(jLabel10)
                .addGap(30, 30, 30)
                .addComponent(informationContentComBx1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(120, 120, 120))
        );

        jTabbedPane2.addTab("Sentense Sense Disambiguation ", jPanel4);

        jTabbedPane1.addTab("Configuration", jTabbedPane2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void disAmbiguateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disAmbiguateBtnActionPerformed
        // TODO add your handling code here:
        sentenceToDisiambiguate = sentencetoDisambiguateTxtAr.getText();
        setSentenceDisambiguator();
        sentenceDisambiguator.setSentence(sentenceToDisiambiguate);
        sentenceDisambiguator.execute(disambiguationWindowSize);
        disambiguationResult = "";
        disambiguationResult += sentenceDisambiguator.resultToString();
        disambiguateResultTxtAr.setText(disambiguationResult);

    }//GEN-LAST:event_disAmbiguateBtnActionPerformed

    private void calculateSimilarityBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculateSimilarityBtnActionPerformed
        // TODO add your handling code here:
        firstSentence = firstSentenceTxtAr.getText();
        secondSentence = secondSentneceTxtAr.getText();
        initSimilarity();
        omAlgo = new BipertiteGraphOptimalMatchingAlgorithm(null);
        HybridMatchingMeasure newMeasure = new HybridMatchingMeasure(firstSentence, secondSentence, null, omAlgo, sentenceDisambiguator, conceptMeasure);
        MouhibiMatchingMeasure mouhibiMeasure = new MouhibiMatchingMeasure(firstSentence, secondSentence, null, omAlgo, sentenceDisambiguator, conceptMeasure);
        LiVectorMeasure vectorMeasure = new LiVectorMeasure(firstSentence, secondSentence, sentenceDisambiguator, conceptMeasure);

        mouhibiMeasure.execute();
        newMeasure.execute();
        vectorMeasure.execute();
        // senSimAlgo.execute();
        double sim1, sim2, sim3;
        if (withStructure) {
            wordOrderAlgo.execute();
            sim2 = (1 - structureWeight) * newMeasure.getSimilarity() + structureWeight * wordOrderAlgo.getSimilarity();
            sim1 = (1 - structureWeight) * mouhibiMeasure.getSimilarity() + structureWeight * wordOrderAlgo.getSimilarity();
            sim3 = (1 - structureWeight) * vectorMeasure.getSimilarity() + structureWeight * wordOrderAlgo.getSimilarity();
        } else {
            sim1 = mouhibiMeasure.getSimilarity();
            sim2 = newMeasure.getSimilarity();
            sim3 = vectorMeasure.getSimilarity();
        }
        String newMethodResult = "";
        String mouhibiMeasureResult = "";
        String vectorMeasureResult = "";
        newMethodResult += "similarity = " + sim2 + "\n____________\n";
        mouhibiMeasureResult += "similarity = " + sim1 + "\n____________\n";
        vectorMeasureResult += "similarity = " + sim3 + "\n____________\n";
        newMethodResult += "matchings \n____________\n";
        mouhibiMeasureResult += "matchings \n____________\n";
        newMethodResult += newMeasure.getMatching();
        mouhibiMeasureResult += mouhibiMeasure.getMatching();
        similarityResult = "";
        mouhibiTxtAr.setText(mouhibiMeasureResult);
        newMethodTxtAr.setText(newMethodResult);
        vectorMeasureTxtAr.setText(vectorMeasureResult);
    }//GEN-LAST:event_calculateSimilarityBtnActionPerformed

    private void nonNounsSimilarityMeasureComBxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nonNounsSimilarityMeasureComBxActionPerformed
        // TODO add your handling code here:
        int index = nonNounsSimilarityMeasureComBx.getSelectedIndex();
        defaultMeasure = (String) nonNounsSimilarityMeasureComBx.getItemAt(index);
        DefaultConceptRelatednessMeasureFactory.setType(defaultMeasure);
    }//GEN-LAST:event_nonNounsSimilarityMeasureComBxActionPerformed

    private void informationContentComBxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_informationContentComBxActionPerformed
        // TODO add your handling code here:
        int index = informationContentComBx.getSelectedIndex();
        similarityconceptRelatednessType = (String) informationContentComBx.getItemAt(index);
        setConceptMeasure();
    }//GEN-LAST:event_informationContentComBxActionPerformed

    private void pathBasedComBxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pathBasedComBxActionPerformed
        // TODO add your handling code here:
        int index = pathBasedComBx.getSelectedIndex();
        similarityconceptRelatednessType = (String) pathBasedComBx.getItemAt(index);
        setConceptMeasure();
    }//GEN-LAST:event_pathBasedComBxActionPerformed

    private void similarityDisambiguationAlgorithmComBxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_similarityDisambiguationAlgorithmComBxActionPerformed
        // TODO add your handling code here:
        int index = similarityDisambiguationAlgorithmComBx.getSelectedIndex();
        sentenceSimilarityWordDisambiguatorType = (String) similarityDisambiguationAlgorithmComBx.getItemAt(index);
        setSimilarityDisambiguator();
    }//GEN-LAST:event_similarityDisambiguationAlgorithmComBxActionPerformed

    private void withStructureRadioBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_withStructureRadioBtnActionPerformed
        // TODO add your handling code here:
        if (withStructureRadioBtn.isSelected()) {
            withStructure = true;
        } else {
            withStructure = false;
        }
    }//GEN-LAST:event_withStructureRadioBtnActionPerformed

    private void informationContentFunctionComBxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_informationContentFunctionComBxActionPerformed
        // TODO add your handling code here:
        int index = informationContentFunctionComBx.getSelectedIndex();
        informationContentFunction = (String) informationContentFunctionComBx.getItemAt(index);
        InformationContentCalculatorFactory.setType(informationContentFunction);
    }//GEN-LAST:event_informationContentFunctionComBxActionPerformed

    private void pathBasedComBx1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pathBasedComBx1ActionPerformed
        // TODO add your handling code here:
        int index = pathBasedComBx1.getSelectedIndex();
        disambiguationConceptMeasureType = (String) pathBasedComBx1.getItemAt(index);

    }//GEN-LAST:event_pathBasedComBx1ActionPerformed

    private void informationContentComBx1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_informationContentComBx1ActionPerformed
        // TODO add your handling code here:
        int index = informationContentComBx1.getSelectedIndex();
        disambiguationConceptMeasureType = (String) informationContentComBx1.getItemAt(index);
    }//GEN-LAST:event_informationContentComBx1ActionPerformed

    private void glossBasedMeasureComBxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_glossBasedMeasureComBxActionPerformed
        // TODO add your handling code here:
        int index = glossBasedMeasureComBx.getSelectedIndex();
        disambiguationConceptMeasureType = (String) glossBasedMeasureComBx.getItemAt(index);
    }//GEN-LAST:event_glossBasedMeasureComBxActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SimilarityForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SimilarityForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SimilarityForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SimilarityForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SimilarityForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.JButton calculateSimilarityBtn;
    private javax.swing.JButton disAmbiguateBtn;
    private javax.swing.JTextArea disambiguateResultTxtAr;
    private javax.swing.JTextArea firstSentenceTxtAr;
    private javax.swing.JComboBox glossBasedMeasureComBx;
    private javax.swing.JComboBox informationContentComBx;
    private javax.swing.JComboBox informationContentComBx1;
    private javax.swing.JComboBox informationContentFunctionComBx;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel jlabel7;
    private javax.swing.JTextArea mouhibiTxtAr;
    private javax.swing.JTextArea newMethodTxtAr;
    private javax.swing.JComboBox nonNounsSimilarityMeasureComBx;
    private javax.swing.JComboBox pathBasedComBx;
    private javax.swing.JComboBox pathBasedComBx1;
    private javax.swing.JTextArea secondSentneceTxtAr;
    private javax.swing.JTextArea sentencetoDisambiguateTxtAr;
    private javax.swing.JComboBox similarityDisambiguationAlgorithmComBx;
    private javax.swing.JTextArea vectorMeasureTxtAr;
    private javax.swing.JRadioButton withStructureRadioBtn;
    // End of variables declaration//GEN-END:variables
}
