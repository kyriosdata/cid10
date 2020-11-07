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

import com.github.kyriosdata.cid10.busca.Busca;
import com.github.kyriosdata.cid10.busca.CarregaDados;
import com.github.kyriosdata.cid10.busca.Carregador;

import java.io.IOException;
import java.util.Arrays;

/**
 * Aplicação CLI para busca em entradas da CID-10.
 */
public class Aplicacao {
    /**
     * Executa busca sobre os dados da CID-10.
     *
     * @param args O primeiro argumento é a ordem a partir da qual os
     *             resultados serão fornecidos. A ordem 0 significa o
     *             primeiro elemento, a ordem 1 o segundo e assim por
     *             diante. A partir do segundo argumento, todos aqueles
     *             fornecidos serão considerados trechos que deverão fazer
     *             parte de toda linha (código CID-10) fornecida como resultado.
     */
    public static void main(String[] args) {
        System.exit(start(args));
    }

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

        Busca cid;
        try {
            final CarregaDados carregador = new Carregador();
            cid = new Busca(carregador);
        } catch (IOException exception) {
            System.out.println("Falha interna.");
            return 2;
        }

        final String criteriosBusca = String.join(" ", criterios);
        System.out.println("RESULTADO PARA: " + criteriosBusca);
        cid.encontre(criterios, ordem).forEach(System.out::println);
        return 0;
    }
}
