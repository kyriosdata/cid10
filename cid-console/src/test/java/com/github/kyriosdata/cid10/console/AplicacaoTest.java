package com.github.kyriosdata.cid10.console;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AplicacaoTest {

    @Test
    void semArgumentosRetornaUm() {
        assertEquals(1, Aplicacao.start(new String[0]));
    }
}
