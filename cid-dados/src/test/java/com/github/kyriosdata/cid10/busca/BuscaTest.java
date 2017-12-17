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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BuscaTest {

    private Busca b;

    @Test
    public void criterioInexistenteNenhumCodigoEncontrado() {
        assertEquals(0, Busca.encontre(new String[] { "xyzw" }).size());
        assertEquals(0, Busca.encontre(new String[] { "@" }).size());
        assertEquals(0, Busca.encontre(new String[] { "-" }).size());
        assertEquals(0, Busca.encontre(new String[] { "[" }).size());
    }

    @Test
    public void maiusculasMinusculasIguais() {
        assertEquals(1, Busca.encontre(new String[] { "A000" }).size());
        assertEquals(1, Busca.encontre(new String[] { "a000" }).size());
    }

    @Test
    public void algunsSimbolosSaoIgnorados() {
        assertEquals(1, Busca.encontre(new String[] { "a000,;" }).size());
    }

    @Test
    public void acentosIgnorados() {
        String[] termos = { "CÓLERA", "NÃO" };
        assertEquals(1, Busca.encontre(termos).size());
    }
}
