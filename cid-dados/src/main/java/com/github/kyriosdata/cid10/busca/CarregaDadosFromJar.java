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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

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
        InputStream is = classLoader.getResourceAsStream(filename);
        if (is == null) {
            throw new IOException("erro ao acessar " + filename);
        }

        is.available();
        Charset utf8 = StandardCharsets.UTF_8;
        InputStreamReader isr = new InputStreamReader(is, utf8);
        BufferedReader br = new BufferedReader(isr);
        List<String> linhas = br.lines().collect(Collectors.toList());
        if (linhas.isEmpty()) {
            throw new IOException("erro ao carregar dados de " + filename);
        }

        return linhas;
    }
}