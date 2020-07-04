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

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Serviço de busca por entradas na CID-10 que contêm os critérios
 * (elementos) fornecidos.
 */
public class Cid implements AutoCloseable {

    /**
     * Total máximo de critérios considerados.
     */
    public static final int MAX_CRITERIOS = 5;

    /**
     * Total máximo de caracteres do critério que são considerados.
     */
    public static final int MAX_SIZE_CRITERIO = 10;

    /**
     * Tamanho máximo de entradas da resposta.
     */
    public static final int MAX_ENTRADAS = 50;

    /**
     * Estrutura sobre a qual a busca é feita.
     */
    private List<String> busca;

    /**
     * Estrutura que contém a versão "original" das
     * entradas da CID. A versão original é empregada para
     * a confecção da resposta, baseada nas entradas originais
     * da CID.
     */
    private List<String> original;

    /**
     * Estrutura que mantém os capítulos.
     */
    private List<String> capitulos;

    /**
     * A resposta será paginada, ou seja, conterá no máximo a quantidade
     * de entradas dada pelo valor da presente variável.
     */
    private int tamanhoPagina = MAX_ENTRADAS;

    /**
     * Cria objeto para atender requisições de busca na CID-10.
     *
     * @param carregador Objeto empregado para recuperar as estruturas de
     *                   dados empregadas para busca.
     * @throws NullPointerException Se o carregador fornecido for {@code null}.
     * @throws IOException          Se não for possível carregar dados.
     */
    public Cid(final CarregaDados carregador) throws IOException {
        Objects.requireNonNull(carregador);

        busca = carregador.getLinhas("/cid/busca.csv");
        original = carregador.getLinhas("/cid/codigos.csv");
        capitulos = carregador.getLinhas("/cid/capitulos.csv");
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
     * @param ordem     A ordem da entrada a partir da qual será montada a
     *                  resposta. Se os critérios identificam N entradas, então
     *                  nenhuma resposta será fornecida se a ordem indicada
     *                  for igual ou superior a N. Se for igual a N - 1, então
     *                  apenas a última entrada das N encontradas será
     *                  retornada.
     *                  Se a ordem for inferior a N - 1, serão retornadas
     *                  todas as
     *                  entradas a partir da ordem fornecida até o limite de
     *                  {@link #tamanhoPagina}.
     * @return As entradas que satisfazem os critérios a partir da ordem
     * indicada.
     */
    public List<String> encontre(String[] criterios, int ordem) {
        if (ordem < 0) {
            return Collections.emptyList();
        }

        List<String> parcial = encontre(criterios);

        // Ordem além da resposta.
        int tamanhoRespostaParcial = parcial.size();
        if (ordem >= tamanhoRespostaParcial) {
            return Collections.emptyList();
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
     * @return Lista com os identificadores de ordem das
     * entradas na CID-10 que satisfazem os critérios fornecidos.
     */
    public List<String> encontre(String[] criterios) {

        String[] ajustados = ajustaCriterios(criterios);

        if (ajustados.length == 0) {
            return Collections.emptyList();
        }

        List<Integer> resultado = new ArrayList<>();

        String primeiro = ajustados[0];
        int total = busca.size();
        for (int i = 0; i < total; i++) {
            // TODO estender busca para além da CID, se for o caso.
            // TODO busca.get(i) acrescentada de sinônimo e característica,
            //  conforme requisição
            if (busca.get(i).contains(primeiro)) {
                resultado.add(i);
            }
        }

        for (int i = 1; i < ajustados.length; i++) {
            resultado = consultaEm(ajustados[i], resultado);
        }

        return codigos(resultado);
    }

    private List<String> codigos(List<Integer> lista) {
        List<String> strs = new ArrayList<>(lista.size());

        lista.forEach(i -> strs.add(original.get(i)));

        return strs;
    }

    /**
     * Prepara novos critérios a partir daqueles recebidos. Entre as operações
     * está a conversão para minúsculas e a exclusão de acentos.
     *
     * @param criterios Critérios conforme recebidos.
     *
     * @return Novo vetor, obtido a partir daquele fornecido, onde cada
     * critério é "ajustado". Os ajustes incluem: (a) conversão para
     * minúscula; (b) remoção de acentos; (c) ignora caracteres além de
     * {@link #MAX_SIZE_CRITERIO} e (d) ignora critérios além do total máximo
     * considerado ({@link #MAX_CRITERIOS}).
     */
    private static String[] ajustaCriterios(String[] criterios) {
        final int totalCriterios = Math.min(criterios.length, MAX_CRITERIOS);
        final String[] criteriosAjustados = new String[totalCriterios];

        for (int i = 0; i < totalCriterios; i++) {
            final int maxSize = Math.min(criterios[i].length(), MAX_SIZE_CRITERIO);
            String delimitado = criterios[i].substring(0, maxSize);
            String miniscula = delimitado.toLowerCase();
            criteriosAjustados[i] = removeAcentos(miniscula);
        }

        return criteriosAjustados;
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

    /**
     * Após chamada deste método não se espera nenhuma outra operação com o
     * objeto em questão. Adicionalmente, a expectativa é que este método
     * seja chamado implicitamente (veja try-with-resources).
     */
    @Override
    public void close() {
        busca.clear();
        busca = null;

        original.clear();
        original = null;

        capitulos.clear();
        capitulos = null;
    }
}
