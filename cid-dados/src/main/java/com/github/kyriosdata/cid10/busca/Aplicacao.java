package com.github.kyriosdata.cid10.busca;

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
     *             diante. A partir do segundo argumentos, todos eles serão
     *             considerados trechos que deverão fazer parte de toda
     *             linha (código CID-10) fornecida como resultado.
     */
    public static void main(String[] args) {
        // TODO tratar argumentos (quantidade)
        // TODO realizar testes
        // TODO retornar 0 em caso de sucesso e valor diferente em caso de erro
        int ordem = Integer.parseInt(args[0]);
        String[] criterios = Arrays.copyOfRange(args, 1, args.length);
        final CarregaDados carregador = new CarregaDadosFromJar();
        new Cid(carregador).encontre(criterios, ordem).forEach(System.out::println);
    }
}
