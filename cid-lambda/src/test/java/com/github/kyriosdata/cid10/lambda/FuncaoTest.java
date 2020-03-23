package com.github.kyriosdata.cid10.lambda;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FuncaoTest {

    @Test
    void sequenciaVazia() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        final String entrada = "";
        new Funcao().handleRequest(getFor(entrada), baos, null);

        final String recuperada = new String(baos.toByteArray(),
                StandardCharsets.UTF_8);

        assertEquals(entrada, recuperada);
    }

    @Test
    void primeiroArgumentoDeveSerNumero() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        final String entrada = "teste";
        new Funcao().handleRequest(getFor(entrada), baos, null);

        final String recuperada = new String(baos.toByteArray(),
                StandardCharsets.UTF_8);

        assertTrue(recuperada.isEmpty());
    }

    @Test
    void dengue() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        final String entrada = "0 dengue 90";
        new Funcao().handleRequest(getFor(entrada), baos, null);

        final String recuperada = new String(baos.toByteArray(),
                StandardCharsets.UTF_8);

        assertTrue(recuperada.contains("A90"));
    }

    private InputStream getFor(final String entrada) {
        return new ByteArrayInputStream(entrada.getBytes());
    }
}
