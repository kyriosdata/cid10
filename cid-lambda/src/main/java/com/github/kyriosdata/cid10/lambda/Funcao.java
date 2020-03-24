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
import com.github.kyriosdata.cid10.busca.CarregaDados;
import com.github.kyriosdata.cid10.busca.CarregaDadosFromJar;
import com.github.kyriosdata.cid10.busca.Cid;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Conector de serviço de localização para AWS Lambda.
 */
public class Funcao implements RequestHandler<String, String> {

    private Cid cid;

    public Funcao() {
        try {
            final CarregaDados carregador = new CarregaDadosFromJar();
            cid = new Cid(carregador);
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

        if (entrada.isEmpty()) {
            return "[]";
        }

        String[] args = entrada.split(" ");
        String[] criterios = Arrays.copyOfRange(args, 1, args.length);
        int ordem;

        try {
            ordem = Integer.parseInt(args[0]);
        } catch (NumberFormatException formato) {
            return "NumberFormatException " + entrada + " " + args[0];
        }

        final List<String> entradas = cid.encontre(criterios, ordem);
        return toJson(entradas);
    }
}
