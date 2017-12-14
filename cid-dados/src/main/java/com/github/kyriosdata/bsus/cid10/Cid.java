/*
 * Copyright (c) 2017
 *
 * Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 *
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.bsus.cid10;

/**
 * Operações para acesso às informações da CID-10.
 *
 * <p>Um objeto que implementa essa interface possui um
 * ciclo de vida formado pela chamada ao método {@link #load()},
 * que prepara o objeto para requisições e pelo método
 * {@link #unload()}, que indica não mais interesse por
 * requisições a serem dirigidas ao objeto. Dessa forma
 * o objeto pode se "ocupar" e "liberar", respectivamente,
 * recursos empregados durante as requisições.
 *
 * <p>As requisições ao objeto são oferecidas pelos
 * métodos {@link #code(String[])}, {@link #description(String[])}
 * e {@link #codeAndDescription(String, String[])}.
 *
 * <p>A implementação dessa interface indica a versão da CID-10 por
 * meio do método {@link #version()}.
 */
public interface Cid {

    /**
     * Executado uma única vez por instância que implementa
     * essa interface. Em geral esse método é chamado logo
     * após a criação do objeto.
     *
     * <p>Permite que a implementação prepare-se antes de receber
     * qualquer requisição ({@link #code(String[])}).
     */
    void load();

    /**
     * Método chamado sempre que o objeto em questão não irá
     * mais receber requisições ({@link #code(String[])}.
     *
     * <p>Permite que a implementação remova uso de recursos
     * empregado para a realização de consultas, o que não mais
     * irá ocorrer.
     */
    void unload();

    /**
     * Identifica códigos e as descrições correspondentes para os
     * critérios de busca fornecidos.
     *
     * @param codigo Código ou parte de um código a ser procurado.
     *
     * @return {@code null} se nenhuma entrada da CID-10 correspondente
     * ao código fornecido ou, caso contrário, uma ou mais entradas
     * correspondentes. Uma entrada é definida pelo código da entrada
     * seguido de espaço em branco e, na sequência, a descrição
     * correspondente.
     */
    String[] code(String[] codigo);

    /**
     * Identifica as entradas da CID-10 cujas descrições contêm os
     * trechos fornecidos.
     * @param textos Palavras ou parte de palavras que devem estar
     *               presentes nas descrições das entradas a serem
     *               retornadas.
     *
     * @return {@code null} caso nenhuma entrada possua em suas
     * descrições os textos indicados ou, caso contrário, uma ou mais
     * entradas correspondentes. Uma entrada é definida pelo código da entrada
     * seguido de espaço em branco e, na sequência, a descrição
     * correspondente.
     */
    String[] description(String[] textos);

    /**
     * Procura por uma entrada que possui um determinado código e uma descrição
     * que inclua os textos indicados.
     *
     * @param code Código ou parte do código da entrada.
     *
     * @param textos Palavras ou parte de palavras que devem estar
     *               presentes na descrição da entrada.
     *
     * @return {@code null} caso nenhuma entrada satisfaça os critérios
     * de busca indicados ou, caso contrário, uma ou mais entradas
     * correspondentes. Uma entrada é definida pelo código da entrada
     * seguido de espaço em branco e, na sequência, a descrição
     * correspondente.
     */
    String[] codeAndDescription(String code, String[] textos);

    /**
     * Recupera a versão da CID-10 utilizada.
     * @return A versão da CID-10 oferecida pelo objeto.
     */
    String version();
}
