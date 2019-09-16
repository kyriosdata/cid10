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

class CarregaDadosFromJarTest {

    private CarregaDadosFromJar fromJar = new CarregaDadosFromJar();

    @Test
    void contratoNomeDoArquivoNaoPodeSerNull() {
        fromJar.getLinhas(null);
    }
}
