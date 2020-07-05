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

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * IMPORTANTE: os arquivos empregados nos testes desta classe são gerados por
 * {@link com.github.kyriosdata.cid10.preprocessor.GeraOriginalAjustado}.
 * Por comodidade estes arquivos já são persistidos no dirório resources/cid.
 */
public class PaginacaoTest {

    private static Busca busca;

    @BeforeAll
    public static void setUp() throws IOException {
        busca = new Busca(new CidPaginacao());
    }

    @Test
    public void naoNull() {
        assertNotNull(busca);
    }

    @Test
    public void respostaDeMilCodigos() {
        assertEquals(1000, busca.encontre(new String[] { "a" }).size());
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
        List<String> encontradas = busca.encontre(new String[]{"a"}, ordem);
        assertEquals(esperado, encontradas.size());
    }
}

class CidPaginacao implements CarregaDados {

    @Override
    public List<String> fromJar(String identificador) {
        final int TOTAL = 1000;
        List<String> conteudo = new ArrayList<>(TOTAL);
        for(int i = 0; i < TOTAL; i++) {
            conteudo.add(i, String.format("A%d;descricao%d", i, i));
        }

        return conteudo;
    }
}
