package com.github.kyriosdata.cid10.console;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LambdaFunctionTest {

    @Test
    void semArgumentosRetornaUm() {
        assertEquals(1, LambdaFunction.start(new String[0]));
    }
}
