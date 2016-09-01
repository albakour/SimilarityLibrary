/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PosTagging;

/**
 *
 * @author sobhy
 */
public class PostHandlerFactory {

    private static PostHandler currentObject;

    /**
     *
     * @param type type of post object
     */
    public static void setType(String type) {
        if (type.equals("stanford")) {
            currentObject = new StanfordPostHandler();
        }
        // default tagger
        currentObject = new StanfordPostHandler();
    }

    /**
     *
     * @return the current post object
     */
    public static PostHandler produceObject() {
        return currentObject;
    }
}
