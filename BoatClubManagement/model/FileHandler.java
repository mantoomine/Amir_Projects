package BoatClubManagement.model;

import java.io.*;

public class FileHandler {
    private final String path;
    public FileHandler(String path) {
        this.path = path + ".txt";
    }

    public void writeToFile(Object object) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(object);
        objectOutputStream.close();
    }

    public Object readFromFile() throws Exception {
        FileInputStream fileInputStream = new FileInputStream(path);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Object object = objectInputStream.readObject();
        objectInputStream.close();
        return object;
    }

    public boolean containsReadableFile() {
        File file = new File(path);
        return file.isFile() && file.canRead();
    }
}