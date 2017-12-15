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
        URL resource = classLoader.getResource(fileName);
        URI uri = null;

        try {
            uri = resource.toURI();
        } catch(Exception exp) {
            return null;
        }

        return resource == null ? null : Paths.get(uri);
    }

    /**
     * Recupera lista de linhas de arquivo CSV.
     *
     * @param entrada Nome do arquivo CSV.
     *
     * @return Lista de linhas correspondentes ao conteúdo do arquivo CSV.
     *
     * @throws IOException
     */
    public static List<String> getLinhas(String entrada) {
        List<String> linhas = null;
        try {
            String fileName = GeraOriginalAjustado.DIR + entrada;
            Path grupos = getPath(fileName);
            linhas = Files.readAllLines(grupos, StandardCharsets.ISO_8859_1);
        } catch (Exception exp) {
            System.err.println(exp.toString());
        }

        return linhas;
    }

    public static void armazena(List<String> dados, String dir, String file) {
        Path path = Paths.get(dir, file);
        Charset charset = StandardCharsets.UTF_8;

        try {
            Files.write(path, dados, charset, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
