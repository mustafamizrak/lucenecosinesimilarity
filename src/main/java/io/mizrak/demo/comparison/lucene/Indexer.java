package io.mizrak.demo.comparison.lucene;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;

public class Indexer {

    private final File sourceDirectory;
    private final File indexDirectory;
    private final String fieldName;

    public Indexer() {
        this.sourceDirectory = new File(Configuration.SOURCE_DIRECTORY_TO_INDEX);
        this.indexDirectory = new File(Configuration.INDEX_DIRECTORY);
        fieldName = Configuration.FIELD_CONTENT;
    }

    /**
     * Method to create Lucene Index Keep in mind that always index text value
     * to Lucene for calculating Cosine Similarity. You have to generate tokens,
     * terms and their frequencies and store them in the Lucene Index.
     *
     */
    public void index() throws CorruptIndexException,
            LockObtainFailedException, IOException {
        Directory directory = FSDirectory.open(indexDirectory.toPath());

        // using stop words
        Analyzer analyzer = new StandardAnalyzer(StandardAnalyzer.STOP_WORDS_SET);
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);

        if (indexDirectory.exists()) {
            indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        } else {
            // Add new documents to an existing index:
            indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        }

        try (IndexWriter writer = new IndexWriter(directory, indexWriterConfig)) {
            for (File file : sourceDirectory.listFiles()) {
                Document document = new Document();

                FieldType fieldType = new FieldType();
                fieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
                fieldType.setStored(true);
                fieldType.setStoreTermVectors(true);
                fieldType.setStoreTermVectorOffsets(true);
                fieldType.setStoreTermVectorPayloads(true);
                fieldType.setStoreTermVectorPositions(true);
                fieldType.setTokenized(true);

                Field contentField = new Field(fieldName, getAllText(file), fieldType);

                document.add(contentField);
                writer.addDocument(document);
            }
        }
    }

    /**
     * Method to get all the texts of the text file. Lucene cannot create the
     * term vetors and tokens for reader class. You have to index its text
     * values to the index. It would be more better if this was in another
     * class. I am lazy you know all.
     *
     */
    public String getAllText(File file) throws FileNotFoundException, IOException {
        StringBuilder textFileContent = new StringBuilder();

        // Charset: ISO_8859_1 
        for (String line : Files.readAllLines(Paths.get(file.getAbsolutePath()), StandardCharsets.ISO_8859_1)) {
            textFileContent.append(line);
        }
        return textFileContent.toString();
    }
}
