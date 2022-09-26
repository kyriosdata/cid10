package com.github.kyriosdata.cid10.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardTokenizer;

/**
 * Realiza as seguintes operações: (a) converte para minúscula;
 * (b) remove stop words; (c) remove acentos;
 */
public class Cid10Analyzer extends Analyzer {

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        final StandardTokenizer src = new StandardTokenizer();
        TokenStream result = new LowerCaseFilter(src);
        result = new RemoveAcentosFilter(result);
        return new TokenStreamComponents(src, result);
    }
}
