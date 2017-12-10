/*
 * Copyright (c) 2016
 *
 * Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 *
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.bsus.cid10.preprocessor;

import org.junit.Test;

import java.io.FileNotFoundException;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class TransformadorTest {

    @Test
    public void totalDeEntradas() throws FileNotFoundException {
        Transformador sub = Transformador.newInstance("cid10.json");
        assertEquals(15672, sub.codigo.length);
    }
}