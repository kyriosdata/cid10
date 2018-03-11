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

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * IMPORTANTE: os arquivos empregados nos testes desta classe são gerados por
 * {@link com.github.kyriosdata.cid10.preprocessor.GeraOriginalAjustado}.
 * Por comodidade estes arquivos já são persistidos no dirório resources/cid.
 */
public class PaginacaoTest {

    private static Cid cid;

    @BeforeClass
    public static void setUp() {
        cid = new CidPaginacao();
    }

    @Test
    public void naoNull() {
        assertNotNull(cid);
    }

    @Test
    public void respostaDeMilCodigos() {
        assertEquals(1000, cid.encontre(new String[] { "a" }).size());
    }

    @Test
    public void primeiraPagina() {
        verificaPagina(0, 50);
        verificaPagina(950, 50);
        verificaPagina(951, 49);
        verificaPagina(952, 48);
        verificaPagina(998, 2);
        verificaPagina(999, 1);
    }

    @Test
    public void casosAlemDosLimites() {
        verificaPagina(-1, 0);
        verificaPagina(1000, 0);
    }

    private void verificaPagina(int ordem, int esperado) {
        List<String> encontradas = cid.encontre(new String[]{"a"}, ordem);
        assertEquals(esperado, encontradas.size());
    }
}

class CidPaginacao extends Cid {

    @Override
    public List<String> getConteudo(String fileName) {
        final int TOTAL = 1000;
        List<String> conteudo = new ArrayList<>(TOTAL);
        for(int i = 0; i < TOTAL; i++) {
            conteudo.add(i, String.format("A%d;descricao%d", i, i));
        }

        return conteudo;
    }
}
