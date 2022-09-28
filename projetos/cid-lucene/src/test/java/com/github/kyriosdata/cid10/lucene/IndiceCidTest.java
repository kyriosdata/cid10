package com.github.kyriosdata.cid10.lucene;

import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.util.BytesRef;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class IndiceCidTest {

    @Test
    public void buscaPorCodigo() {
        IndiceCid indiceCid = new IndiceCid(new ByteBuffersDirectory(), new Cid10Analyzer());

        List<Document> documents = indiceCid.searchIndex("descricao", "Cólera");
        assertTrue(documents.size() > 0);
    }

    @Test
    public void givenBuscaComMinúsculaEncontrada() {
        IndiceCid indiceCid = new IndiceCid(new ByteBuffersDirectory(), new Cid10Analyzer());
        indiceCid.acrescenteEntrada("Seja bem-vindo!", "Olá Fábio", "-");

        List<Document> documents = indiceCid.searchIndex("descricao", "seja");
        Assert.assertEquals("Olá Fábio", documents.get(0).get("codigo"));
    }

    @Test
    public void semAcentoEncontrada() {
        IndiceCid indiceCid = new IndiceCid(new ByteBuffersDirectory(), new Cid10Analyzer());
        indiceCid.acrescenteEntrada("Seja bem-vindo!", "Açaí é bom", "-");

        List<Document> documents = indiceCid.searchIndex("codigo", "acai");
        Assert.assertEquals("Açaí é bom", documents.get(0).get("codigo"));
    }

    @Test
    public void comAcentoMaiusculasEncontrada() {
        IndiceCid indiceCid = new IndiceCid(new ByteBuffersDirectory(), new Cid10Analyzer());
        indiceCid.acrescenteEntrada("Seja bem-vindo!", "Açaí é bom", "-");

        List<Document> documents = indiceCid.searchIndex("codigo", "AçaÍ");
        Assert.assertEquals("Açaí é bom", documents.get(0).get("codigo"));
    }

    @Test
    public void givenSearchQueryWhenFetchedDocumentThenCorrect() {
        IndiceCid indiceCid = new IndiceCid(new ByteBuffersDirectory(), new StandardAnalyzer());
        indiceCid.acrescenteEntrada("Seja bem-vindo!", "Olá Fábio", "-");

        List<Document> documents = indiceCid.searchIndex("descricao", "seja");
        Assert.assertEquals("Olá Fábio", documents.get(0).get("codigo"));
    }

    @Test
    public void givenTermQueryWhenFetchedDocumentThenCorrect() {
        IndiceCid indiceCid = new IndiceCid(new ByteBuffersDirectory(), new Cid10Analyzer());
        indiceCid.acrescenteEntrada("código contém código", "c1", "-");
        indiceCid.acrescenteEntrada("outro código na descrição", "c2", "-");

        Term term = new Term("descricao", "codigo");
        Query query = new TermQuery(term);

        List<Document> documents = indiceCid.searchIndex(query);
        Assert.assertEquals(2, documents.size());
    }

    @Test
    public void givenBooleanQueryWhenFetchedDocumentThenCorrect() {
        IndiceCid indiceCid = new IndiceCid(new ByteBuffersDirectory(), new StandardAnalyzer());
        indiceCid.acrescenteEntrada("yes success ok", "loinc", "-");
        indiceCid.acrescenteEntrada("ok tudo bem yes", "rnds", "-");

        Term term1 = new Term("descricao", "yes");
        Term term2 = new Term("descricao", "ok");

        TermQuery query1 = new TermQuery(term1);
        TermQuery query2 = new TermQuery(term2);

        BooleanQuery booleanQuery = new BooleanQuery.Builder().add(query1, BooleanClause.Occur.MUST)
                .add(query2, BooleanClause.Occur.MUST).build();

        List<Document> documents = indiceCid.searchIndex(booleanQuery);
        Assert.assertEquals(2, documents.size());
    }

    @Test
    public void givenPhraseQueryWhenFetchedDocumentThenCorrect() {
        IndiceCid indiceCid = new IndiceCid(new ByteBuffersDirectory(), new StandardAnalyzer());
        indiceCid.acrescenteEntrada("yes ok tudo bem", "descricao", "-");

        Query query = new PhraseQuery(1, "descricao", new BytesRef("yes"), new BytesRef("ok"));
        List<Document> documents = indiceCid.searchIndex(query);

        Assert.assertEquals(1, documents.size());
    }

    @Test
    public void givenWildCardQueryWhenFetchedDocumentThenCorrect() {
        IndiceCid indiceCid = new IndiceCid(new ByteBuffersDirectory(), new StandardAnalyzer());
        indiceCid.acrescenteEntrada("success", "loinc", "-");
        indiceCid.acrescenteEntrada("sucesso", "rnds", "-");

        Term term = new Term("descricao", "*cess*");
        Query query = new WildcardQuery(term);

        List<Document> documents = indiceCid.searchIndex(query);
        Assert.assertEquals(2, documents.size());
    }
}