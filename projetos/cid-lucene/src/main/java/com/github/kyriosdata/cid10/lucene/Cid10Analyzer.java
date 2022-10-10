package com.github.kyriosdata.cid10.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.FilteringTokenFilter;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.br.BrazilianStemFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

import java.io.IOException;

/**
 * Realiza as seguintes operações: (a) converte para minúscula;
 * (b) remove stop words; (c) remove acentos;
 */
public class Cid10Analyzer extends Analyzer {

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        final StandardTokenizer src = new StandardTokenizer();
        TokenStream result = new LowerCaseFilter(src);
        result = new BrazilianStemFilter(result);
        result = new RemoveAcentosFilter(result);
        result = new RemoveUnicaLetraFilter(result);
        return new TokenStreamComponents(src, result);
    }
}
