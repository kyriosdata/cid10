/*
 * Copyright (c) 2018
 *
 * Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 *
 * Creative Commons Attribution 4.0 International License.
 *
 */

package com.github.kyriosdata.cid10.busca;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DescricaoAdicionalTest {

    @Test
    void descricaoVaziaSeLeituraNaoRealizada() {
        DescricaoAdicional da = DescricaoAdicional.fromCsv(null);
        assertNotNull(da);
        assertNull(da.original(null));
        assertNull(da.busca(null));
        assertEquals(0, da.encontre("x").size());
        assertEquals(0, da.encontre(null).size());
        assertEquals(0, da.encontre("").size());
    }

    @Test
    void trivial() {
        final String d = "G540;Síndrome do desfiladeiro\nG540;sindrome " +
                "desfiladeiro";
        DescricaoAdicional da = DescricaoAdicional.fromCsv(isFromString(d));

        final List<String> encontre = da.encontre("desfi");
        assertEquals(1, encontre.size());

        final String cid = encontre.get(0);
        assertEquals("G540", cid);

        assertEquals("Síndrome do desfiladeiro", da.original(cid));
    }

    @Test
    void descricaoInexistenteNadaEncontrado() {
        final String x = "";
        DescricaoAdicional da = DescricaoAdicional.fromCsv(isFromString(x));

        final List<String> encontre = da.encontre("x");
        assertEquals(0, encontre.size());
    }

    @Test
    void associacoesParaCsv() {
        Map<String, List<String>> associacoes = new HashMap<>();
        associacoes.put("a", List.of("a1", "a2", "a3"));
        associacoes.put("b", List.of("b1"));

        final String csv = DescricaoAdicional.toCsv(associacoes);
        assertEquals("a;a1;a2;a3\nb;b1\n", csv);
    }

    private InputStream isFromString(final String entrada) {
        try {
            final byte[] bytes = entrada.getBytes("UTF-8");
            return new ByteArrayInputStream(bytes);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
