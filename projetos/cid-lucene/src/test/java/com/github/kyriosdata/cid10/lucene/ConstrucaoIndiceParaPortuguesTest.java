package com.github.kyriosdata.cid10.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
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
public class ConstrucaoIndiceParaPortuguesTest {

    private final IndiceCid indiceCid = new IndiceCid(new ByteBuffersDirectory(), new Cid10Analyzer());

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
    public void localizaOcorrenciasDeParatifoide() {
        List<Document> novos = indiceCid.searchIndex("descricao", "paratifoide");
        assertEquals(6, novos.size());
    }

    @Test
    public void localizaOcorrenciasDeParatifoideComMaiuscula() {
        List<Document> novos = indiceCid.searchIndex("descricao", "Paratifoide");
        assertEquals(6, novos.size());
    }

    @Test
    public void singular() {
        List<Document> novos = indiceCid.searchIndex("descricao", "roda");
        assertEquals(10, novos.size());
    }

}