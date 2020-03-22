/*
 * Copyright (c) 2018
 *
 * Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 *
 * Creative Commons Attribution 4.0 International License.
 *
 */

package com.github.kyriosdata.cid10.console;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.github.kyriosdata.cid10.busca.CarregaDados;
import com.github.kyriosdata.cid10.busca.CarregaDadosFromJar;
import com.github.kyriosdata.cid10.busca.Cid;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * Conector de serviço de localização para AWS Lambda.
 */
public class LambdaFunction implements RequestStreamHandler {

    static int start(String[] args) {
        if (args.length < 2) {
            System.out.println("USO: <ordem> <termos>");
            System.out.println("\n\tExibe as entradas, a partir da ordem " +
                    "(valor inteiro)" +
                    "fornecida, nas quais estão presentes os termos " +
                    "fornecidos, sequências de letras e/ou números que " +
                    "devem fazer parte do código ou da descrição.\n");
            return 1;
        }

        int ordem;
        try {
            ordem = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe) {
            System.out.println("USO: <ordem> <termos>");
            System.out.println("<ordem> deve ser um número inteiro");
            return 1;
        }

        String[] criterios = Arrays.copyOfRange(args, 1, args.length);

        Cid cid;
        try {
            final CarregaDados carregador = new CarregaDadosFromJar();
            cid = new Cid(carregador);
        } catch (IOException exception) {
            System.out.println("Falha interna.");
            return 2;
        }

        final String criteriosBusca = String.join(" ", criterios);
        System.out.println("RESULTADO PARA: " + criteriosBusca);
        cid.encontre(criterios, ordem).forEach(System.out::println);
        return 0;
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        outputStream.write(inputStream.readAllBytes());
    }
}
