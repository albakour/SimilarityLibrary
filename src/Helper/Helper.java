/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import WordSenseDisambiguation.Word;
import java.util.ArrayList;

/**
 *
 * @author sobhy
 */
public class Helper {

    public static String[] wordsToIgnore = {"a", "an", "and", "for", "in", "of", "or", "the", "than", "that", "this",
        "those", "to", "what", "when", "where", "which", "who", "with", "at", "any", "but", "so", "then"};

    public static boolean ignore(String word) {
        for (int i = 0; i < wordsToIgnore.length; i++) {
            if (wordsToIgnore[i].equals(word)) {
                return true;
            }
        }
        return false;
    }

    public static String[] getWordsFormSentence(String sentence) {
        // remove white spaces
        String[] words = sentence.split("\\s+");
        // remove punctuation
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].replaceAll("[^\\w]", "");
        }
        ArrayList<String> nonempty = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            if (!words[i].equals("")) {
                nonempty.add(words[i]);
            }
        }
        String[] result = new String[nonempty.size()];
        for (int i = 0; i < nonempty.size(); i++) {
            result[i] = nonempty.get(i);
        }
        return result;
    }
    /**
     * remove punctuation 
     * @param sentence
     * @return 
     */

    public static String prepare(String sentence) {
        String[] words = getWordsFormSentence(sentence);
        String result = "";
        for (int i = 0; i < words.length; i++) {
            if (i != words.length) {
                result += words[i] + " ";
            }
            else{
                result+=words[i];
            }
        }
        return result;
    }

    /**
     * to combine the two sentences in one and delete the repetitions
     *
     * @param sentenceWords1
     * @param sentenceWords2
     * @return
     */

    public static Word[] combineSentences(Word[] sentenceWords1, Word[] sentenceWords2) {
        // temp to store words without repetittions
        ArrayList<Word> temp = new ArrayList<>();
        //adding words from the first sentence
        for (int i = 0; i < sentenceWords1.length; i++) {
            if (!temp.contains(sentenceWords1[i])) {
                temp.add(sentenceWords1[i]);
            }
        }
        //adding words from the second sentence
        for (int i = 0; i < sentenceWords2.length; i++) {
            if (!temp.contains(sentenceWords2[i])) {
                temp.add(sentenceWords2[i]);
            }
        }
        // forming the result as an array
        Word[] result = new Word[temp.size()];
        for (int i = 0; i < temp.size(); i++) {
            result[i] = temp.get(i);
        }

        return result;

    }

    /**
     * utility function to test if a sentence contains a word
     *
     * @param arr
     * @param word
     * @return
     */
    public static int SentenceContainsWord(Word[] arr, Word word) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(word)) {
                return i;
            }
        }
        return -1;

    }

}
