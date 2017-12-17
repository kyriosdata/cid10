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

public class CidTest {

    @Test
    public void criterioInexistenteNenhumCodigoEncontrado() {
        assertEquals(0, Cid.encontre(new String[] { "xyzw" }).size());
        assertEquals(0, Cid.encontre(new String[] { "@" }).size());
        assertEquals(0, Cid.encontre(new String[] { "-" }).size());
        assertEquals(0, Cid.encontre(new String[] { "[" }).size());
    }

    @Test
    public void maiusculasMinusculasIguais() {
        assertEquals(1, Cid.encontre(new String[] { "A000" }).size());
        assertEquals(1, Cid.encontre(new String[] { "a000" }).size());
    }

    @Test
    public void algunsSimbolosSaoIgnorados() {
        assertEquals(1, Cid.encontre(new String[] { "a000,;" }).size());
    }

    @Test
    public void acentosIgnorados() {
        String[] termos = { "CÓLERA", "NÃO" };
        assertEquals(1, Cid.encontre(termos).size());
    }

    @Test
    public void outrosCasos() {
        String[] termos = { "Paratifóide", "11" };
        assertEquals(1, Cid.encontre(termos).size());
    }

    @Test
    public void verificaCapitulos() {
        Cid.capitulos().forEach(System.out::println);
    }
}
