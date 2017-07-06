package io.mizrak.demo.comparison.lucene;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.util.BytesRef;

public class AllTerms {

    private final Map allTerms;
    Integer totalNoOfDocumentInIndex;
    IndexReader indexReader;

    public AllTerms() throws IOException {
        allTerms = new HashMap<>();
        indexReader = IndexOpener.GetIndexReader();
        totalNoOfDocumentInIndex = IndexOpener.TotalDocumentInIndex();
    }

    public void initAllTerms() throws IOException {
        int position = 0;
        
        for (int docId = 0; docId < totalNoOfDocumentInIndex; docId++) {
            Terms terms = indexReader.getTermVector(docId, Configuration.FIELD_CONTENT);
            TermsEnum termsEnum = terms.iterator();
            BytesRef text = null;
            while ((text = termsEnum.next()) != null) {
                allTerms.put(text.utf8ToString(), position++);
            }
        }

        //Update postition
        position = 0;
        for (Object set : allTerms.entrySet()) {
            Entry<String, Integer> entry = (Entry<String, Integer>) set;
            System.out.println(entry.getKey());
            entry.setValue(position++);
        }
    }

    public Map<String, Integer> getAllTerms() {
        return allTerms;
    }
}
