package de.ulfbiallas.tyreogem.mesh.io.obj;

import java.io.File;
import java.util.Arrays;

public class ObjFileDescriptor {

    private final String directoryPath;

    private final String fileName;

    public ObjFileDescriptor(String directoryPath, String fileName) {
        this.directoryPath = directoryPath;
        this.fileName = fileName;
    }

    public ObjFileDescriptor(File directory, String fileName) {
        if(!directory.isDirectory()) {
            throw new IllegalArgumentException("Argument does not specify a directory!");
        }
        this.directoryPath = directory.getAbsolutePath();
        this.fileName = fileName;
    }

    public ObjFileDescriptor(File file) {
        this.directoryPath = file.getParentFile().getAbsolutePath() + "\\";
        final String fullFileName = file.getName();
        final String[] fileNameParts = fullFileName.split("\\.");
        if(fileNameParts.length > 1) {
            this.fileName = String.join("", Arrays.copyOf(fileNameParts, fileNameParts.length-1));
        } else {
            this.fileName = fullFileName;
        }
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public String getFileName() {
        return fileName;
    }

    public File getObjFile() {
        return new File(directoryPath, fileName + ".obj");
    }

    public File getMtlFile() {
        return new File(directoryPath, fileName + ".mtl");
    }
}
