package io.mizrak.demo.comparison.lucene;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.util.BytesRef;

public class VectorGenerator {

    private final DocumentVector[] documentVector;
    private final Integer totalNoOfDocumentInIndex;
    private final IndexReader indexReader;
    private Map allterms;

    public VectorGenerator() throws IOException {
        allterms = new HashMap<>();
        indexReader = IndexOpener.GetIndexReader();
        totalNoOfDocumentInIndex = IndexOpener.TotalDocumentInIndex();
        documentVector = new DocumentVector[totalNoOfDocumentInIndex];
    }

    public void GetAllTerms() throws IOException {
        AllTerms allTerms = new AllTerms();
        allTerms.initAllTerms();
        allterms = allTerms.getAllTerms();
    }

    public DocumentVector[] GetDocumentVectors() throws IOException {
        for (int documentId = 0; documentId < totalNoOfDocumentInIndex; documentId++) {
            Terms terms = indexReader.getTermVector(documentId, Configuration.FIELD_CONTENT);
            TermsEnum termsEnum = terms.iterator();
            BytesRef text = null;
            documentVector[documentId] = new DocumentVector(allterms);
            
            while ((text = termsEnum.next()) != null) {
                String term = text.utf8ToString();
                int freq = (int) termsEnum.totalTermFreq();
                documentVector[documentId].setEntry(term, freq);
            }
            
            documentVector[documentId].normalize();
        }
        
        indexReader.close();
        return documentVector;
    }
}
