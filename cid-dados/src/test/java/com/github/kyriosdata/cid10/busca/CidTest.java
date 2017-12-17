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

import com.github.kyriosdata.cid10.preprocessor.ArquivoUtils;
import com.github.kyriosdata.cid10.preprocessor.GeraDadosAjustadosParaBusca;
import com.github.kyriosdata.cid10.preprocessor.GeraOriginalAjustado;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;

public class CidTest {

    private static Cid cid;

    @BeforeClass
    public static void setUp() throws Exception {
        GeraOriginalAjustado.gerador();
        GeraDadosAjustadosParaBusca.gerador();
        
        Path dir = ArquivoUtils.getPath("cid/");
        File[] arquivos = dir.toFile().listFiles();
        assertEquals(5, arquivos.length);

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
