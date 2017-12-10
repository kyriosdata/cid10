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
import java.util.Arrays;
import java.util.List;

public class Aplicacao {
    public static void main(String[] args) throws Exception {
        List<String> linhas = getLinhas("datasus/CID-10-GRUPOS.CSV");

        linhas.forEach(l -> {
            String[] campos = l.split(";");
            List<String> listaCampos = Arrays.asList(campos);
            System.out.println(String.join(";", listaCampos));
        });
    }

    private String remontaLinha(String[] campos, int ignora) {
        String novaLinha = "";
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
    private static List<String> getLinhas(String entrada) throws IOException {
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
