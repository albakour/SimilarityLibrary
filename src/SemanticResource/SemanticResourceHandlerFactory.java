/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SemanticResource;

/**
 *
 * @author sobhy
 */
public class SemanticResourceHandlerFactory {

    private static SemanticResourceHandler currentObject;

    public static SemanticResourceHandler setConfiguration(String type, String lcsAlgorithmName, String sourcePath) {
        if (type.equals("wordnet")) {
            WordNetHandler.setDictionaryPath(sourcePath);
            WordNetHandler.setFindLcsAlgorithm(lcsAlgorithmName);
            currentObject = WordNetHandler.getInstance();
            return currentObject;
        }
        return null;
    }
    public static SemanticResourceHandler produceObject(){
        return currentObject;
    }

}
