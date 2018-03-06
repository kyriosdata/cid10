
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementa serviço de busca na CID-10.
 * <p>O uso é trivial, basta enviar a mensagem
 * {@link #encontre(String[])}} para uma instância
 * da classe {@link Cid}.</p>
 */
public class Cid {

    /**
     * Trata argumentos fornecido ao aplicativo como partes de entradas da CID-10
     * a serem localizadas. O conjunto daquelas encontradas é retornado.
     *
     * @param args Argumentos que serão tratados como partes ou componentes
     *             de código e/ou descrição das entradas da CID-10.
     */
    public static void main(String[] args) {
        new Cid().encontre(args).forEach(System.out::println);
    }

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

    public Cid() {
        busca = getConteudo("cid/busca.csv");

        original = getConteudo("cid/codigos.csv");

        // Recupera capítulos e remove header.
        capitulos = getConteudo("cid/capitulos.csv");
    }

    public List<String> getConteudo(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream is = classLoader.getResourceAsStream(fileName);
        Charset utf8 = StandardCharsets.UTF_8;
        InputStreamReader isr = new InputStreamReader(is, utf8);
        BufferedReader br = new BufferedReader(isr);

        List<String> conteudo = new ArrayList<>();

        String linha;

        try {
            while ((linha = br.readLine()) != null) {
                conteudo.add(linha);
            }
        } catch (Exception exp) {
            return null;
        }

        return conteudo;
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
     *                  em toda entrada da CID-10 retornada.
     *
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
        int total = busca.size();
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
