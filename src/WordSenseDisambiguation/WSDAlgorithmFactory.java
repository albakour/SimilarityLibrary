/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WordSenseDisambiguation;

import WordSenseDisambiguation.*;

/**
 *
 * @author sobhy
 */
public class WSDAlgorithmFactory {

    public static WordSenseDisambiguationAlgorithm produceObject(String type) {
        WordSenseDisambiguationAlgorithm object;
        if (type.equals("lesk")) {
            object = new OrginalLeskAlgorithm();
            return object;
        }
        if (type.equals("lesk-e")) {
            object = new ExtendedLeskAlgorithm();
            return object;
        }
        // default path
        object = new OrginalLeskAlgorithm();
        return object;
    }
}
