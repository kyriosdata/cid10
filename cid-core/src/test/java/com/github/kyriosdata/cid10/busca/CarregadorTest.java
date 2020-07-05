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

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CarregadorTest {

    private Carregador fromJar = new Carregador();

    @Test
    void contratoNomeDoArquivoNaoPodeSerNull() {
        assertThrows(NullPointerException.class, () ->
                fromJar.fromJar((String)null));
    }

    @Test
    void contratoNomeDoArquivoNaoPodeSerVazio() {
        assertThrows(IllegalArgumentException.class, () ->
                fromJar.fromJar(""));
    }

    @Test
    void contratoNomeDoArquivoNaoPodeSerInexistente() {
        assertThrows(NullPointerException.class, () ->
                fromJar.fromJar(UUID.randomUUID().toString()));
    }

    @Test
    void carregaArquivoDeBusca() {
        List<String> linhas = fromJar.fromJar("/cid/busca.csv");
        assertNotNull(linhas);
        assertTrue(linhas.get(0).contains("a00;colera"));
    }
}
