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

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * IMPORTANTE: os arquivos empregados nos testes desta classe são gerados por
 * {@link com.github.kyriosdata.cid10.preprocessor.GeraOriginalAjustado}.
 * Por comodidade estes arquivos já são persistidos no dirório resources/cid.
 */
public class CidTest {

    private static Cid cid;

    @BeforeClass
    public static void setUp() {
        cid = new Cid();
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
