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
public class StatisticsHelper {

    private double[] trueData;
    private double[] estimatedData;
    private double threshold;
    private double precision;
    private double recall;
    private double rejection;
    private double accuracy;
    private double correlation;
    private boolean isExecuted;

    public StatisticsHelper(double[] trueData, double[] estimatedData, double threshold) {
        this.trueData = trueData;
        this.estimatedData = estimatedData;
        this.threshold = threshold;
    }

    /**
     * calculate statistics
     */
    public void execute() {
        if (trueData.length != estimatedData.length) {
            return;
        }
        double totalEstimatedTrue = 0;
        double trueEstimatedTrue = 0;
        double actualTrue = 0;
        double trueEstimatedFalse = 0;
        double actualFalse = 0;
        double trueEstimated = 0;
        for (int i = 0; i < trueData.length; i++) {
            if (estimatedData[i] >= threshold) {
                totalEstimatedTrue++;
            }
            if (estimatedData[i] >= threshold && trueData[i] >= threshold) {
                trueEstimatedTrue++;
            }
            if (trueData[i] >= threshold) {
                actualTrue++;
            }
            if (estimatedData[i] < threshold && trueData[i] < threshold) {
                trueEstimatedFalse++;
            }
        }
        actualFalse = trueData.length - actualFalse;
        trueEstimated = trueEstimatedFalse + trueEstimatedTrue;
        this.precision = (trueEstimatedTrue) / (totalEstimatedTrue);
        this.recall = (trueEstimatedTrue) / (actualTrue);
        this.rejection = (trueEstimatedFalse) / (actualFalse);
        this.accuracy = trueEstimated / trueData.length;
        this.correlation = calculateCorrelation();
        this.isExecuted = true;

    }

    /**
     *
     * @return precision measure
     */
    public double precision() {
        if (isExecuted) {
            return this.precision;
        }
        return -1;
    }

    /**
     *
     * @return recall measure
     */

    public double recall() {
        if (isExecuted) {
            return this.recall;
        }
        return -1;
    }

    /**
     *
     * @return rejection measure
     */
    public double rejection() {
        if (isExecuted) {
            return this.rejection;
        }
        return -1;
    }

    /**
     * accuracy measure
     *
     * @return
     */
    public double accuracy() {
        if (isExecuted) {
            return this.accuracy;
        }
        return -1;
    }

    /**
     *
     * @param beta f order
     * @return f measure
     */
    public double fMeasure(double beta) {
        if (this.isExecuted) {
            double result = ((1 + beta * beta) * (precision * recall)) / ((beta * beta * precision) + recall);
            return result;
        }
        return -1;

    }

    /**
     *
     * @return the correlation value between the two samples
     */
    public double correlation() {
        if (this.isExecuted) {
            return this.correlation;
        }
        return -1;
    }

    public void setTrueData(double[] t) {
        this.trueData = t;
        this.isExecuted = false;
    }

    public void setEstimatedData(double[] e) {
        this.estimatedData = e;
        this.isExecuted = false;
    }

    public void setThreshold(double t) {
        this.threshold = t;
        this.isExecuted = false;
    }

    private double calculateCorrelation() {
        double[] x = this.trueData;
        double[] y = this.estimatedData;
        if (x.length != y.length) {
            return 0;
        }
        // get mean
        double xBar = 0, yBar = 0;
        for (int i = 0; i < x.length; i++) {
            xBar += x[i];
            yBar += y[i];
        }
        xBar /= x.length;
        yBar /= y.length;
        // get standard deviations 
        double sxy = 0, sx = 0, sy = 0;
        for (int i = 0; i < x.length; i++) {
            sxy += (x[i] - xBar) * (y[i] - yBar);
            sx += (x[i] - xBar) * (x[i] - xBar);
            sy += (y[i] - yBar) * (y[i] - yBar);
        }
        double result = sxy / Math.sqrt(sx * sy);
        return result;
    }
}
