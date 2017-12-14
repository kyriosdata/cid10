/*
 * Copyright (c) 2017
 *
 * Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 *
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.bsus.cid10.preprocessor;

import java.util.*;

/**
 *
 */
public class Busca {

    private String[] sentencas;
    private int size;

    public Busca(String[] sentencas) {
        this.sentencas = sentencas;
        size = sentencas.length;
    }

    public List<Integer> encontre(String[] criterios) {

        List<Integer> encontradas = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            boolean encontrou = true;
            for (String criterio : criterios) {
                if (!sentencas[i].contains(criterio)) {
                    encontrou = false;
                    break;
                }
            }

            if (encontrou) {
                encontradas.add(i);
            }
        }

        return encontradas;
    }

    private static List<String> paraRemover = Arrays.asList(new String[]{
            "de", "da", "das", "do", "dos",
            "a", "as", "e", "o", "os",
            "na", "nas", "no", "nos",
            "para",
            "que", "com", "ou",
            "em"
    });

    /**
     * Operações realizadas:
     * (a) toLower (realizado via linha de comandos)
     * cat arquivo.json | tr "[A-Z]" "[a-z]"
     * (b) remover 'de', 'da', 'a', 'e', 'as', 'dos', '[', ']', '-', ',' e outros.
     * (c) eliminar acentos
     */
    public static String trataPalavra(String palavra) {

        if (paraRemover.contains(palavra)) {
            return null;
        }

        if (palavra.isEmpty()) {
            return null;
        }

        return palavra;

    }
}