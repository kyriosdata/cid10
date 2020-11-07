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

import com.github.kyriosdata.cid10.busca.Busca;

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
 * Gerador de estrutura de dados projetada para a eficiência da busca.
 * Esta estrutura de dados DEVE permitir a localização eficiente de entradas
 * da CID, a partir de códigos e da descrição de cada entrada. Naturalmente,
 * a estrutura deve incluir todas as entradas correspondentes aos critérios
 * da busca e apenas estes.
 *
 * <p><b>IMPORTANTE:</b> dados gerados pelo presente programa são necessários
 * para a execução dos testes da classe
 * {@link Busca}.</p>
 *
 * <p>A intenção é eliminar dados que não serão
 * empregadas durante a execução, o que reduz o tempo necessário
 * para "montagem" das estruturas de dados visando a eficiência da busca.
 * Ou seja, parte das operações são feitas pela presente classe, e não
 * em tempo de execução do serviço.</p>
 */
public class GeraOriginalAjustado {

    public static final String IN_DIR = "datasus/";
    public static final String OUT_DIR = "./src/main/resources/cid/";

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
    public static final String OUT_BUSCA = "busca.csv";

    /**
     * Aplicação desenvolvida para converter os dados da
     * CID-10, do formato fornecido pelo DATASUS (original) para aquele
     * projetado para a busca.
     *
     * <p>Os dados são obtidos de arquivos com nomes padronizados:
     * {@link #CAPITULOS}, {@link #GRUPOS} e assim por diante.
     * </p>
     *
     * <p>Os diretórios onde os arquivos padrão são obtidos e os resultados
     * correspondentes depositados são indicados por
     * {@link #IN_DIR} e {@link #OUT_DIR}, respectivamente, caso não sejam
     * fornecidos como argumentos.
     * </p>
     *
     * @param args Nenhum argumento é esperado.
     * @throws Exception Operações com leitura e escrita de arquivos podem
     *                   gerar exceções.
     */
    public static void main(String[] args) throws Exception {
        final boolean argsFornecidos = args.length == 2;
        String input = argsFornecidos ? args[0] : IN_DIR;
        String output = argsFornecidos ? args[1] : OUT_DIR;

        String inputMsg = String.format("\nDiretório de entrada: %s", input);
        String outputMsg = String.format("\nDiretório de saíde: %s", output);
        System.out.println(inputMsg);
        System.out.println(outputMsg);

        gerador(input, output);
        geraEstruturaParaBusca(output);
    }

    public static void gerador(final String inDir, final String outDir) throws Exception {

        preparaDiretorio(outDir);

        // Capítulos
        Path capitulosCsv = getPath(inDir, CAPITULOS);
        List<String> chapters = processaCapitulos(capitulosCsv);
        Path chapter = Paths.get(outDir, OUT_CAPITULOS);
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
        final Path gruposCsv = getPath(inDir, GRUPOS);
        List<String> groups = processaGrupo(gruposCsv);
        armazena(groups, Paths.get(outDir, OUT_GRUPOS));

        // Grupos oncologia
        Path gruposOncologiaCsv = getPath(inDir, GRUPOS_ONCOLOGIA);
        List<String> go = processaGrupo(gruposOncologiaCsv);
        armazena(go, Paths.get(outDir, OUT_GO));

        // Códigos = Categorias + Subcategorias + Categorias da oncologia
        final Path categoriasCsv = getPath(inDir, CATEGORIAS);
        List<String> categorias = processaCategorias(categoriasCsv);

        final Path subcategoriasCsv = getPath(inDir, SUBCATEGORIAS);
        List<String> subcategorias = processaSubcategorias(subcategoriasCsv);

        final Path categoriasOncologiaCsv = getPath(inDir,
                CATEGORIAS_ONCOLOGIA);
        List<String> co = processaCategoriasOncologia(categoriasOncologiaCsv);

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

        // Elimina o ; ao final de cada linha
        List<String> pronta = new ArrayList<>(parcial.size());
        parcial.forEach(l -> {
            pronta.add(l.substring(0, l.length() - 1));
        });

        armazena(pronta, Paths.get(outDir, OUT_CODIGOS));
    }

    /**
     * Prepara o diretório onde serão depositados os arquivos contendo a
     * estrutura de dados produzida para busca eficiente.
     *
     * @param outDir Diretório a ser criado, caso não exista. Se o diretório
     *               indicado já existe, então será removido (inclusive seu
     *               conteúdo, arquivos e subdiretórios) e um novo será criado.
     * @throws IOException Se não for possível criar o diretório indicado.
     */
    private static void preparaDiretorio(String outDir) throws IOException {
        Path outPath = Paths.get(outDir);

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

    private static List<String> processaCategoriasOncologia(Path csv) {
        List<String> linhas = getLinhas(csv);

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

    private static List<String> processaCapitulos(Path csv) {
        List<String> linhas = getLinhas(csv);

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

    private static List<String> processaGrupo(Path csv) {
        List<String> linhas = getLinhas(csv);

        // Remove header (nomes das colunas)
        linhas.remove(0);

        List<String> processadas = new ArrayList<>(linhas.size());

        linhas.forEach(l -> {
            String linhaPreprocessada = excluiColuna(l, 3);
            processadas.add(linhaPreprocessada);
        });

        return processadas;
    }

    private static List<String> processaSubcategorias(Path csv) {
        List<String> linhas = getLinhas(csv);

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
     * @param csv
     */
    private static List<String> processaCategorias(Path csv) {
        List<String> linhas = getLinhas(csv);

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
     * @param linha  Linha CSV cujo delimitador é ";".
     * @param ignora Número da coluna a ser ignorada (zero-based).
     * @return A linha CSV inicialmente fornecida após a exclusão da coluna a
     * ser ignorada.
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
     * @param csv
     * @return Lista de linhas correspondentes ao conteúdo do arquivo CSV.
     */
    public static List<String> getLinhas(Path csv) {
        List<String> linhas = null;
        try {
            linhas = Files.readAllLines(csv, StandardCharsets.ISO_8859_1);
        } catch (Exception exp) {
            System.err.println(exp.toString());
        }

        return linhas;
    }

    private static Path getPath(String inDir, String entrada) {
        return ArquivoUtils.getPath(inDir + entrada);
    }

    public static void geraEstruturaParaBusca(String outDir) {
        List<String> busca = new ArrayList<>();

        Path path = Paths.get(outDir, OUT_CODIGOS);
        List<String> codes = ArquivoUtils.carrega(path);

        codes.forEach(l -> {
            String nl = l;
            nl = ajusta(l);

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

        Path pathBusca = Paths.get(outDir, OUT_BUSCA);
        armazena(busca, pathBusca);
    }

    private static String ajusta(String entrada) {
        // Minúsculas apenas
        String nl = entrada.toLowerCase();

        // Eliminar vírgulas
        nl = nl.replaceAll(",", " ");

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
        return nl;
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
