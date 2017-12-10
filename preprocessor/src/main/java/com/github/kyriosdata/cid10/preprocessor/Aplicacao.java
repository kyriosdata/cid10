/*
 *
 *  Copyright (c) 2017
 *
 *  Fábio Nogueira de Lucena
 *  Fábrica de Software - Instituto de Informática (UFG)
 *
 *  Creative Commons Attribution 4.0 International License.
 *
 *
 */

package com.github.kyriosdata.cid10.preprocessor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Aplicacao {
    public static void main(String[] args) throws Exception {
        List<String> linhas = getLinhas("datasus/CID-10-GRUPOS.CSV");

        linhas.forEach(l -> System.out.println(l));
    }

    private static List<String> getLinhas(String entrada) throws IOException {
        Path grupos = FileFromResourcesFolder.getPath(entrada);
        return Files.readAllLines(grupos, StandardCharsets.ISO_8859_1);
    }
}
