/*
 * Copyright (c) 2016
 *
 * Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 *
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.bsus.cid10.preprocessor.json;

import java.util.ArrayList;
import java.util.List;

/**
 * Objeto que reúne o conteúdo da CID-10 (capítulos, categorias, grupos
 * e demais) sem "transformações", ou seja, o conteúdo "original".
 */
public class Cid {
    public List<String> codigo;
    public List<String> descricao;

    public Cid() {
        codigo = new ArrayList<>();
        descricao = new ArrayList<>();
    }
}
