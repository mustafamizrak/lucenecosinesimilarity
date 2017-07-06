package io.mizrak.demo.comparison.lucene;

import java.io.File;
import java.io.IOException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

public class IndexOpener {

    public static IndexReader GetIndexReader() throws IOException {
        return DirectoryReader.open(FSDirectory.open(new File(Configuration.INDEX_DIRECTORY).toPath()));
    }

    /**
     * Returns the total number of documents in the index
     * 
     */
    public static Integer TotalDocumentInIndex() throws IOException {
        IndexReader indexReader = GetIndexReader();
        Integer maxDoc = indexReader.maxDoc();
        indexReader.close();
        return maxDoc;
    }
}
