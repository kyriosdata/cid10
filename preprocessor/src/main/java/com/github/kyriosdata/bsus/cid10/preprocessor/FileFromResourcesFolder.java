/*
 * Copyright (c) 2017
 *
 * Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 *
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.bsus.cid10.preprocessor;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

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
