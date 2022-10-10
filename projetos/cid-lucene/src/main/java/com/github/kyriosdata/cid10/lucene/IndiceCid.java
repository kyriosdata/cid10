package com.github.kyriosdata.cid10.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.kyriosdata.cid10.busca.Carregador;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.BytesRef;

/**
 * O índice segundo o Lucene é formado por um Document para
 * cada entrada da CID. Este Document tem dois campos (field):
 * (a) código, por exemplo "a250" e (b) descrição, por exemplo
 * "espirilose".
 */
public class IndiceCid {

    private Directory indice;
    private Analyzer analyzer;
    private List<String> codigos;

    public IndiceCid(Directory indice, Analyzer analyzer) {
        super();
        this.indice = indice;
        this.analyzer = analyzer;

        codigos = new Carregador().fromJar("/cid/codigos.csv");
        geraIndiceParaCid10();
    }

    public void geraIndiceParaCid10() {
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        try {
            IndexWriter writter = new IndexWriter(indice, indexWriterConfig);

            codigos.stream()
                    .map(e -> e.split(";"))
                    .forEach(t -> {
                        try {
                            writter.addDocument(createDocument(t[0], t[1], t[2]));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });

            writter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Cada documento é formado por um código e a semântica a ele atribuída.
     *
     * @param descricao Descrição da terminologia.
     * @param codigo    Código de uma terminologia.
     * @param sexo
     */
    public void acrescenteEntrada(String codigo, String sexo, String descricao) {

        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        try {
            IndexWriter writter = new IndexWriter(indice, indexWriterConfig);
            Document document = createDocument(codigo, sexo, descricao);

            writter.addDocument(document);
            writter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Document createDocument(String codigo, String sexo, String descricao) {
        Document document = new Document();
        document.add(new TextField("codigo", codigo, Field.Store.YES));
        document.add(new TextField("descricao", descricao, Field.Store.YES));
        document.add(new TextField("sexo", sexo, Field.Store.YES));
        document.add(new SortedDocValuesField("codigo", new BytesRef(codigo)));
        return document;
    }

    public List<Document> searchIndex(String inField, String queryString) {
        try {
            Query query = new QueryParser(inField, analyzer).parse(queryString);

            IndexReader indexReader = DirectoryReader.open(indice);
            IndexSearcher searcher = new IndexSearcher(indexReader);
            TopDocs topDocs = searcher.search(query, 10);
            List<Document> documents = new ArrayList<>();
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                documents.add(searcher.doc(scoreDoc.doc));
            }

            return documents;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;

    }

    public void deleteDocument(Term term) {
        try {
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
            IndexWriter writter = new IndexWriter(indice, indexWriterConfig);
            writter.deleteDocuments(term);
            writter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Document> searchIndex(Query query) {
        try {
            IndexReader indexReader = DirectoryReader.open(indice);
            IndexSearcher searcher = new IndexSearcher(indexReader);
            TopDocs topDocs = searcher.search(query, 10);
            List<Document> documents = new ArrayList<>();
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                documents.add(searcher.doc(scoreDoc.doc));
            }

            return documents;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<Document> searchIndex(Query query, Sort sort) {
        try {
            IndexReader indexReader = DirectoryReader.open(indice);
            IndexSearcher searcher = new IndexSearcher(indexReader);
            TopDocs topDocs = searcher.search(query, 10, sort);
            List<Document> documents = new ArrayList<>();
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                documents.add(searcher.doc(scoreDoc.doc));
            }

            return documents;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

}
