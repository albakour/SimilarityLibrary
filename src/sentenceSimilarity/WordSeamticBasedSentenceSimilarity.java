///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package sentenceSimilarity;
//
//import ConceptRelatedness.ConceptsRelatednessAlgorithm;
//import WordSenseDisambiguation.SentenceSenseDisambiguator;
//import WordSenseDisambiguation.Word;
//import WordSenseDisambiguation.WordSenseDisambiguationAlgorithm;
//
///**
// *
// * @author sobhy
// * @param <T> the word sense algorithm that the sentence Disambiguator will use
// * @param <R> the Similarity measure that the sentence similarity will use in
// * calculating the similarity
// */
//public abstract class WordSeamticBasedSentenceSimilarity<T extends WordSenseDisambiguationAlgorithm, R extends ConceptsRelatednessAlgorithm> extends SentenceSimilarityAlgorithm {
////
////    protected SentenceSenseDisambiguator<T> sentenceSenseisambiguator;
////    protected R conceptRelatednessAlgorithm;
////
////    public WordSeamticBasedSentenceSimilarity(String sentence1, String sentence2, SentenceSenseDisambiguator<T> disambiguator, R conceptRelatednessAlgorithm) {
////        super(sentence1, sentence2);
////        this.sentenceSenseisambiguator = disambiguator;
////        this.conceptRelatednessAlgorithm = conceptRelatednessAlgorithm;
////        this.sentenceWords1=getSenteceWords(sentence1);
////        this.sentenceWords2=getSenteceWords(sentence2);
////    }
////
////    @Override
////    public abstract void execute();
////
////    private Word[] getSenteceWords(String sentence) {
////        this.sentenceSenseisambiguator.setSentence(sentence);
////        this.sentenceSenseisambiguator.execute(windowSize);
////        return this.sentenceSenseisambiguator.getDisambiguatedWords();
////    }
//
//}
