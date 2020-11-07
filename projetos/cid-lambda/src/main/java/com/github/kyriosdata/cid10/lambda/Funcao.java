/*
 * Copyright (c) 2018
 *
 * Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 *
 * Creative Commons Attribution 4.0 International License.
 *
 */

package com.github.kyriosdata.cid10.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.github.kyriosdata.cid10.busca.Busca;
import com.github.kyriosdata.cid10.busca.CarregaDados;
import com.github.kyriosdata.cid10.busca.Carregador;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Conector (wrapper) de serviço de localização de CID para AWS Lambda.
 */
public class Funcao implements RequestHandler<String, String> {

    /**
     * Tamanho máximo admitido para a entrada. Valores superiores serão
     * ignorados, a resposta será um vetor vazio.
     */
    public static final int MAX_SIZE_INPUT = 40;

    private Busca cid;

    public Funcao() {
        System.out.println("Versão: 0.0.0");
        try {
            final CarregaDados carregador = new Carregador();
            cid = new Busca(carregador);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Transforma lista de sequências de caracteres em um vetor destas
     * sequências em JSON.
     *
     * @param strs Lista de sequências de caracteres a ser convertida em
     *             vetor de sequências no formato JSON. Não pode ser
     *             {@code null}.
     * @return Vetor JSON contendo as sequências fornecidas na entrada.
     */
    public static String toJson(List<String> strs) {
        if (strs.isEmpty()) {
            return "[]";
        }

        StringBuilder json = new StringBuilder("[");

        final int ultimo = strs.size() - 1;
        for (int i = 0; i < ultimo; i++) {
            json.append("'");
            json.append(strs.get(i));
            json.append("', ");
        }

        json.append("'");
        json.append(strs.get(ultimo));
        json.append("']");

        return json.toString();
    }

    @Override
    public String handleRequest(String entrada, Context context) {
        if (entrada == null) {
            throw new IllegalArgumentException("entrada nula");
        }

        if (entrada.length() > MAX_SIZE_INPUT) {
            throw new IllegalArgumentException("tamanho maior que o máximo");
        }

        if (entrada.isEmpty()) {
            throw new IllegalArgumentException("entrada vazia");
        }

        // Registra em log (CloudWatch) a requisição recebida
        System.out.print("Recebido: ");
        System.out.println(entrada);

        // Primeiro argumento, se número, será a ordem a partir do qual
        // elementos serão retornados, caso contrário, a ordem será 0 e
        // será tratado como critério de busca.

        String[] args = entrada.split(" ");

        int ordem;
        try {
            ordem = Integer.parseInt(args[0]);
        } catch (NumberFormatException formato) {
            throw new IllegalArgumentException("primeiro argumento deve ser " +
                    "número");
        }

        String[] criterios = Arrays.copyOfRange(args, 1, args.length);
        final List<String> entradas = cid.encontre(criterios, ordem);
        return toJson(entradas);
    }
}
