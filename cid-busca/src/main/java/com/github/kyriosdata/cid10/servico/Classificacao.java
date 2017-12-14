package com.github.kyriosdata.cid10.servico;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Classificacao {

    @RequestMapping(value = "/capitulos", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String capitulos() {
        return "títulos de todos os capítulos";
    }

    @RequestMapping(value="/capitulos/{capitulo}", method =RequestMethod.GET)
    public String capitulos(@PathVariable int capitulo) {
        return"os grupos do capitulo "+ capitulo;
    }

    @RequestMapping(value = "/grupos", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String[] grupos() {

        return new String[]{ "ok", "erro" };
    }
}
