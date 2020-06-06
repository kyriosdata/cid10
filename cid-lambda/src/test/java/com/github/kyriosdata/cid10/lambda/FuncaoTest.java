package com.github.kyriosdata.cid10.lambda;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FuncaoTest {

    @Test
    void nenhumaSequencia() {
        assertEquals("[]", Funcao.toJson(Collections.emptyList()));
    }

    @Test
    void umaSequencia() {
        assertEquals("['a']", Funcao.toJson(List.of("a")));
    }

    @Test
    void duasSequencias() {
        assertEquals("['a', 'b']", Funcao.toJson(List.of("a", "b")));
    }

    @Test
    void sequenciaVazia() {
        final String recuperada = new Funcao().handleRequest("", null);
        assertEquals("[]", recuperada);
    }

    @Test
    void entradaNull() {
        final String resultado = new Funcao().handleRequest(null, null);
        assertEquals("[]", resultado);
    }

    @Test
    void dengue() {
        final String entrada = "0 dengue 90";
        final String recuperada = new Funcao().handleRequest(entrada, null);

        assertTrue(recuperada.contains("A90"));
    }
}
