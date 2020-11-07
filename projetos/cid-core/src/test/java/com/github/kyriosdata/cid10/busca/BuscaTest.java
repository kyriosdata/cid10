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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * IMPORTANTE: os arquivos empregados nos testes desta classe são gerados por
 * {@link com.github.kyriosdata.cid10.preprocessor.GeraOriginalAjustado}.
 * Por comodidade estes arquivos já são persistidos no dirório resources/cid.
 */
class BuscaTest {

    private static Busca busca;

    @BeforeEach
    void setUp() throws IOException {
        busca = new Busca(new Carregador());
    }

    @AfterEach
    void tearDown() {
        busca.close();
    }

    @Test
    void nadaEncontradoSeNadaProcurado() {
        List<String> search = busca.encontre(new String[]{}, 0);
        assertEquals(0, search.size());
    }

    @Test
    void autoCloseableCalled() {
        try (Busca local = new Busca(new Carregador())) {
            assertNotNull(local.capitulos());
        } catch (IOException exp) {
            fail("não deveria ocorrer exceção");
        }
    }

    @Test
    void umaEntradaExistente() {
        List<String> search = busca.encontre(new String[]{"a9", "w"}, 0);
        assertEquals(1, search.size());
        assertTrue(search.get(0).startsWith("A923"));
    }

    @Test
    void criterioInexistenteNenhumCodigoEncontrado() {
        assertEquals(0, busca.encontre(new String[] { "xyzw" }).size());
        assertEquals(0, busca.encontre(new String[] { "@" }).size());
        assertEquals(0, busca.encontre(new String[] { "-" }).size());
        assertEquals(0, busca.encontre(new String[] { "[" }).size());
    }

    @Test
    void maiusculasMinusculasIguais() {
        assertEquals(1, busca.encontre(new String[] { "A000" }).size());
        assertEquals(1, busca.encontre(new String[] { "a000" }).size());
    }

    @Test
    void acentosIgnorados() {
        String[] termos = { "CÓLERA", "NÃO" };
        assertEquals(1, busca.encontre(termos).size());
    }

    @Test
    void outrosCasos() {
        String[] termos = { "Paratifóide", "11" };
        assertEquals(1, busca.encontre(termos).size());
    }

    @Test
    void verificaCapitulos() {
        assertEquals(22, busca.capitulos().size());
    }
}
