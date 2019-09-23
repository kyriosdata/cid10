/*
 *
 *  * Copyright (c) 2017
 *  *
 *  * Fábio Nogueira de Lucena
 *  * Fábrica de Software - Instituto de Informática (UFG)
 *  *
 *  * Creative Commons Attribution 4.0 International License.
 *
 *
 */

package com.github.kyriosdata.cid10.busca;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * IMPORTANTE: os arquivos empregados nos testes desta classe são gerados por
 * {@link com.github.kyriosdata.cid10.preprocessor.GeraOriginalAjustado}.
 * Por comodidade estes arquivos já são persistidos no dirório resources/cid.
 */
class CidTest {

    private static Cid cid;

    @BeforeEach
    void setUp() throws IOException {
        cid = new Cid(new CarregaDadosFromJar());
    }

    @AfterEach
    void tearDown() {
        cid.close();
    }

    @Test
    void autoCloseableCalled() {
        try (Cid local = new Cid(new CarregaDadosFromJar())) {
            assertNotNull(local.capitulos());
        } catch (IOException exp) {
            assertTrue(false, "não deveria ocorrer exceção");
        }
    }

    @Test
    public void umaEntradaExistente() {
        List<String> search = cid.encontre(new String[]{"a9", "w"}, 0);
        assertEquals(1, search.size());
        assertTrue(search.get(0).startsWith("A923"));
    }

    @Test
    public void criterioInexistenteNenhumCodigoEncontrado() {
        assertEquals(0, cid.encontre(new String[] { "xyzw" }).size());
        assertEquals(0, cid.encontre(new String[] { "@" }).size());
        assertEquals(0, cid.encontre(new String[] { "-" }).size());
        assertEquals(0, cid.encontre(new String[] { "[" }).size());
    }

    @Test
    public void maiusculasMinusculasIguais() {
        assertEquals(1, cid.encontre(new String[] { "A000" }).size());
        assertEquals(1, cid.encontre(new String[] { "a000" }).size());
    }

    @Test
    public void algunsSimbolosSaoIgnorados() {
        assertEquals(1, cid.encontre(new String[] { "a000,;" }).size());
    }

    @Test
    public void acentosIgnorados() {
        String[] termos = { "CÓLERA", "NÃO" };
        assertEquals(1, cid.encontre(termos).size());
    }

    @Test
    public void outrosCasos() {
        String[] termos = { "Paratifóide", "11" };
        assertEquals(1, cid.encontre(termos).size());
    }

    @Test
    public void verificaCapitulos() {
        assertEquals(22, cid.capitulos().size());
    }
}
