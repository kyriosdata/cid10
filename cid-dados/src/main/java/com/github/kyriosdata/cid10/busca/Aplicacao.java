package com.github.kyriosdata.cid10.busca;

import java.io.IOException;
import java.util.Arrays;

/**
 * Aplicação que faz uso do serviço de busca de dados na CID-10.
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
        if (args.length < 2) {
            System.out.println("USO: <ordem> <termos>");
            System.exit(1);
        }

        int ordem = 0;
        try {
            ordem = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe) {
            System.out.println("USO: <ordem> <termos>");
            System.out.printf("<ordem> deve ser um número inteiro");
            System.exit(1);
        }

        String[] criterios = Arrays.copyOfRange(args, 1, args.length);

        Cid cid = null;
        try {
            final CarregaDados carregador = new CarregaDadosFromJar();
            cid = new Cid(carregador);
        } catch (IOException exception) {
            System.out.println("Falha interna.");
            System.exit(2);
        }

        final String criteriosBusca = String.join(" ", criterios);
        System.out.println("RESULTADO PARA: " + criteriosBusca);
        cid.encontre(criterios, ordem).forEach(System.out::println);
        System.exit(0);
    }
}
