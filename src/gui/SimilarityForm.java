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
    String defaultMeasure = "extended gloss";
    SemanticResourceHandler semanticResource;
    StanfordPostHandler posTagger;
    WordSenseDisambiguationAlgorithm similarityWordDisambiguator;
    SentenceSenseDisambiguator similaritySentenceDisambiguator;
    ConceptsRelatednessAlgorithm conceptMeasure;
    OptimalGraphMatchingAlgorithm omAlgo;
    SentenceSimilarityAlgorithm senSimAlgo;
    String similarityResult;
    String sentenceToDisiambiguate;
    SentenceSenseDisambiguator sentenceDisambiguator;
    WordSenseDisambiguationAlgorithm wordSenseDisambiguator;
    int disambiguationWindowSize = 10;
    String disambiguationResult;
    WordOrderAlgorithm wordOrderAlgo;
    double structureWeight = 0.15;

    int sentenceSimilarityType;
    int sentenceSimilarityWordDisambiguatorType;
    int similarityconceptRelatednessType;
    boolean withStructure = false;
    boolean isPath = true;

    public SimilarityForm() {
        initComponents();
        init();
        initSimilarity();
        initDisambiguation();

    }

    private void init() {
        //System.out.println("collecting WordNet statistics ... ");
        semanticResource = SemanticResourceHandlerFactory.produceObject("wordnet", "", dictionaryPath);
        // WordNetHandler semanticResource2 = (WordNetHandler) SemanticResourceHandlerFactory.produceObject("wordnet", dictionaryPath);
        InformationContentCalculatorFactory.setSemanticResource(semanticResource);
        DefaultConceptRelatednessMeasureFactory.produceObject(defaultMeasure, semanticResource);
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
        setSimilarityAlgorithm();

//        similarityWordDisambiguator = new ExtendedLeskAlgorithm(null, null, semanticResource);
//        similarityWordDisambiguator.setWindowSize(SimilarityWindowSize);
//        similaritySentenceDisambiguator = new SentenceSenseDisambiguator("", similarityWordDisambiguator, semanticResource, posTagger);
//        conceptMeasure = new WuPalmerMeasure(null, null, semanticResource);
//        omAlgo = new BipertiteGraphOptimalMatchingAlgorithm(null);
    }

    private void setSimilarityDisambiguator() {
        if (sentenceSimilarityWordDisambiguatorType == 0) {
            similarityWordDisambiguator = new ExtendedLeskAlgorithm(null, null, semanticResource);
        } else {
            similarityWordDisambiguator = new OrginalLeskAlgorithm(null, null, semanticResource);
        }
        similarityWordDisambiguator.setWindowSize(SimilarityWindowSize);
        similaritySentenceDisambiguator = new SentenceSenseDisambiguator("", similarityWordDisambiguator, semanticResource, posTagger);
    }

    private void setSimilarityAlgorithm() {
        if (sentenceSimilarityType == 0) {
            omAlgo = new BipertiteGraphOptimalMatchingAlgorithm(null);
            senSimAlgo = new OptimalGraphMatchingBasedSimilarityAlgorithm(firstSentence, secondSentence, omAlgo, similaritySentenceDisambiguator, conceptMeasure);
        } else {
            senSimAlgo = new LiVectorBasedSentenceSimilarityAlgorithm(firstSentence, secondSentence, similaritySentenceDisambiguator, conceptMeasure);
        }
        wordOrderAlgo = new WordOrderAlgorithm(firstSentence, secondSentence, similaritySentenceDisambiguator, conceptMeasure);
    }

    private void setConceptMeasure() {
        if (isPath) {
            if (similarityconceptRelatednessType == 0) {
                conceptMeasure = new PathMeasure(null, null, semanticResource);
                return;
            }
            if (similarityconceptRelatednessType == 1) {
                double alpha, beta;
                alpha = 0.2;
                beta = 0.6;
                conceptMeasure = new LiMeasure(alpha, beta, null, null, semanticResource);
                return;
            }
            if (similarityconceptRelatednessType == 2) {
                conceptMeasure = new WuPalmerMeasure(null, null, semanticResource);
                return;
            }
            if (similarityconceptRelatednessType == 3) {
                conceptMeasure = new LeakcockChodorowMeasure(null, null, semanticResource);
                return;
            }
            if (similarityconceptRelatednessType == 4) {
                double alpha, beta;
                alpha = 0.5;
                beta = 0.3;
                conceptMeasure = new JiangCornathMeasure(null, null, alpha, beta, semanticResource);
            }

        } else {
            if (similarityconceptRelatednessType == 0) {
                conceptMeasure = new ReniskMeasure(null, null, semanticResource);
                return;
            }
            if (similarityconceptRelatednessType == 1) {
//                double alpha, beta;
//                alpha = 0.2;
//                beta = 0.6;
                conceptMeasure = new LinMeasure(null, null, semanticResource);
                return;
            }
            if (similarityconceptRelatednessType == 2) {
                conceptMeasure = new JiangMeasure(null, null, semanticResource);
            }

        }

    }

    private void initDisambiguation() {
        wordSenseDisambiguator = new ExtendedLeskAlgorithm(null, null, semanticResource);
        wordSenseDisambiguator.setWindowSize(disambiguationWindowSize);
        sentenceDisambiguator = new SentenceSenseDisambiguator("", wordSenseDisambiguator, semanticResource, posTagger);
        sentenceToDisiambiguate = "pure hearts can see the beauty of the nature";
        sentencetoDisambiguateTxtAr.setText(sentenceToDisiambiguate);

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
        similarityResultTxtAr = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        calculateSimilarityBtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        sentencetoDisambiguateTxtAr = new javax.swing.JTextArea();
        disAmbiguateBtn = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        disambiguateResultTxtAr = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        informationContentComBx1 = new javax.swing.JComboBox();
        pathBasedComBx1 = new javax.swing.JComboBox();
        jRadioButton3 = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        sentenceSimilarityAlgorithmComBx = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        withStructureRadioBtn = new javax.swing.JRadioButton();
        similarityDisambiguationAlgorithmComBx = new javax.swing.JComboBox();
        jlabel7 = new javax.swing.JLabel();
        pathBasedComBx = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        pathRadioBtn = new javax.swing.JRadioButton();
        informationContentComBx = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        firstSentenceTxtAr.setColumns(20);
        firstSentenceTxtAr.setRows(5);
        jScrollPane1.setViewportView(firstSentenceTxtAr);

        jLabel1.setText("First Sentence");

        secondSentneceTxtAr.setColumns(20);
        secondSentneceTxtAr.setRows(5);
        jScrollPane2.setViewportView(secondSentneceTxtAr);

        similarityResultTxtAr.setColumns(20);
        similarityResultTxtAr.setRows(5);
        jScrollPane3.setViewportView(similarityResultTxtAr);

        jLabel2.setText("Second Sentence");

        calculateSimilarityBtn.setText("Calculat Similarity");
        calculateSimilarityBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateSimilarityBtnActionPerformed(evt);
            }
        });

        jLabel3.setText("Result");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel3)
                                .addGap(131, 131, 131)
                                .addComponent(calculateSimilarityBtn)))
                        .addGap(0, 267, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(calculateSimilarityBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane4)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel4))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel5)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(176, 176, 176)
                .addComponent(disAmbiguateBtn)
                .addContainerGap(262, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(disAmbiguateBtn)
                .addGap(8, 8, 8)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Sentence Sense Disambiguation", jPanel2);

        informationContentComBx1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Resnik", "Lin", "Jiang" }));

        pathBasedComBx1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Path", "Li", "Wu", "Leakcock", "Jiang Conrath" }));

        jRadioButton3.setText("path based concept relatedness algorithm ?");

        jLabel9.setText("path based");

        jLabel10.setText("information content ");

        jLabel11.setText("Concept Relatedness Algorithm");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(informationContentComBx1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton3)
                    .addComponent(jLabel11)
                    .addComponent(jLabel9)
                    .addComponent(pathBasedComBx1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addContainerGap(320, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel11)
                .addGap(42, 42, 42)
                .addComponent(jRadioButton3)
                .addGap(40, 40, 40)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(pathBasedComBx1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(140, 140, 140)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(informationContentComBx1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(102, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Sentense Sense Disambiguation ", jPanel4);

        sentenceSimilarityAlgorithmComBx.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Optimal Graph Matching Measure", "Li Vector Based Measure" }));
        sentenceSimilarityAlgorithmComBx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sentenceSimilarityAlgorithmComBxActionPerformed(evt);
            }
        });

        jLabel6.setText("Sentnece Similarity Algorithm");

        withStructureRadioBtn.setText("with structure similarity?");
        withStructureRadioBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                withStructureRadioBtnActionPerformed(evt);
            }
        });

        similarityDisambiguationAlgorithmComBx.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Extended Lesk", "Original Lesk" }));
        similarityDisambiguationAlgorithmComBx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                similarityDisambiguationAlgorithmComBxActionPerformed(evt);
            }
        });

        jlabel7.setText("Disambiguation Algorithm");

        pathBasedComBx.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Path", "Li", "Wu", "Leakcock", "Jiang Conrath" }));
        pathBasedComBx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pathBasedComBxActionPerformed(evt);
            }
        });

        jLabel7.setText("Path Based");

        jLabel8.setText("InformationContent Based");

        pathRadioBtn.setText("path based concept relatedness algorithm?");
        pathRadioBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pathRadioBtnActionPerformed(evt);
            }
        });

        informationContentComBx.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Resnik", "Lin", "Jiang" }));
        informationContentComBx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                informationContentComBxActionPerformed(evt);
            }
        });

        jLabel12.setText("Concept Relatedness Algorithm for Sentece Similarity");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jlabel7)
                    .addComponent(similarityDisambiguationAlgorithmComBx, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(pathRadioBtn)
                    .addComponent(sentenceSimilarityAlgorithmComBx, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pathBasedComBx, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(83, 83, 83)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(informationContentComBx, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(withStructureRadioBtn))
                .addContainerGap(239, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(sentenceSimilarityAlgorithmComBx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(withStructureRadioBtn)
                .addGap(29, 29, 29)
                .addComponent(jlabel7)
                .addGap(18, 18, 18)
                .addComponent(similarityDisambiguationAlgorithmComBx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(pathRadioBtn)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pathBasedComBx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(informationContentComBx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(87, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Senetence Similarity", jPanel3);

        jTabbedPane1.addTab("Configuration", jTabbedPane2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void disAmbiguateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disAmbiguateBtnActionPerformed
        // TODO add your handling code here:
        sentenceToDisiambiguate = sentencetoDisambiguateTxtAr.getText();
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
        senSimAlgo.execute();
        double sim;
        if (withStructure) {
            wordOrderAlgo.execute();
            sim = (1 - structureWeight) * senSimAlgo.getSimilarity() + structureWeight * wordOrderAlgo.getSimilarity();
        } else {
            sim = senSimAlgo.getSimilarity();
        }
        similarityResult = "";
        similarityResult += "similarity = " + sim + "\n____________\n";
        if (sentenceSimilarityType == 0) {
            similarityResult += "matchings \n____________\n";
            similarityResult += ((OptimalGraphMatchingBasedSimilarityAlgorithm) senSimAlgo).getMatching();
        }
        similarityResultTxtAr.setText(similarityResult);
    }//GEN-LAST:event_calculateSimilarityBtnActionPerformed

    private void sentenceSimilarityAlgorithmComBxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sentenceSimilarityAlgorithmComBxActionPerformed
        // TODO add your handling code here:
        int index = sentenceSimilarityAlgorithmComBx.getSelectedIndex();
        // String type=(String)sentenceSimilarityAlgorithmComBx.getItemAt(index);
        sentenceSimilarityType = index;
    }//GEN-LAST:event_sentenceSimilarityAlgorithmComBxActionPerformed

    private void similarityDisambiguationAlgorithmComBxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_similarityDisambiguationAlgorithmComBxActionPerformed
        // TODO add your handling code here:
        int index = similarityDisambiguationAlgorithmComBx.getSelectedIndex();
        sentenceSimilarityWordDisambiguatorType = index;

    }//GEN-LAST:event_similarityDisambiguationAlgorithmComBxActionPerformed

    private void pathRadioBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pathRadioBtnActionPerformed
        // TODO add your handling code here:
        if (pathRadioBtn.isSelected()) {
            isPath = true;
        } else {
            isPath = false;
        }
    }//GEN-LAST:event_pathRadioBtnActionPerformed

    private void withStructureRadioBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_withStructureRadioBtnActionPerformed
        // TODO add your handling code here:
        if (withStructureRadioBtn.isSelected()) {
            withStructure = true;
        } else {
            withStructure = false;
        }
    }//GEN-LAST:event_withStructureRadioBtnActionPerformed

    private void pathBasedComBxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pathBasedComBxActionPerformed
        // TODO add your handling code here:
        int index = pathBasedComBx.getSelectedIndex();
        similarityconceptRelatednessType = index;
    }//GEN-LAST:event_pathBasedComBxActionPerformed

    private void informationContentComBxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_informationContentComBxActionPerformed
        // TODO add your handling code here:
        int index = informationContentComBx.getSelectedIndex();
        similarityconceptRelatednessType = index;
    }//GEN-LAST:event_informationContentComBxActionPerformed

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
    private javax.swing.JComboBox informationContentComBx;
    private javax.swing.JComboBox informationContentComBx1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel jlabel7;
    private javax.swing.JComboBox pathBasedComBx;
    private javax.swing.JComboBox pathBasedComBx1;
    private javax.swing.JRadioButton pathRadioBtn;
    private javax.swing.JTextArea secondSentneceTxtAr;
    private javax.swing.JComboBox sentenceSimilarityAlgorithmComBx;
    private javax.swing.JTextArea sentencetoDisambiguateTxtAr;
    private javax.swing.JComboBox similarityDisambiguationAlgorithmComBx;
    private javax.swing.JTextArea similarityResultTxtAr;
    private javax.swing.JRadioButton withStructureRadioBtn;
    // End of variables declaration//GEN-END:variables
}
