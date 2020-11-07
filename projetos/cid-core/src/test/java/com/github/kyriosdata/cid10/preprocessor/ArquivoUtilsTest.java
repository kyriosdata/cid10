/*
 *
 * Copyright (c) 2017
 *
 * Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 *
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.cid10.preprocessor;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ArquivoUtilsTest {

    @Test
    void acessoCorretoEmResources() {
        Path p = ArquivoUtils.getPath("datasus/CID-10-GRUPOS.CSV");
        assertNotNull(p);
    }

    @Test
    void arquivoInvalidoRetornaNull() {
        Path p = ArquivoUtils.getPath("alguma coisa");
        assertNull(p);
    }
}
