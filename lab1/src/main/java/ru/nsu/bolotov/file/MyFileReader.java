package ru.nsu.bolotov.file;

import ru.nsu.bolotov.exceptions.FailedFileCloseException;
import ru.nsu.bolotov.exceptions.FailedFileReadException;
import ru.nsu.bolotov.exceptions.InvalidFilePath;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class MyFileReader implements AutoCloseable {
    private final BufferedReader in;

    public MyFileReader(String path) {
        try {
            in = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException exception) {
            throw new InvalidFilePath(exception.getMessage());
        }
    }

    public String getNextWord() {
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

    public void close() {
        try {
            in.close();
        } catch (IOException exception) {
            throw new FailedFileCloseException(exception.getMessage());
        }
    }
}
