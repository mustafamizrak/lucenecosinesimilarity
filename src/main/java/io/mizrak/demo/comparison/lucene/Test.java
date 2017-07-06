package io.mizrak.demo.comparison.lucene;

import java.io.IOException;
import org.apache.lucene.store.LockObtainFailedException;

public class Test {

    public static void main(String[] args) throws LockObtainFailedException, IOException {
        getCosineSimilarity();
    }

    public static void getCosineSimilarity() throws LockObtainFailedException, IOException {

        new Indexer().index();
        VectorGenerator vectorGenerator = new VectorGenerator();
        vectorGenerator.GetAllTerms();

        // getting document vectors
        DocumentVector[] documentVectors = vectorGenerator.GetDocumentVectors();

        for (int referenceDocumentId = 0; referenceDocumentId < documentVectors.length; referenceDocumentId++) {
            for (int documentId = 0; documentId < documentVectors.length; documentId++) {
                double cosineSimilarity = CosineSimilarity.CosineSimilarity(documentVectors[referenceDocumentId], documentVectors[documentId]);
                System.out.println("Cosine Similarity Score between document " + referenceDocumentId + " and " + documentId + "  = " + cosineSimilarity);
            }
        }
    }
}
