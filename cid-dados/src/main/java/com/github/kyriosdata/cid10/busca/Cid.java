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

import java.nio.file.Path;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementa busca sobre informações contidas na CID-10.
 */
public class Cid {

    /**
     * Estrutura sobre a qual a busca é feita.
     */
    private List<String> busca;

    /**
     * Estrutura que contém a versão "original" das
     * entradas da CID. Empregada para montagem do
     * retorno.
     */
    private List<String> original;

    /**
     * Estrutura que mantém os capítulos.
     */
    private List<String> capitulos;

    private int total;

    public Cid() {
        Path path = ArquivoUtils.getPath("cid/busca.csv");
        busca = ArquivoUtils.carrega(path);
        total = busca.size();

        Path codigos = ArquivoUtils.getPath("cid/codigos.csv");
        original = ArquivoUtils.carrega(codigos);

        // Recupera capítulos e remove header.
        Path chapter = ArquivoUtils.getPath("cid/capitulos.csv");
        capitulos = ArquivoUtils.carrega(chapter);
        capitulos.remove(0);
    }

    /**
     * O conjunto de capítulos da CID-10.
     *
     * @return Lista contendo os capítulos da CID-10.
     */
    public List<String> capitulos() {
        return capitulos;
    }

    /**
     * Procura por entradas na CID-10 que contêm todos os
     * critérios fornecidos.
     *
     * @param criterios Elementos que devem estar presentes
     *                  em toda entrada da CID-10 que os contêm.
     * @return Lista com os identificadores de ordem das
     * entradas na CID-10 que satisfazem os critérios fornecidos.
     */
    public List<String> encontre(String[] criterios) {

        ajustaCriterios(criterios);

        List<Integer> resultado = new ArrayList<>();

        if (criterios.length == 0) {
            return new ArrayList<>(0);
        }

        String primeiro = criterios[0];
        for (int i = 0; i < total; i++) {
            if (busca.get(i).contains(primeiro)) {
                resultado.add(i);
            }
        }

        for (int i = 1; i < criterios.length; i++) {
            resultado = consultaEm(criterios[i], resultado);
        }

        return codigos(resultado);
    }

    private List<String> codigos(List<Integer> lista) {
        List<String> strs = new ArrayList<>(lista.size());

        lista.forEach(i -> {
            strs.add(original.get(i));
        });

        return strs;
    }

    private static void ajustaCriterios(String[] criterios) {
        for (int i = 0; i < criterios.length; i++) {
            criterios[i] = ajustaCriterio(criterios[i]);
        }
    }

    private static String ajustaCriterio(String criterio) {
        StringBuilder ajustado = new StringBuilder(criterio.toLowerCase());

        for (int i = 0; i < ajustado.length(); i++) {
            char letra = ajustado.charAt(i);
            if (letra == ';' || letra == ',') {
                ajustado.deleteCharAt(i);
            }
        }

        return removeAcentos(ajustado.toString());
    }

    private static String removeAcentos(String original) {
        String sa = Normalizer.normalize(original, Normalizer.Form.NFD);

        return sa.replaceAll("\\p{M}", "");
    }

    private List<Integer> consultaEm(String criterio, List<Integer> parcial) {
        List<Integer> encontrados = new ArrayList<>();
        int dominioBusca = parcial.size();

        for (int i = 0; i < dominioBusca; i++) {
            int indiceParaBusca = parcial.get(i);
            if (busca.get(indiceParaBusca).contains(criterio)) {
                encontrados.add(indiceParaBusca);
            }
        }

        return encontrados;
    }
}
