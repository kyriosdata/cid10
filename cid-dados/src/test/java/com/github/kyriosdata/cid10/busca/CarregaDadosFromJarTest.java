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

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CarregaDadosFromJarTest {

    private CarregaDadosFromJar fromJar = new CarregaDadosFromJar();

    @Test
    void contratoNomeDoArquivoNaoPodeSerNull() {
        assertThrows(IllegalArgumentException.class, () ->
                fromJar.getLinhas(null));
    }

    @Test
    void contratoNomeDoArquivoNaoPodeSerVazio() {
        assertThrows(IllegalArgumentException.class, () ->
                fromJar.getLinhas(""));
    }

    @Test
    void contratoNomeDoArquivoNaoPodeSerInexistente() {
        assertThrows(IOException.class, () ->
                fromJar.getLinhas(UUID.randomUUID().toString()));
    }
}
