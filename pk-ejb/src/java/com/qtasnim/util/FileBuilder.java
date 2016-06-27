package com.qtasnim.util;

import java.io.*;

public class FileBuilder
{
    private static final String DEFAULT_TEMP_FOLDER = "resources/temp/";
    
    public static File getFile(final byte[] file) throws FileNotFoundException, IOException {
        return getFile("resources/temp/", String.valueOf(file.hashCode()), file);
    }
    
    public static File getFile(final String fileName, final byte[] file) throws FileNotFoundException, IOException {
        return getFile("resources/temp/", fileName, file);
    }
    
    public static File getFile(final String folderName, String fileName, final byte[] file) throws FileNotFoundException, IOException {
        final File folder = new File(folderName);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        if (folder.exists()) {
            fileName = folderName + fileName;
            final FileOutputStream fileOuputStream = new FileOutputStream(fileName);
            fileOuputStream.write(file);
            fileOuputStream.close();
            return new File(fileName);
        }
        throw new RuntimeException("File or Folder cant be created or does not exist!");
    }
    
    public static InputStream createInputStream(final File file) throws FileNotFoundException {
        final InputStream stream = new FileInputStream(file);
        return stream;
    }
    
    public static File createFile(final String name) {
        final File file = new File(name);
        return file;
    }
}
