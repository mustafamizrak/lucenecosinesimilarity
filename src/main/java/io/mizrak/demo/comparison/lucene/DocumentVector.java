package io.mizrak.demo.comparison.lucene;

import java.util.Map;
import org.apache.commons.math3.linear.OpenMapRealVector;
import org.apache.commons.math3.linear.RealVector;

public class DocumentVector {

    public Map terms;
    public RealVector vector;

    public DocumentVector(Map terms) {
        this.terms = terms;
        this.vector = new OpenMapRealVector(terms.size());
    }

    public void setEntry(String term, int freq) {
        if (terms.containsKey(term)) {
            vector.setEntry((int) terms.get(term), (double) freq);
        }
    }

    public void normalize() {
        double sum = vector.getL1Norm();
        vector = vector.mapDivide(sum);
    }
}
