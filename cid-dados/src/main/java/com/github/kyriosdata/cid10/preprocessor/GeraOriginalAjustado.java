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

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Aplicação que gera dados em formato alternativo ao original
 * obtido do DATASUS. Os dados são gerados no diretório "resources/cid".
 *
 * <p><b>IMPORTANTE:</b> dados gerados pelo presente programa são necessários
 * para a execução dos testes da classe {@link com.github.kyriosdata.cid10.busca.Cid}.</p>
 *
 * <p>A intenção é eliminar informações que não serão
 * empregadas durante a execução, além de reduzir o tempo necessário
 * para "montagem" das estruturas de dados para busca. Ou seja, parte
 * considerável das operações são feitas pela presente classe, e não
 * em tempo de execução do serviço.</p>
 */
public class GeraOriginalAjustado {

    public static final String DIR = "datasus/";
    public static final String CAPITULOS = "CID-10-CAPITULOS.CSV";
    public static final String GRUPOS = "CID-10-GRUPOS.CSV";
    public static final String CATEGORIAS = "CID-10-CATEGORIAS.CSV";
    public static final String SUBCATEGORIAS = "CID-10-SUBCATEGORIAS.CSV";
    public static final String GRUPOS_ONCOLOGIA = "CID-O-GRUPOS.CSV";
    public static final String CATEGORIAS_ONCOLOGIA = "CID-O-CATEGORIAS.CSV";

    public static final String OUT_DIR = "./src/main/resources/cid/";
    public static final String OUT_CAPITULOS = "capitulos.csv";
    public static final String OUT_GRUPOS = "grupos.csv";
    public static final String OUT_GO = "go.csv";
    public static final String OUT_CODIGOS = "codigos.csv";

    /**
     * Apenas para viabilizar geração automática via Maven.
     *
     * @param args Nenhum argumento é esperado.
     * @throws Exception Operações com leitura e escrita de arquivos podem
     * gerar exceções.
     */
    public static void main(String[] args) throws Exception {
        gerador();
        geraEstruturaParaBusca();
    }

    public static void gerador() throws Exception {

        preparaDiretorio();

        // Capítulos
        List<String> chapters = processaCapitulos(CAPITULOS);
        Path chapter = Paths.get(OUT_DIR, OUT_CAPITULOS);
        armazena(chapters, chapter);

        // Monta "cache" de capítulos
        final List<Capitulo> capitulos = new ArrayList<>(22);

        chapters.forEach(c -> {
            String[] campos = c.split(";");
            Capitulo cap = new Capitulo();
            cap.num = Integer.parseInt(campos[0]);
            cap.ci = campos[1];
            cap.cf = campos[2];
            cap.info = campos[3];
            capitulos.add(cap);
        });

        // Grupos
        List<String> groups = processaGrupo(GRUPOS);
        armazena(groups, Paths.get(OUT_DIR, OUT_GRUPOS));

        // Grupos oncologia
        List<String> go = processaGrupo(GRUPOS_ONCOLOGIA);
        armazena(go, Paths.get(OUT_DIR, OUT_GO));

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

        // Eliminar duplicidade de linha com mesma chave
        for (int i = 0; i < parcial.size() - 1; i++) {
            String item = parcial.get(i).split(";")[0];
            String next = parcial.get(i + 1).split(";")[0];
            if (item.equals(next)) {
                parcial.remove(i);
            }
        }

        armazena(parcial, Paths.get(OUT_DIR, OUT_CODIGOS));
    }

    private static void preparaDiretorio() throws IOException {
        Path outPath = Paths.get(OUT_DIR);

        if (Files.exists(outPath)) {
            deleteDir(outPath.toFile());
        }

        Files.createDirectories(outPath);
    }

    private static void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteDir(f);
            }
        }

        file.delete();
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
        List<String> linhas = getLinhas(arquivo);

        // Remove header (nomes das colunas)
        linhas.remove(0);

        List<String> saida = new ArrayList<>(linhas.size());

        linhas.forEach(l -> {
            String[] c = l.split(";");
            String ln = String.format("%s;-;%s;", c[0], c[1]);
            saida.add(ln);
        });

        return saida;
    }

    private static List<String> processaCapitulos(String arquivo) {
        List<String> linhas = getLinhas(arquivo);

        // Remove header (nomes das colunas)
        linhas.remove(0);

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
        List<String> linhas = getLinhas(arquivo);

        // Remove header (nomes das colunas)
        linhas.remove(0);

        List<String> processadas = new ArrayList<>(linhas.size());

        linhas.forEach(l -> {
            String linhaPreprocessada = excluiColuna(l, 3);
            processadas.add(linhaPreprocessada);
        });

        return processadas;
    }

    private static List<String> processaSubcategorias(String arquivo) {
        List<String> linhas = getLinhas(arquivo);

        // Remove header (nomes das colunas)
        linhas.remove(0);

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

    /**
     * Apenas as colunas 0 e 2 do arquivo CSV original são utilizadas,
     * a saber, categoria e descrição. Demais colunas são ignoradas.
     * A categoria é acrescida de um espaço em branco para facilitar
     * a ordenação lexicográfica. Este espaço é removido posteriormente.
     *
     * @param entrada Nome do arquivo contendo as categorias.
     */
    private static List<String> processaCategorias(String entrada) {
        List<String> linhas = getLinhas(entrada);

        // Remove header (nomes das colunas)
        linhas.remove(0);

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
     * @param linha  Linha CSV, cujo delimitador é ";" cuja coluna
     *               indicada deve ser eliminada.
     * @param ignora Número da coluna a ser ignorada (zero-based).
     * @return A linha CSV inicialmente fornecida com a coluna
     * indicada excluída.
     */
    private static String excluiColuna(String linha, int ignora) {
        String novaLinha = "";
        String[] campos = linha.split((";"));
        int total = campos.length;
        for (int i = 0; i < total; i++) {
            if (i == ignora) {
                continue;
            }

            novaLinha += campos[i] + ";";
        }

        return novaLinha;
    }

    /**
     * Recupera lista de linhas de arquivo CSV.
     *
     * @param entrada Nome do arquivo CSV.
     * @return Lista de linhas correspondentes ao conteúdo do arquivo CSV.
     */
    public static List<String> getLinhas(String entrada) {
        List<String> linhas = null;
        try {
            String fileName = DIR + entrada;
            Path grupos = ArquivoUtils.getPath(fileName);
            linhas = Files.readAllLines(grupos, StandardCharsets.ISO_8859_1);
        } catch (Exception exp) {
            System.err.println(exp.toString());
        }

        return linhas;
    }

    public static void geraEstruturaParaBusca() {
        List<String> busca = new ArrayList<>();

        Path path = Paths.get(OUT_DIR, "codigos.csv");
        List<String> codes = ArquivoUtils.carrega(path);

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

            // Troca ª por a
            nl = nl.replaceAll("ª", "a");

            // Troca aspa por espaço
            nl = nl.replaceAll("\"", " ");

            // Elimina preposições, artigos,...
            nl = eliminaAlgumasPalavras(nl);

            // Troca dois ou mais espaços por apenas um espaço
            nl = nl.replaceAll("[ ]{2,}", " ");

            // Coluna sexo não empregada na busca
            String[] campos = nl.split(";");
            if (campos.length < 3) {
                System.out.println(nl);
                System.out.println(l);
                return;
            }
            campos[0] = campos[0].trim();
            campos[2] = campos[2].trim();
            nl = String.format("%s;%s", campos[0], campos[2]);

            busca.add(nl);
        });

        Path pathBusca = Paths.get(OUT_DIR, "busca.csv");
        armazena(busca, pathBusca);
    }

    /**
     * Palavras serão consultadas sem sinais ou acentos
     */
    private static String removeSinais(String entrada) {
        String sa = Normalizer.normalize(entrada, Normalizer.Form.NFD);

        return sa.replaceAll("\\p{M}", "");
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

    public static void armazena(List<String> dados, Path path) {
        Charset charset = StandardCharsets.UTF_8;

        try {
            Files.write(path, dados, charset, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
