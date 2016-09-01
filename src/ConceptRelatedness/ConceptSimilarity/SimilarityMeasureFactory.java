/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConceptRelatedness.ConceptSimilarity;

import ConceptRelatedness.ConceptSimilarity.InformationConctentMeasures.JiangMeasure;
import ConceptRelatedness.ConceptSimilarity.InformationConctentMeasures.LinMeasure;
import ConceptRelatedness.ConceptSimilarity.InformationConctentMeasures.ResnikkMeasure;
import ConceptRelatedness.ConceptSimilarity.PathMeasures.JiangCornathMeasure;
import ConceptRelatedness.ConceptSimilarity.PathMeasures.LeakcockChodorowMeasure;
import ConceptRelatedness.ConceptSimilarity.PathMeasures.LiMeasure;
import ConceptRelatedness.ConceptSimilarity.PathMeasures.PathMeasure;
import ConceptRelatedness.ConceptSimilarity.PathMeasures.WuPalmerMeasure;

/**
 *
 * @author sobhy
 */
public class SimilarityMeasureFactory {
    /**
     * responsible of producing relatedness measures
     * @param type the desired type to produce
     * @return  a concept relatedness measure
     */

    public static SimilarityAlgorithm produceObject(String type) {
        SimilarityAlgorithm object;
        if (type.equals("li")) {
            // experimantal optimal values
            double alpha, beta;
            alpha = 0.2;
            beta = 0.6;
            object = new LiMeasure(alpha, beta);
            return object;
        }
        if (type.equals("wu")) {
            object = new WuPalmerMeasure();
            return object;
        }
        if (type.equals("path")) {
            object = new PathMeasure();
            return object;
        }
        if (type.equals("leakcok")) {
            object = new LeakcockChodorowMeasure();
            return object;
        }
        if (type.equals("jiang conrath")) {
            double alpha, beta;
            alpha = 0.5;
            beta = 0.3;
            object = new JiangCornathMeasure(alpha, beta);
            return object;
        }
        if (type.equals("lin")) {
            object = new LinMeasure();
            return object;
        }
        if (type.equals("resnik")) {
            object = new ResnikkMeasure();
            return object;
        }
        if (type.equals("jiang")) {
            object = new JiangMeasure();
            return object;
        }
        object = new PathMeasure();
        return object;
    }

}
