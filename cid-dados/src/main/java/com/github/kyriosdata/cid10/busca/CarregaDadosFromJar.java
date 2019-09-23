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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CarregaDadosFromJar implements CarregaDados {
    /**
     * Recupera conteúdo de arquivo contido no próprio jar file,
     * diretório "resources".
     *
     * @param filename O nome do arquivo contido no diretório "resources".
     *                 Por exemplo, "x.txt" para o arquivo em questão ou
     *                 "dir/x.txt" se este arquivo estiver no diretório
     *                 "dir", contido no diretório "resources'.
     * @return O conteúdo do arquivo em uma lista de linhas.
     *
     * @throws NullPointerException Se argumento é {@code null}.
     * @throws IllegalArgumentException Se nome de arquivo é {@code null} ou
     * vazio.
     * @throws IOException Se não for possível carregar dados.
     */
    @Override
    public List<String> getLinhas(String filename) throws IOException {
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("nome de arquivo inválido");
        }

        ClassLoader classLoader = this.getClass().getClassLoader();
        URL url = classLoader.getResource(filename);
        if (url == null) {
            throw new IOException("erro ao acessar " + filename);
        }

        Path path = new File(url.getPath()).toPath();
        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }
}