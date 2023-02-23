package ru.nsu.bolotov.file;

import java.io.*;

public class MyFileReader {
    public MyFileReader(String path) throws FileNotFoundException {
        File currentPath = new File(path);
        if (!currentPath.exists()) {
            throw new FileNotFoundException();
        }
        in = new BufferedReader(new FileReader(path));
    }

    public String getNextWord() throws IOException {
        StringBuilder currentWord = new StringBuilder();
        int characterCode;
        while ((characterCode = in.read()) != -1) {
            if (Character.isLetterOrDigit((char) characterCode)) {
                currentWord.append((char) characterCode);
            }
            else if (currentWord.length() > 0) {
                break;
            }
        }
        if (characterCode == -1 && currentWord.length() == 0) {
            return null;
        }
        return currentWord.toString();
    }

    public void close() throws IOException {
        in.close();
    }
    private final BufferedReader in;
}
