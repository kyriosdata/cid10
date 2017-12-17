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

import com.github.kyriosdata.cid10.preprocessor.GeraOriginalAjustado;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Função para recuperar conteúdo de arquivos no diretório
 * "resources".
 */
public class ArquivoUtils {

    /**
     * Obtém instância de {@link File} correspondente ao nome de arquivo
     * depositado no diretório "resources".
     *
     * @param fileName Nome do arquivo relativo ao diretório "resources".
     * @return Instância de {@link File}.
     */
    public static Path getPath(String fileName) {
        ArquivoUtils obj = new ArquivoUtils();
        ClassLoader classLoader = obj.getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        URI uri = null;

        try {
            uri = resource.toURI();
        } catch (Exception exp) {
            return null;
        }

        return resource == null ? null : Paths.get(uri);
    }

    public static List<String> carrega(Path path) {
        try {
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println(e);
        }

        return null;
    }
}
