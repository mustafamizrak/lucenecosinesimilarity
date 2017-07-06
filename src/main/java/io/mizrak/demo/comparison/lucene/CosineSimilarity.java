package io.mizrak.demo.comparison.lucene;

import org.apache.commons.math3.exception.DimensionMismatchException;

public class CosineSimilarity {

    public static double CosineSimilarity(DocumentVector documentVector, DocumentVector referenceDocumentVector) {
        double cosinesimilarity;
        
        try {
            cosinesimilarity = (documentVector.vector.dotProduct(referenceDocumentVector.vector))
                    / (documentVector.vector.getNorm() * referenceDocumentVector.vector.getNorm());
        } catch (DimensionMismatchException e) {
            return 0.0;
        }
        
        return cosinesimilarity;
    }
}
