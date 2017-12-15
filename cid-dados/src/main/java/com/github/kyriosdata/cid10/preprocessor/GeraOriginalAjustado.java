/*
 *
 *  Copyright (c) 2017
 *
 *  Fábio Nogueira de Lucena
 *  Fábrica de Software - Instituto de Informática (UFG)
 *
 *  Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.cid10.preprocessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Aplicação que gera em formato alternativo ao original, os dados
 * obtidos do DATASUS. A intenção é eliminar informações que não serão
 * empregadas durante a execução, além de reduzir o tempo necessário
 * para "montagem" das estruturas de dados para busca. Ou seja, parte
 * considerável das operações são feitas pela presente classe, e não
 * em tempo de execução do serviço.
 *
 */
public class GeraOriginalAjustado {

    public static final String DIR = "datasus/";
    public static final String CAPITULOS = "CID-10-CAPITULOS.CSV";
    public static final String GRUPOS = "CID-10-GRUPOS.CSV";
    public static final String CATEGORIAS = "CID-10-CATEGORIAS.CSV";
    public static final String SUBCATEGORIAS = "CID-10-SUBCATEGORIAS.CSV";
    public static final String GRUPOS_ONCOLOGIA = "CID-O-GRUPOS.CSV";
    public static final String CATEGORIAS_ONCOLOGIA = "CID-O-CATEGORIAS.CSV";

    public static final String OUT_CAPITULOS = "capitulos.csv";
    public static final String OUT_GRUPOS = "grupos.csv";
    public static final String OUT_GO = "go.csv";
    public static final String OUT_CODIGOS = "codigos.csv";

    public static void main(String[] args) throws Exception {

        // Capítulos
        List<String> chapters = processaCapitulos(CAPITULOS);
        FileFromResourcesFolder.armazena(chapters, "./", OUT_CAPITULOS);

        // Grupos
        List<String> groups = processaGrupo(GRUPOS);
        FileFromResourcesFolder.armazena(groups, "./", OUT_GRUPOS);

        // Grupos oncologia
        List<String> go = processaGrupo(GRUPOS_ONCOLOGIA);
        FileFromResourcesFolder.armazena(go, "./", OUT_GO);

        // Códigos = Categorias + Subcategorias + Categorias da oncologia
        List<String> categorias = processaCategorias(CATEGORIAS);
        List<String> subcategorias = processaSubcategorias(SUBCATEGORIAS);
        List<String> co = processaCategoriasOncologia(CATEGORIAS_ONCOLOGIA);

        // Realiza a união
        List<String> codigos = new ArrayList<>();
        codigos.addAll(categorias);
        codigos.addAll(subcategorias);
        codigos.addAll(co);

        // Ordena a lista resultante contendo os códigos
        Collections.sort(codigos);

        // Elimina espaço acrescentado para as categorias
        List<String> parcial = eliminaEspacoEmCategorias(codigos);

        FileFromResourcesFolder.armazena(parcial, "./", OUT_CODIGOS);
    }

    private static List<String> eliminaEspacoEmCategorias(List<String> codes) {
        List<String> ajustado = new ArrayList<>(codes.size());

        codes.forEach(l -> {
            String linhaAjustada = l;
            if (l.charAt(3) == ' ') {
                StringBuilder sb = new StringBuilder(l);
                sb.deleteCharAt(3);
                linhaAjustada = sb.toString();
            }

            ajustado.add(linhaAjustada);
        });

        return ajustado;
    }

    private static List<String> processaCategoriasOncologia(String arquivo) {
        return excluiColunaDeLinha(arquivo, 2);
    }

    private static List<String> processaCapitulos(String arquivo) {
        List<String> linhas = FileFromResourcesFolder.getLinhas(arquivo);
        List<String> saida = new ArrayList<>(linhas.size());

        linhas.forEach(l -> {
            String[] c = l.split(";");
            c[3] = c[3].replaceAll("Capítulo\\s.*\\s\\-\\s", "");
            String ln = String.format("%s;%s;%s;%s;", c[0], c[1], c[2], c[3]);
            saida.add(ln);
        });

        return saida;
    }

    private static List<String> processaGrupo(String arquivo) {
        return excluiColunaDeLinha(arquivo, 3);
    }

    private static List<String> processaSubcategorias(String arquivo) {
        List<String> linhas = FileFromResourcesFolder.getLinhas(arquivo);

        List<String> saida = new ArrayList<String>(linhas.size());

        linhas.forEach(l -> {
            String[] c = l.split(";");
            if (c[2].isEmpty()) {
                c[2] = "-";
            }

            String filtrada = String.format("%s;%s;%s;", c[0], c[2], c[4]);
            saida.add(filtrada);
        });

        return saida;
    }

    private static List<String> excluiColunaDeLinha(String entrada, int ignora) {
        List<String> linhas = FileFromResourcesFolder.getLinhas(entrada);

        List<String> processadas = new ArrayList<String>(linhas.size());

        linhas.forEach(l -> {
            String linhaPreprocessada = excluiColuna(l, ignora);
            processadas.add(linhaPreprocessada);
        });

        return processadas;
    }

    /**
     * Apenas as colunas 0 e 2 do arquivo CSV original são utilizadas,
     * a saber, categoria e descrição. Demais colunas são ignoradas.
     * A categoria é acrescida de um espaço em branco para facilitar
     * a ordenação lexicográfica. Este espaço é removido posteriormente.
     *
     * @param entrada Nome do arquivo contendo as categorias.
     */
    private static List<String> processaCategorias(String entrada) {
        List<String> linhas = FileFromResourcesFolder.getLinhas(entrada);

        List<String> processadas = new ArrayList<String>(linhas.size());

        linhas.forEach(l -> {
            String linhaPreprocessada = colunasZeroDois(l);
            processadas.add(linhaPreprocessada);
        });

        return processadas;
    }

    /**
     * Método que reúne as colunas (zero-based) de ordem zero e 2
     * da linha fornecida.
     *
     * @param linha A linha CSV contendo várias colunas.
     *
     * @return A linha CSV montada apenas com as colunas 0 e 2 da
     * linha de entrada.
     */
    private static String colunasZeroDois(String linha) {
        String[] campos = linha.split(";");
        return String.format("%s ;-;%s;", campos[0], campos[2]);
    }

    /**
     * Exclui coluna de uma linha CSV.
     *
     * @param linha Linha CSV, cujo delimitador é ";" cuja coluna
     *              indicada deve ser eliminada.
     *
     * @param ignora Número da coluna a ser ignorada (zero-based).
     *
     * @return A linha CSV inicialmente fornecida com a coluna
     * indicada excluída.
     */
    private static String excluiColuna(String linha, int ignora) {
        String novaLinha = "";
        String[] campos = linha.split((";"));
        int total = campos.length;
        for(int i = 0; i < total; i++) {
            if (i == ignora) {
                continue;
            }

            novaLinha += campos[i] + ";";
        }

        return novaLinha;
    }

}
