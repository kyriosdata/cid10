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

package com.github.kyriosdata.cid10.preprocessor;

import java.util.List;

/**
 * Produz arquivos contendo versão da CID-10 utilizada no processo
 * de busca. Os arquivos produzidos pelo presente programa são obtidos
 * a partir dos arquivos gerados pela classe
 * {@link GeraOriginalAjustado}.
 *
 * <p>Várias alterações são feitas para "otimizar" a busca:
 * (a) converter para minúsculas;
 * (b) eliminar vírgulas
 * (b) remover acentos;
 * (c) remover []</p>
 *
 */
public class GeraDadosAjustadosParaBusca {
    public static void main(String[] args) {
        List<String> codes = ArquivoUtils.carrega("./", "codigos.csv");

        codes.forEach(l -> {
            String nl = l;

            // Minúsculas apenas
            nl = l.toLowerCase();

            // Eliminar vírgulas
            nl = nl.replaceAll(",", "");

            // Troca colchete por espaço
            nl = nl.replaceAll("[\\[\\]]", " ");

            // Plural simples, troca "(s)" por "s" e "(es)" por "es"
            nl = nl.replaceAll("\\(s\\)", "s");
            nl = nl.replaceAll("\\(es\\)", "es");

            System.out.println(nl);
        });

        System.out.println(codes.size());
    }
}
