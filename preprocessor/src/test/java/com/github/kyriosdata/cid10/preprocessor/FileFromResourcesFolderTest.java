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

import java.io.File;

import static junit.framework.TestCase.assertNotNull;

public class FileFromResourcesFolderTest {

    @Test
    public void acessoCorretoEmResources() {
        File f = FileFromResourcesFolder.get("datasus/CID-10-GRUPOS.CSV");
        assertNotNull(f);
    }
}
