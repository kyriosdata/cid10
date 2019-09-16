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

import java.text.Normalizer;
import java.util.*;

/**
 * Serviço de busca na CID-10.
 */
public class Cid {

    /**
     * Cache de lista vazia (evita recriar instância desnecessariamente).
     */
    private static List<String> listaVazia =
            Collections.unmodifiableList(new ArrayList<>(0));

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

    /**
     * A resposta será paginada, ou seja, conterá no máximo a quantidade
     * de entradas dada pelo valor da presente variável. O valor padrão
     * é 50.
     */
    private int tamanhoPagina = 50;

    /**
     * Cria objeto para atender requisições de busca na CID-10.
     * @param carregador
     *
     * @throws NullPointerException Se o carregador fornecido for {@code null}.
     */
    public Cid(final CarregaDados carregador) {
        CarregaDados carregaCID = Objects.requireNonNull(carregador);

        busca = carregaCID.getConteudo("cid/busca.csv");

        original = carregaCID.getConteudo("cid/codigos.csv");

        // Recupera capítulos e remove header.
        capitulos = carregaCID.getConteudo("cid/capitulos.csv");
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
     * Localiza entradas da CID-10 que contém os critérios fornecidos e
     * retorna apenas aquelas a partir da ordem fornecida até o limite de
     * {@link #tamanhoPagina}.
     *
     * <p>Em geral este método é chamado inicialmente com o valor 0 para a
     * ordem e, para requisições seguintes, o valores múltiplos de
     * {@link #tamanhoPagina}.</p>
     *
     * @param criterios Se uma entrada é retornada, então satisfaz ou contém
     *                  os critérios fornecidos.
     *
     * @param ordem A ordem da entrada a partir da qual será montada a
     *               resposta. Se os critérios identificam N entradas, então
     *              nenhuma resposta será fornecida se a ordem indicada
     *              for igual ou superior a N. Se for igual a N - 1, então
     *              apenas a última entrada das N encontradas será retornada.
     *              Se a ordem for inferior a N - 1, serão retornadas todas as
     *              entradas a partir da ordem fornecida até o limite de
     *              {@link #tamanhoPagina}.
     *
     * @return As entradas que satisfazem os critérios a partir da ordem
     * indicada.
     */
    public List<String> encontre(String[] criterios, int ordem) {
        if (ordem < 0) {
            return listaVazia;
        }

        List<String> parcial = encontre(criterios);

        // Ordem além da resposta.
        int tamanhoRespostaParcial = parcial.size();
        if (ordem >= tamanhoRespostaParcial) {
            return listaVazia;
        }

        int superior = ordem + tamanhoPagina;
        if (superior > tamanhoRespostaParcial) {
            superior = tamanhoRespostaParcial;
        }

        return parcial.subList(ordem, superior);
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
            return listaVazia;
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

        lista.forEach(i -> strs.add(original.get(i)));

        return strs;
    }

    // TODO limitar total de critérios (talvez 5, por exemplo) (ignora demais).
    // TODO limitar tamanho maximo de cada critério (talvez 10 caracteres, ignore demais)
    // TODO não alterar array original.
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

        for (Integer aParcial : parcial) {
            int indiceParaBusca = aParcial;
            if (busca.get(indiceParaBusca).contains(criterio)) {
                encontrados.add(indiceParaBusca);
            }
        }

        return encontrados;
    }
}
