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
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.github.kyriosdata.cid10.busca.CarregaDados;
import com.github.kyriosdata.cid10.busca.CarregaDadosFromJar;
import com.github.kyriosdata.cid10.busca.Cid;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * Conector de serviço de localização para AWS Lambda.
 */
public class Funcao implements RequestStreamHandler {

    private Cid cid;

    public Funcao() {
        try {
            final CarregaDados carregador = new CarregaDadosFromJar();
            cid = new Cid(carregador);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void handleRequest(InputStream inputStream,
                              OutputStream outputStream, Context context) throws IOException {
        String entrada = new String(inputStream.readAllBytes(),
                StandardCharsets.UTF_8);

        if (entrada.isEmpty()) {
            outputStream.write("entrada.isEmpty == true".getBytes());
            return;
        }

        String[] args = entrada.split(" ");
        String[] criterios = Arrays.copyOfRange(args, 1, args.length);
        int ordem;

        try {
            ordem = Integer.parseInt(args[0]);
        } catch (NumberFormatException formato) {
            final String fmt = "NumberFormatException %s %s";
            final String msg = String.format(fmt, entrada, args[0]);
            outputStream.write(msg.getBytes());
            return;
        }

        final List<String> entradas = cid.encontre(criterios, ordem);
        final String saida = String.join("\n", entradas);

        if (saida.isEmpty()) {
            outputStream.write(("saida vazia para: " + entrada).getBytes());
            return;
        }
        outputStream.write(saida.getBytes());
    }
}
