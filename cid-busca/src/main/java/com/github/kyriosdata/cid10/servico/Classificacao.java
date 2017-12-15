package com.github.kyriosdata.cid10.servico;

import com.github.kyriosdata.cid10.servico.modelo.Capitulo;
import com.github.kyriosdata.cid10.servico.modelo.Codigo;
import com.github.kyriosdata.cid10.servico.modelo.Grupo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Classificacao {

    /**
     * Recupera todos os capítulos da CID-10.
     *
     * @return Os capítulos da CID-10.
     */
    @RequestMapping(value = "/capitulos", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Capitulo> capitulos() {
        Capitulo c = new Capitulo();
        c.num = 1;
        c.ci = "A00";
        c.cf = "B99";
        c.descricao = "Algumas doenças infecciosas e parasitárias";
        List<Capitulo> capitulos = new ArrayList<>();
        capitulos.add(c);

        return capitulos;
    }

    /**
     * Recupera a lista de grupos contidos em um dado capítulo.
     *
     * @param capitulo O número do capítulo.
     *
     * @return A lista de grupos contida no capítulo.
     */
    @RequestMapping(value="/grupos/{capitulo}", method =RequestMethod.GET)
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
    @RequestMapping(value = "/categorias/{faixa}", method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
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
    @RequestMapping(value = "/subcategorias/{categoria}", method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
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
     * busca realizada com base nas palavras fornecidas.
     */
    @RequestMapping(value = "/busca/{palavras}", method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Codigo> busca(@PathVariable String palavras) {
        List<Codigo> resposta = new ArrayList<>();
        return resposta;
    }
}
