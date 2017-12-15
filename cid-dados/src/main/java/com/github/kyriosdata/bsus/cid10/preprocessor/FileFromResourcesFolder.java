/*
 * Copyright (c) 2017
 *
 * Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 *
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.bsus.cid10.preprocessor;

import com.github.kyriosdata.cid10.preprocessor.GeraOriginalAjustado;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Classe utilitária para obter 'File' de arquivo incluído no
 * diretório 'resources'.
 */
public class FileFromResourcesFolder {

    public static File get(String fileName) {
        FileFromResourcesFolder obj = new FileFromResourcesFolder();
        ClassLoader classLoader = obj.getClass().getClassLoader();
        return new File(classLoader.getResource(fileName).getFile());
    }

    public static <T> T getConteudo(String fileName, Class<T> classOfT) throws FileNotFoundException {
        File capitulosJson = get(fileName);
        FileReader fileReader = new FileReader(capitulosJson);

        Gson gson = new Gson();
        return gson.fromJson(fileReader, classOfT);
    }

}
