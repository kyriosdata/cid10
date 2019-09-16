/*
 * Copyright (c) 2019
 *
 * Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 *
 * Creative Commons Attribution 4.0 International License.
 *
 */
package com.github.kyriosdata.cid10.busca;

import java.util.List;

/**
 * Interface a ser implementada por serviço que carrega estrutura de dados da
 * CID-10.
 */
public interface CarregaDados {

    /**
     * Carrega dados da CID-10.
     *
     * @param identificador Identificador dos dados a serem carregados.
     *
     * @return Lista de sequências de caracteres (linhas) dos dados
     * correspondentes ao identificador fornecido.
     */
    List<String> getLinhas(String identificador);
}
