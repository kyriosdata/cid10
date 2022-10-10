package com.github.kyriosdata.cid10.lucene;

import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.*;
import org.apache.lucene.util.BytesRef;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConstrucaoIndiceParaPortuguesTest {

    private IndiceCid indiceCid = new IndiceCid(new ByteBuffersDirectory(), new Cid10Analyzer());

    @Test
    public void encontraConformeDescricao() {
        List<Document> documents = indiceCid.searchIndex("descricao", "Cólera");
        assertEquals(6, documents.size());
    }

    @Test
    public void encontraSemAcento() {
        List<Document> documents = indiceCid.searchIndex("descricao", "Colera");
        assertEquals(6, documents.size());
    }

    @Test
    public void encontraMinusculasSemAcento() {
        List<Document> documents = indiceCid.searchIndex("descricao", "colera");
        assertEquals(6, documents.size());
    }

    @Test
    public void encontraTudoMaiuscula() {
        List<Document> documents = indiceCid.searchIndex("descricao", "CÓLERA");
        assertEquals(6, documents.size());
    }

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
    public void ultimaLetraDesconhecida() {
        Term term = new Term("descricao", "Paratifoide");
        Query query = new WildcardQuery(term);
        List<Document> novos = indiceCid.searchIndex(query);
        assertEquals(6, novos.size());
    }

    @Test
    public void givenBuscaComMinúsculaEncontrada() {
        indiceCid.acrescenteEntrada("Olá Fábio", "-", "Seja bem-vindo!");

        List<Document> documents = indiceCid.searchIndex("descricao", "seja");
        Assert.assertEquals("Olá Fábio", documents.get(0).get("codigo"));
    }

    @Test
    public void semAcentoEncontrada() {
        indiceCid.acrescenteEntrada( "Açaí é bom", "-", "Seja bem-vindo!");

        List<Document> documents = indiceCid.searchIndex("codigo", "acai");
        Assert.assertEquals("Açaí é bom", documents.get(0).get("codigo"));
    }

    @Test
    public void comAcentoMaiusculasEncontrada() {
        indiceCid.acrescenteEntrada( "Açaí é bom", "-", "Seja bem-vindo!");

        List<Document> documents = indiceCid.searchIndex("codigo", "AçaÍ");
        Assert.assertEquals("Açaí é bom", documents.get(0).get("codigo"));
    }

    @Test
    public void givenSearchQueryWhenFetchedDocumentThenCorrect() {
        indiceCid.acrescenteEntrada( "Olá Fábio", "-", "Seja bem-vindo!");

        List<Document> documents = indiceCid.searchIndex("descricao", "seja");
        Assert.assertEquals("Olá Fábio", documents.get(0).get("codigo"));
    }

    @Test
    public void givenTermQueryWhenFetchedDocumentThenCorrect() {
        indiceCid.acrescenteEntrada( "c1", "-", "código contém código");
        indiceCid.acrescenteEntrada( "c2", "-", "outro código na descrição");

        Term term = new Term("descricao", "codigo");
        Query query = new TermQuery(term);

        List<Document> documents = indiceCid.searchIndex(query);
        Assert.assertEquals(2, documents.size());
    }

    @Test
    public void givenBooleanQueryWhenFetchedDocumentThenCorrect() {
        indiceCid.acrescenteEntrada("loinc", "-", "yes success ok");
        indiceCid.acrescenteEntrada( "rnds", "-", "ok tudo bem yes");

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
        indiceCid.acrescenteEntrada("yes ok tudo bem", "-", "descricao");

        Query query = new PhraseQuery(1, "codigo", new BytesRef("yes"), new BytesRef("ok"));
        List<Document> documents = indiceCid.searchIndex(query);

        Assert.assertEquals(1, documents.size());
    }

    @Test
    public void givenWildCardQueryWhenFetchedDocumentThenCorrect() {
        Term term = new Term("descricao", "*cess*");
        Query query = new WildcardQuery(term);

        List<Document> documents = indiceCid.searchIndex(query);
        Assert.assertEquals(10, documents.size());
    }
}