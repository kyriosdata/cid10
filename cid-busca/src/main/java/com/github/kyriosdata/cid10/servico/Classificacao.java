
/*
 *
 *  Copyright (c) 2018
 *
 *  Fábio Nogueira de Lucena
 *  Fábrica de Software - Instituto de Informática (UFG)
 *
 */

package com.github.kyriosdata.cid10.servico;

import com.github.kyriosdata.cid10.busca.Cid;
import com.github.kyriosdata.cid10.servico.modelo.Codigo;
import com.github.kyriosdata.cid10.servico.modelo.Grupo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class Classificacao {

    /**
     * Classe que oferece acesso ao conteúdo da CID-10 (inclusive busca).
     */
    private Cid cid;

    /**
     * Lista contendo todas as entradas da CID-10.
     * Sobre esta lista são feitas as operações de
     * consulta.
     */
    private List<Codigo> codigos;

    /**
     * Lista de capítulos da CID-10.
     */
    private String capitulos;

    /**
     * Lista de grupos da CID-10 por capítulo. Inclui grupos da
     * CID-10 oncologia.
     */
    private Map<Integer, List<Grupo>> grupos;

    public Classificacao() {
        cid = new Cid();
    }

    /**
     * Recupera todos os capítulos da CID-10.
     *
     * @return Os capítulos da CID-10.
     */
    @GetMapping(value = "/capitulos")
    public List<String> capitulos() {

        return cid.capitulos();
    }

    /**
     * Recupera a lista de grupos contidos em um dado capítulo.
     *
     * @param capitulo O número do capítulo.
     *
     * @return A lista de grupos contida no capítulo.
     */
    @GetMapping(value="/grupos/{capitulo}")
    public List<Grupo> grupos(@PathVariable int capitulo) {
        Grupo g = new Grupo();
        g.ci = "A00";
        g.cf = "A09";
        g.descricao = "Descrição do grupo";

        List<Grupo> grupos = new ArrayList<>();
        grupos.add(g);

        return grupos;
    }

    /**
     * Recupera a lista de categorias em uma dada faixa de categorias.
     * Este método permite recuperar todas as categorias de um grupo.
     *
     * @param faixa A faixa no formato LDD-LDD, onde L é uma letra e D
     *              é um dígito.
     *
     * @return Todas as categorias incluídas na faixa (inclusive os limites).
     */
    @GetMapping(value = "/categorias/{faixa}")
    public List<Codigo> categorias(@PathVariable String faixa) {
        Codigo c = new Codigo();
        c.codigo = "A00";
        c.descrica = "A descrição da categoria";
        c.sexo = '-';

        List<Codigo> categorias = new ArrayList<>();
        categorias.add(c);

        return categorias;
    }

    /**
     * Recupera as subcategorias de uma dada categoria.
     *
     * @param categoria O código de uma categoria (três dígitos).
     *
     * @return A lista de subcategorias da categoria informada.
     */
    @GetMapping(value = "/subcategorias/{categoria}")
    public List<Codigo> subcategorias(@PathVariable String categoria) {
        Codigo c = new Codigo();
        c.codigo = "B00";
        c.descrica = "subcategoria";
        c.sexo = 'M';

        List<Codigo> subcategorias = new ArrayList<>();
        subcategorias.add(c);

        return subcategorias;
    }

    /**
     * Recupera categorias e/ou subcategorias compatíveis com as palavras
     * fornecidas.
     *
     * @param palavras Uma ou mais palavras que serão empregadas na busca
     *                 de categorias e/ou subcategorias correspondentes.
     *
     * @return A lista de categorias e/ou subcategorias correspondentes à
     * busca realizada com base nas palavras fornecidas. De forma simplificada,
     * cada item retornado contém em sua descrição as palavras fornecidas.
     * Os códigos dos itens retornados também são consultados nessa busca.
     */
    @GetMapping(value = "/busca/{palavras}/{bloco}")
    public List<String> busca(@PathVariable String palavras, @PathVariable int bloco) {
        System.out.println(bloco);
        String[] words = palavras.split(" ");
        return cid.encontre(words);
    }
}
