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

public class LuceneInMemorySearchIntegrationTest {

    @Test
    public void givenBuscaComMinúsculaEncontrada() {
        IndiceParaCodigos indiceParaCodigos = new IndiceParaCodigos(new ByteBuffersDirectory(), new Cid10Analyzer());
        indiceParaCodigos.indexDocument("Olá Fábio", "Seja bem-vindo!");

        List<Document> documents = indiceParaCodigos.searchIndex("descricao", "seja");
        Assert.assertEquals("Olá Fábio", documents.get(0).get("codigo"));
    }

    @Test
    public void semAcentoEncontrada() {
        IndiceParaCodigos indiceParaCodigos = new IndiceParaCodigos(new ByteBuffersDirectory(), new Cid10Analyzer());
        indiceParaCodigos.indexDocument("Açaí é bom", "Seja bem-vindo!");

        List<Document> documents = indiceParaCodigos.searchIndex("codigo", "acai");
        Assert.assertEquals("Açaí é bom", documents.get(0).get("codigo"));
    }

    @Test
    public void comAcentoMaiusculasEncontrada() {
        IndiceParaCodigos indiceParaCodigos = new IndiceParaCodigos(new ByteBuffersDirectory(), new Cid10Analyzer());
        indiceParaCodigos.indexDocument("Açaí é bom", "Seja bem-vindo!");

        List<Document> documents = indiceParaCodigos.searchIndex("codigo", "AçaÍ");
        Assert.assertEquals("Açaí é bom", documents.get(0).get("codigo"));
    }

    @Test
    public void givenSearchQueryWhenFetchedDocumentThenCorrect() {
        IndiceParaCodigos indiceParaCodigos = new IndiceParaCodigos(new ByteBuffersDirectory(), new StandardAnalyzer());
        indiceParaCodigos.indexDocument("Olá Fábio", "Seja bem-vindo!");

        List<Document> documents = indiceParaCodigos.searchIndex("descricao", "seja");
        Assert.assertEquals("Olá Fábio", documents.get(0).get("codigo"));
    }

    @Test
    public void givenTermQueryWhenFetchedDocumentThenCorrect() {
        IndiceParaCodigos indiceParaCodigos = new IndiceParaCodigos(new ByteBuffersDirectory(), new Cid10Analyzer());
        indiceParaCodigos.indexDocument("c1", "código contém código");
        indiceParaCodigos.indexDocument("c2", "outro código na descrição");

        Term term = new Term("descricao", "codigo");
        Query query = new TermQuery(term);

        List<Document> documents = indiceParaCodigos.searchIndex(query);
        Assert.assertEquals(2, documents.size());
    }

    @Test
    public void givenBooleanQueryWhenFetchedDocumentThenCorrect() {
        IndiceParaCodigos indiceParaCodigos = new IndiceParaCodigos(new ByteBuffersDirectory(), new StandardAnalyzer());
        indiceParaCodigos.indexDocument("loinc", "yes success ok");
        indiceParaCodigos.indexDocument("rnds", "ok tudo bem yes");

        Term term1 = new Term("descricao", "yes");
        Term term2 = new Term("descricao", "ok");

        TermQuery query1 = new TermQuery(term1);
        TermQuery query2 = new TermQuery(term2);

        BooleanQuery booleanQuery = new BooleanQuery.Builder().add(query1, BooleanClause.Occur.MUST)
                .add(query2, BooleanClause.Occur.MUST).build();

        List<Document> documents = indiceParaCodigos.searchIndex(booleanQuery);
        Assert.assertEquals(2, documents.size());
    }

    @Test
    public void givenPhraseQueryWhenFetchedDocumentThenCorrect() {
        IndiceParaCodigos indiceParaCodigos = new IndiceParaCodigos(new ByteBuffersDirectory(), new StandardAnalyzer());
        indiceParaCodigos.indexDocument("descricao", "yes ok tudo bem");

        Query query = new PhraseQuery(1, "descricao", new BytesRef("yes"), new BytesRef("ok"));
        List<Document> documents = indiceParaCodigos.searchIndex(query);

        Assert.assertEquals(1, documents.size());
    }

    @Test
    public void givenWildCardQueryWhenFetchedDocumentThenCorrect() {
        IndiceParaCodigos indiceParaCodigos = new IndiceParaCodigos(new ByteBuffersDirectory(), new StandardAnalyzer());
        indiceParaCodigos.indexDocument("loinc", "success");
        indiceParaCodigos.indexDocument("rnds", "sucesso");

        Term term = new Term("descricao", "*cess*");
        Query query = new WildcardQuery(term);

        List<Document> documents = indiceParaCodigos.searchIndex(query);
        Assert.assertEquals(2, documents.size());
    }
}