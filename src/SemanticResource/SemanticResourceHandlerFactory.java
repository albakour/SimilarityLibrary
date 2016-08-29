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

    /**
     *
     * @param type
     * @param lcsAlgorithmName
     * @param sourcePath
     * @return
     */
    public static SemanticResourceHandler produceObject(String type,String lcsAlgorithmName,String sourcePath) {
        if (type.equals("wordnet")) {
            WordNetHandler.setDictionaryPath(sourcePath);
            WordNetHandler.setFindLcsAlgorithm(lcsAlgorithmName);
            return WordNetHandler.getInstance();
        }
        return null;
    }
    

}
