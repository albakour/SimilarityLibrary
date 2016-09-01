/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentenceSimilarity;

import java.util.ArrayList;

/**
 *
 * @author sobhy
 */
public class Vector {

    ArrayList<Double> projections;

    public Vector(ArrayList<Double> projections) {
        this.projections = projections;
    }

    /**
     *
     * @return the euclid length of the vector
     * @throws DimensionsDoNotMatchException
     */
    public double euclidNorm() throws DimensionsDoNotMatchException {
        double res = this.scalarProduct(this);
        res = Math.sqrt(res);
        return res;

    }

    /**
     *
     * @param v considered vector to do production with
     * @return the standard scalar product between this vector and v
     * @throws DimensionsDoNotMatchException
     */
    public double scalarProduct(Vector v) throws DimensionsDoNotMatchException {
        if (v.size() != this.size()) {
            throw new DimensionsDoNotMatchException();

        }
        double res = 0;
        for (int i = 0; i < this.size(); i++) {
            res += this.get(i) * v.get(i);
        }
        return res;

    }

    /**
     *
     * @param v
     * @return the cosine of the angle between this vector and v
     * @throws DimensionsDoNotMatchException
     */
    public double cos(Vector v) throws DimensionsDoNotMatchException {
        double norm1, norm2, scalar;
        norm1 = this.euclidNorm();
        norm2 = v.euclidNorm();
        scalar = this.scalarProduct(v);
        return scalar / (norm1 * norm2);

    }

    /**
     *
     * @param v
     * @return subtraction result as vector
     * @throws DimensionsDoNotMatchException
     */
    public Vector subtract(Vector v) throws DimensionsDoNotMatchException {
        Vector result;
        ArrayList<Double> projs = new ArrayList<>();
        for (int i = 0; i < this.size(); i++) {
            projs.add(this.get(i) - v.get(i));
        }
        result = new Vector(projs);
        return result;
    }

    /**
     *
     * @param v
     * @return adding result as vector
     * @throws DimensionsDoNotMatchException
     */
    public Vector add(Vector v) throws DimensionsDoNotMatchException {
        Vector result;
        ArrayList<Double> projs = new ArrayList<>();
        for (int i = 0; i < this.size(); i++) {
            projs.add(this.get(i) + v.get(i));
        }
        result = new Vector(projs);
        return result;
    }

    /**
     *
     * @return vector with opposite entries
     */
    public Vector opposite() {
        Vector result;
        ArrayList<Double> projs = new ArrayList<>();
        for (int i = 0; i < this.size(); i++) {
            projs.add((-1) * this.get(i));
        }
        result = new Vector(projs);
        return result;
    }

    public double get(int i) {
        return projections.get(i);
    }

    public int size() {
        return projections.size();
    }

}
