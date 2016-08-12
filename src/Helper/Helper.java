/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

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
        return words;
    }
}
