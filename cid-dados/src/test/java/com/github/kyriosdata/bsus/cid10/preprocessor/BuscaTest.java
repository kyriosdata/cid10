package com.github.kyriosdata.bsus.cid10.preprocessor;

import com.github.kyriosdata.bsus.cid10.preprocessor.json.Cid;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class BuscaTest {

    private static Transformador sc;

    // Dados "originais" sem transformações
    private static Cid c;

    private static List<String> sentencas;

    @BeforeClass
    public static void montagemIndice() throws FileNotFoundException {

        // Converte versão original em versão eficiente para consulta
        final String fileName = "cid10.json";
        sc = Transformador.newInstance(fileName);

        // Empregado exclusivamente para retornar resultado (sem transformações)
        c = FileFromResourcesFolder.getConteudo("cid10.json", Cid.class);

        sentencas = sc.getSentencas();
    }

    @Test
    public void analisaSentencas() {
        int maxSize = 0;
        for (String sentenca : sentencas) {
            if (sentenca.length() > maxSize) {
                maxSize = sentenca.length();
            }
        }

        assertTrue(maxSize < 256);
    }
}
