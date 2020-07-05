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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DescricaoAdicionalTest {

    @Test
    void trivial() {
        final String d = "G540;Síndrome do desfiladeiro\nG540;sindrome " +
                "desfiladeiro";
        DescricaoAdicional da = DescricaoAdicional.fromCsv(isFromString(d));

        final List<String> encontre = da.encontre("desfi");
        assertEquals(1, encontre.size());
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
