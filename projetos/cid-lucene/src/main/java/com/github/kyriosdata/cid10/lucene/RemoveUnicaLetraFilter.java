package com.github.kyriosdata.cid10.lucene;

import org.apache.lucene.analysis.FilteringTokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;

public class RemoveUnicaLetraFilter extends FilteringTokenFilter {
    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    public RemoveUnicaLetraFilter(TokenStream in) {
        super(in);
    }

    @Override
    protected boolean accept() throws IOException {
        return termAtt.length() > 1;
    }
}
