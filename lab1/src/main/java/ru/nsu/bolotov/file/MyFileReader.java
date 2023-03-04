package ru.nsu.bolotov.file;

import ru.nsu.bolotov.exceptions.FailedFileCloseException;
import ru.nsu.bolotov.exceptions.FailedFileReadException;
import ru.nsu.bolotov.exceptions.InvalidFilePath;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MyFileReader implements AutoCloseable {
    private final BufferedReader in;

    public MyFileReader(String path) throws InvalidFilePath {
        try {
            in = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException exception) {
            throw new InvalidFilePath(exception.getMessage());
        }
    }

    public String getNextWord() throws FailedFileReadException {
        StringBuilder currentWord = new StringBuilder();
        int characterCode;
        try {
            while ((characterCode = in.read()) != -1) {
                if (Character.isLetterOrDigit((char) characterCode)) {
                    currentWord.append((char) characterCode);
                } else if (currentWord.length() > 0) {
                    break;
                }
            }
        } catch (IOException exception) {
            throw new FailedFileReadException(exception.getMessage());
        }

        if (characterCode == -1 && currentWord.length() == 0) {
            return null;
        }
        return currentWord.toString();
    }

    public void close() throws FailedFileCloseException {
        try {
        in.close();
        } catch (IOException exception) {
            throw new FailedFileCloseException(exception.getMessage());
        }
    }
}
