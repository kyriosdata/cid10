package com.github.kyriosdata.cid10.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.util.BytesRef;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TiposDeBuscasTest {

    private final IndiceCid indiceCid = new IndiceCid(new ByteBuffersDirectory(), new Cid10Analyzer());

    @Test
    public void encontraComEspaco() {
        List<Document> documents = indiceCid.searchIndex("descricao", "el tor");
        assertEquals("A001", documents.get(0).get("codigo"));
    }

    @Test
    public void independenteDoPrimeiroCaractere() {
        Term term = new Term("descricao", "?aratifoide");
        Query query = new WildcardQuery(term);
        List<Document> novos = indiceCid.searchIndex(query);
        assertEquals(6, novos.size());
    }

    @Test
    public void independenteDasDuasPrimeirasLetras() {
        Term term = new Term("descricao", "??ratifoide");
        Query query = new WildcardQuery(term);
        List<Document> novos = indiceCid.searchIndex(query);
        assertEquals(6, novos.size());
    }

    @Test
    public void encontraIndependenDoUltimoCaractere() {
        Term term = new Term("descricao", "paratifoid?");
        Query query = new WildcardQuery(term);
        List<Document> novos = indiceCid.searchIndex(query);
        assertEquals(6, novos.size());
    }

    @Test
    public void inicioFimQuaisquer() {
        Term term = new Term("descricao", "?aratifoid?");
        Query query = new WildcardQuery(term);
        List<Document> novos = indiceCid.searchIndex(query);
        assertEquals(6, novos.size());
    }

    @Test
    public void incluiPlurais() {
        Term term = new Term("descricao", "*aratifoid*");
        Query query = new WildcardQuery(term);
        List<Document> novos = indiceCid.searchIndex(query);
        assertEquals(8, novos.size());
    }

    @Test
    public void givenBooleanQueryWhenFetchedDocumentThenCorrect() {
        List<Document> documents = indiceCid.searchIndex("descricao", "dependencia && respirado?");
        Assert.assertEquals(1, documents.size());
    }

    @Test
    public void givenPhraseQueryWhenFetchedDocumentThenCorrect() {
        Query query = new PhraseQuery(1, "descricao", new BytesRef("dependencia"), new BytesRef("respirador"));
        List<Document> documents = indiceCid.searchIndex(query);

        Assert.assertEquals(1, documents.size());
    }
}