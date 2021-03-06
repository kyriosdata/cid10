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
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Carregador implements CarregaDados {
    /**
     * Recupera conteúdo de arquivo contido no próprio jar file, diretório
     * "resources".
     *
     * <p>Não há garantia de que este método funcione em outros cenários.
     * Observe que faz uso do classloader empregado para carregar esta
     * própria classe.</p>
     *
     * @param filename O nome do arquivo contido no diretório "resources". Por
     *                 exemplo, "x.txt" para o arquivo em questão ou "dir/x.txt" se
     *                 este arquivo estiver no diretório "dir", contido no diretório
     *                 "resources'.
     * @return O conteúdo do arquivo em uma lista de linhas.
     * @throws NullPointerException     Se argumento é {@code null}.
     * @throws IllegalArgumentException Se nome de arquivo é {@code null} ou vazio.
     * @throws IOException              Se não for possível carregar dados.
     */
    @Override
    public List<String> fromJar(String filename) {
        Objects.requireNonNull(filename, "filename");

        if (filename.isEmpty()) {
            throw new IllegalArgumentException("nome inválido");
        }

        // IMPORTANTE: ANTES DE ALTERAR A ESTRATÉGIA
        // filename deve iniciar por /
        // Outras estratégias empregadas e que apresentaram falhas
        return from(Carregador.class.getResourceAsStream(filename));
    }

    public List<String> from(InputStream is) {
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        try (BufferedReader br = new BufferedReader(isr)) {
            return br.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException("erro ao ler stream");
        }
    }
}