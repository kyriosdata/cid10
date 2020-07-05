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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Textos adicionais, ou descrições, associadas a entradas da CID.
 * O objetivo é permitir que a busca possa ser feita, além do código e da
 * descrição de uma CID, também pelo "texto adicional". Por exemplo,
 * o texto adicional "Síndrome do desfiladeiro" pode ser associado à entrada
 * G540 e, consequentemente, a busca por "desfilad" permitirá a recuperação
 * da G540.
 */
public class DescricaoAdicional {

    /**
     * Empregado para recuperar texto original de um texto associado
     * a uma CID. Por exemplo, para a chave é "G540" o valor pode ser
     * "Síndrome do desfiladeiro".
     */
    private Map<String, String> base;

    /**
     * Empregado para realizar a busca sobre o texto ajustado,
     * obtido a partir do original. O texto ajustado não inclui
     * acentos, apenas minúsculas e outros. Por exemplo, para a chave
     * "G540" o valor pode ser "sindrome desfiladeiro", onde acento
     * é removido, apenas letras minúsculas e "de" (stopword) foi
     * removida.
     */
    private Map<String, String> busca;

    public String original(final String cid) {
        return base.get(cid);
    }

    public String busca(final String cid) {
        return busca.get(cid);
    }

    /**
     * Localiza entradas da CID para as quais as descrições adicionais
     * fornecidas satisfazem o critério indicado.
     *
     * @param criterio Texto a ser localizado nas descrições adicionais.
     *
     * @return Entradas da CID (códigos) para as quais as descrições
     * adicionais correspondentes contêm o texto (critério) fornecido.
     */
    public List<String> encontre(final String criterio) {
        return busca.entrySet().stream()
                .filter(e -> e.getValue().contains(criterio))
                .map(s -> s.getKey()).collect(Collectors.toList());
    }

    /**
     * Recupera a descrição adicional disponível para a entrada fornecida.
     * O formato do arquivo CSV tem como objetivo o desempenho da
     * leitura e montagem da estrutura de dados necessária para busca
     * eficiente.
     *
     * @param is A entrada a partir da qual uma descrição adicional será
     *           recuperada no formato CSV.
     *
     * @return A instância correspondente aos dados obtidos da entrada
     * fornecida.
     */
    public static DescricaoAdicional fromCsv(final InputStream is) {
        DescricaoAdicional descricao = new DescricaoAdicional();
        descricao.base = new HashMap<>();
        descricao.busca = new HashMap<>();

        InputStreamReader isr = new InputStreamReader(is,
                StandardCharsets.UTF_8);
        try (BufferedReader br = new BufferedReader(isr)) {
            String first;
            while ((first = br.readLine()) != null) {
                // Primeira linha (base)
                final int separador = first.indexOf(";");
                final String cid = first.substring(0, separador);
                descricao.base.put(cid, first.substring(separador + 1));

                // Segunda linha (busca)
                final String second = br.readLine();
                descricao.busca.put(cid, second.substring(separador + 1));
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("erro ao ler stream");
        }

        return descricao;
    }
}
