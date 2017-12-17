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

import org.junit.Test;

import java.nio.file.Path;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

public class ArquivoUtilsTest {

    @Test
    public void acessoCorretoEmResources() {
        Path p = ArquivoUtils.getPath("datasus/CID-10-GRUPOS.CSV");
        assertNotNull(p);
    }

    @Test
    public void arquivoInvalidoRetornaNull() {
        Path p = ArquivoUtils.getPath("alguma coisa");
        assertNull(p);
    }
}
