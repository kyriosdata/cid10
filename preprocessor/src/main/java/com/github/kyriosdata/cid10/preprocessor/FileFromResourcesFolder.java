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

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Função para recuperar conteúdo de arquivos no diretório
 * "resources".
 */
public class FileFromResourcesFolder {

    /**
     * Obtém instância de {@link File} correspondente ao nome de arquivo
     * depositado no diretório "resources".
     *
     * @param fileName Nome do arquivo relativo ao diretório "resources".
     *
     * @return Instância de {@link File}.
     */
    public static Path getPath(String fileName) {
        FileFromResourcesFolder obj = new FileFromResourcesFolder();
        ClassLoader classLoader = obj.getClass().getClassLoader();
        return Paths.get(classLoader.getResource(fileName).getFile());
    }
}
