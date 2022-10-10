package com.github.kyriosdata.cid10.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.SQLOutput;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConstrucaoIndiceParaPortuguesTest {

    private final IndiceCid indiceCid = new IndiceCid(new ByteBuffersDirectory(), new Cid10Analyzer());

    @Test
    public void encontraConformeDescricao() {
        List<Document> documents = indiceCid.searchIndex("descricao", "Cólera");
        assertEquals(10, documents.size());
    }

    @Test
    public void encontraSemAcento() {
        List<Document> documents = indiceCid.searchIndex("descricao", "Colera");
        assertEquals(10, documents.size());
    }

    @Test
    public void encontraMinusculasSemAcento() {
        List<Document> documents = indiceCid.searchIndex("descricao", "colera");
        assertEquals(10, documents.size());
    }

    @Test
    public void encontraTudoMaiuscula() {
        List<Document> documents = indiceCid.searchIndex("descricao", "CÓLERA");
        assertEquals(10, documents.size());
    }

    @Test
    public void localizaOcorrenciasDeParatifoide() {
        List<Document> novos = indiceCid.searchIndex("descricao", "paratifoide");
        assertEquals(8, novos.size());
    }

    @Test
    public void localizaOcorrenciasDeParatifoideComMaiuscula() {
        List<Document> novos = indiceCid.searchIndex("descricao", "Paratifoide");
        assertEquals(8, novos.size());
    }

    @Test
    public void artificiais() {
        List<Document> novos = indiceCid.searchIndex("descricao", "artificiais");
        assertEquals(10, novos.size());
    }

    @Test
    public void audiologicos() {
        List<Document> novos = indiceCid.searchIndex("descricao", "audiologico");
        assertEquals("Z962", novos.get(0).get("codigo"));
    }
}