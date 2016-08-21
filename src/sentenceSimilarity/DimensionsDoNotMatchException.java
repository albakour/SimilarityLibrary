/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentenceSimilarity;

/**
 *
 * @author sobhy
 */
public class DimensionsDoNotMatchException extends Exception {

    public DimensionsDoNotMatchException() {
        super("scalar product cant be done on two vectors from different spaces(the dimensions do not match)");
    }

}
