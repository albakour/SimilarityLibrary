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
public class FindLCSALgorithmFactory {

    public static FindLCSAlgorithm produceObject(String name) {
        if (name.equals("BottomUp")) {
            BottomUpFindLcsAlgorithm object = new BottomUpFindLcsAlgorithm();
            return object;
        }
        //default path
        BottomUpFindLcsAlgorithm object = new BottomUpFindLcsAlgorithm();
        return object;

    }

}
