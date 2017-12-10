/*
 *
 *  Copyright (c) 2017
 *
 *  Fábio Nogueira de Lucena
 *  Fábrica de Software - Instituto de Informática (UFG)
 *
 *  Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.cid10.preprocessor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Aplicacao {
    public static void main(String[] args) throws Exception {
        String grupos = "datasus/CID-10-GRUPOS.CSV";
        String capitulos = "datasus/CID-10-CAPITULOS.CSV";

        excluiDescricaoAbreviada(capitulos, 4);
    }

    private static void excluiDescricaoAbreviada(String entrada, int ignora) {
        List<String> linhas = getLinhas(entrada);

        linhas.forEach(l -> {
            String linhaPreprocessada = excluiColuna(l, ignora);
            System.out.println(linhaPreprocessada);
        });
    }

    /**
     * Exclui coluna de uma linha CSV.
     *
     * @param linha Linha CSV, cujo delimitador é ";" cuja coluna
     *              indicada deve ser eliminada.
     *
     * @param ignora Número da coluna a ser ignorada (zero-based).
     *
     * @return A linha CSV inicialmente fornecida com a coluna
     * indicada excluída.
     */
    private static String excluiColuna(String linha, int ignora) {
        String novaLinha = "";
        String[] campos = linha.split((";"));
        int total = campos.length;
        for(int i = 0; i < total; i++) {
            if (i == ignora) {
                continue;
            }

            novaLinha += campos[i] + ";";
        }

        return novaLinha;
    }

    /**
     * Recupera lista de linhas de arquivo CSV.
     *
     * @param entrada Nome do arquivo CSV.
     *
     * @return Lista de linhas correspondentes ao conteúdo do arquivo CSV.
     *
     * @throws IOException
     */
    private static List<String> getLinhas(String entrada) {
        List<String> linhas = null;
        try {
            Path grupos = FileFromResourcesFolder.getPath(entrada);
            linhas = Files.readAllLines(grupos, StandardCharsets.ISO_8859_1);
        } catch (Exception exp) {
            System.err.println(exp.toString());
        }

        return linhas;
    }
}
