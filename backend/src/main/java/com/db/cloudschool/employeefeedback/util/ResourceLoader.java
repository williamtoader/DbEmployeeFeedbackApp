package com.db.cloudschool.employeefeedback.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ResourceLoader {

    /**
     * Gets specified resource amd reads it as String
     * @param fileName Relative path to the file located in resources folder
     * @return File contents concatenated line-by-line using default system line separator character
     */
    public static String getResourceFileAsString(String fileName) {
        InputStream is = getResourceFileAsInputStream(fileName);
        if (is != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } else {
            throw new RuntimeException("resource not found");
        }
    }

    /**
     * Gets input stream to specified resource file in class path
     * @param fileName Relative path to the file located in resources folder
     * @return Input stream of specified resource
     */
    public static InputStream getResourceFileAsInputStream(String fileName) {
        ClassLoader classLoader = ResourceLoader.class.getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }
}
