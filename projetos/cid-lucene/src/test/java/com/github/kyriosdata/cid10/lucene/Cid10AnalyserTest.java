package com.github.kyriosdata.cid10.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class Cid10AnalyserTest {

    private static final String FIELD = "descricao";

    @Test
    public void quandoTransformacao_quebraCorreta() throws IOException {
        List<String> result = analyze("Febre paratifóide B", new Cid10Analyzer());

        assertThat(result, contains("febre", "paratifoide", "b"));
    }

    @Test
    public void hifenSeparado() throws IOException {
        List<String> result = analyze("pós-cirúrgicos", new Cid10Analyzer());

        assertThat(result, contains("pos", "cirurgicos"));
    }

    @Test
    public void termoComVariacoes() throws IOException {
        List<String> result = analyze("A vida É bem-vinda 110", new Cid10Analyzer());

        assertThat(result, contains("a", "vida", "e", "bem", "vinda", "110"));
    }

    public List<String> analyze(String text, Analyzer analyzer) throws IOException {
        List<String> result = new ArrayList<String>();
        TokenStream tokenStream = analyzer.tokenStream(FIELD, text);
        CharTermAttribute attr = tokenStream.addAttribute(CharTermAttribute.class);
        tokenStream.reset();
        while (tokenStream.incrementToken()) {
            result.add(attr.toString());
        }
        return result;
    }

}
