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

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Aplicacao {
    public static void main(String[] args) throws Exception {
        Path grupos = FileFromResourcesFolder.getPath("datasus/CID-10-GRUPOS.CSV");
        List<String> linhas = Files.readAllLines(grupos, Charset.forName("ISO-8859-1"));
        linhas.forEach(l -> System.out.println(l));
    }
}
