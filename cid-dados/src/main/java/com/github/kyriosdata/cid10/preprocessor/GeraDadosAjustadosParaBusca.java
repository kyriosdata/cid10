/*
 *
 *  * Copyright (c) 2017
 *  *
 *  * Fábio Nogueira de Lucena
 *  * Fábrica de Software - Instituto de Informática (UFG)
 *  *
 *  * Creative Commons Attribution 4.0 International License.
 *
 *
 */

package com.github.kyriosdata.cid10.preprocessor;

import java.util.*;

/**
 * Produz arquivos contendo versão da CID-10 utilizada no processo
 * de busca. Os arquivos produzidos pelo presente programa são obtidos
 * a partir dos arquivos gerados pela classe
 * {@link GeraOriginalAjustado}.
 * <p>
 * <p>Várias alterações são feitas para "otimizar" a busca.</p>
 */
public class GeraDadosAjustadosParaBusca {
    public static void main(String[] args) {
        List<String> busca = new ArrayList<>();
        List<String> codes = ArquivoUtils.carrega("./", "codigos.csv");

        codes.forEach(l -> {
            String nl = l;

            // Minúsculas apenas
            nl = l.toLowerCase();

            // Eliminar vírgulas
            nl = nl.replaceAll(",", "");

            // Plural simples, troca "(s)" por "s" e "(es)" por "es"
            nl = nl.replaceAll("\\(s\\)", "s");
            nl = nl.replaceAll("\\(es\\)", "es");

            // Troca colchete e parêntese por espaço
            nl = nl.replaceAll("[\\[\\]\\(\\)]", " ");

            // Troca hífen por espaço
            nl = nl.replaceAll("-", " ");

            // Remove acentos
            nl = removeSinais(nl);

            // Troca aspa por espaço
            nl = nl.replaceAll("\"", " ");

            // Elimina preposições, artigos,...
            nl = eliminaAlgumasPalavras(nl);

            // Troca dois ou mais espaços por apenas um espaço
            nl = nl.replaceAll("[ ]{2,}", " ");

            // Coluna sexo não empregada na busca
            String[] campos = nl.split(";");
            campos[0] = campos[0].trim();
            campos[2] = campos[2].trim();
            nl = String.format("%s;%s", campos[0], campos[2]);

            busca.add(nl);
        });

        ArquivoUtils.armazena(busca, "./", "busca.csv");
    }

    /**
     * Assume palavras apenas com letras minúsculas.
     */
    private static String removeSinais(String entrada) {
        String comAcentos = "äáâàãéêëèíîïìöóôòõüúûùçª";
        String semAcentos = "aaaaaeeeeiiiiooooouuuuca";

        final int SIZE = comAcentos.length();

        String saida = entrada;

        for (int j = 0; j < SIZE; j++) {
            String acento = "" + comAcentos.charAt(j);
            String semAcento = "" + semAcentos.charAt(j);
            saida = saida.replaceAll(acento, semAcento);
        }

        return saida;
    }

    private static String eliminaAlgumasPalavras(String entrada) {
        List<String> paraRemover = Arrays.asList(new String[]{
                " de ", " da ", " das ", " do ", " dos ",
                " a ", " as ", " e ", " o ", " os ",
                " na ", " nas ", " no ", " nos ",
                " para ",
                " que ", " com ", " ou ",
                " em ", " por "
        });

        String saida = entrada;

        for (int i = 0; i < paraRemover.size(); i++) {
            for (String eliminar : paraRemover) {
                if (saida.contains(eliminar)) {
                    saida = saida.replaceAll(eliminar, " ");
                }
            }
        }

        return saida;
    }
}
