package com.github.kyriosdata.cid10.lucene;

import org.apache.lucene.analysis.CharacterUtils;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.text.Normalizer;

public class RemoveAcentosFilter extends TokenFilter {
    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    /**
     * Cria filtro que remove acentos.
     *
     * @param in TokenStream to filter
     */
    public RemoveAcentosFilter(TokenStream in) {
        super(in);
    }

    @Override
    public final boolean incrementToken() throws IOException {
        if (!input.incrementToken()) {
            return false;
        }

        char[] buffer = termAtt.buffer();

        String semSinal = removeSinais(new String(buffer,0, termAtt.length()));
        char[] newBuffer = semSinal.toCharArray();

        termAtt.setEmpty();
        termAtt.copyBuffer(newBuffer, 0, newBuffer.length);
        return true;
    }

    /**
     * Palavras serão consultadas sem sinais ou acentos
     */
    private static String removeSinais(String entrada) {
        String sa = Normalizer.normalize(entrada, Normalizer.Form.NFD);

        return sa.replaceAll("\\p{M}", "");
    }
}

