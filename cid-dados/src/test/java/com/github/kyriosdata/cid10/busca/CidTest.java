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

import static junit.framework.Assert.assertEquals;

/**
 * IMPORTANTE: você deve executar as classes
 * {@link com.github.kyriosdata.cid10.preprocessor.GeraOriginalAjustado} e
 * {@link com.github.kyriosdata.cid10.preprocessor.GeraDadosAjustadosParaBusca}
 * antes que os testes desta classe possam ser executados. 
 */
public class CidTest {

    private static Cid cid;

    @BeforeClass
    public static void setUp() throws Exception {
        cid = new Cid();
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
