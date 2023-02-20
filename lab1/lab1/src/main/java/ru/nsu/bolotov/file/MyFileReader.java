package ru.nsu.bolotov.file;

import java.io.*;
public class FileReader {
    public FileReader(String path) throws FileNotFoundException {
        this.path = new File(path);
        if (!this.path.exists()) {
            throw new FileNotFoundException();
        }
        in = new BufferedReader(new java.io.FileReader(path));
    }
    public String getNextWord() throws IOException {
        StringBuilder currentWord = new StringBuilder();
        int characterCode = 0;
        while ((characterCode = in.read()) != -1) {
            if (Character.isLetterOrDigit((char) characterCode)) {
                currentWord.append((char) characterCode);
            }
            else {
                break;
            }
        }
        return currentWord.toString();
    }
    private final BufferedReader in;
    private final File path;
}
