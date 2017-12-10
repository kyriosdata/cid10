/*
 * Copyright (c) 2017
 *
 * Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 *
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.bsus.cid10.preprocessor;

import com.github.kyriosdata.bsus.cid10.preprocessor.json.Cid;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Aplicativo que gera os dois arquivos esperados do pré-processamento.
 * A saber: (a) arquivo "cid10.org" contendo versão original (utilizada
 * para produção de resposta e (b) arquivo "cid10.ser" contendo versão
 * empregada para as buscas.
 */
public class Gerador {

    public static void main(String[] args) throws IOException {
        Transformador t = Transformador.newInstance("cid10.json");
        List<String> sentencas = t.getSentencas();

        Files.write(Paths.get("./src/main/resources/cid10.ser"), sentencas);

        List<String> originais = new ArrayList<>();
        Cid c = FileFromResourcesFolder.getConteudo("cid10.json", Cid.class);
        for (int i = 0; i < c.codigo.size(); i++) {
            originais.add(c.codigo.get(i) + " " + c.descricao.get(i));
        }

        Files.write(Paths.get("./src/main/resources/cid10.org"), originais);
    }
}
