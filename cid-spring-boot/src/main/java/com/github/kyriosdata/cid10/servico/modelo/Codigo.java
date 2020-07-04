package com.github.kyriosdata.cid10.servico.modelo;

/**
 * Representa uma categoria ou subcategoria, que definem o escopo de busca
 * na CID-10.
 *
 * <p>Uma subcategoria possui restrição do uso do código por sexo. O que
 * foi codificado da seguinte forma: (a) '-' (independe); (b) 'M' (masculuno)
 * e (c) 'F' (feminino).</p>
 */
public class Codigo {
    public String codigo;
    public String descricao;
    public char sexo;
}
