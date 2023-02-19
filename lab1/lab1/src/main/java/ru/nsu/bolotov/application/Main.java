package ru.nsu.bolotov.application;

import ru.nsu.bolotov.file.FileChecker;
import ru.nsu.bolotov.file.FileReader;
import ru.nsu.bolotov.exceptions.InvalidFileExtension;
import static ru.nsu.bolotov.application.info.UtilityInfo.printInfo;;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            printInfo();
            System.exit(0);
        }
        FileChecker fileChecker = new FileChecker(args[0]);
        try {
            fileChecker.checkExtension();
        }
        catch (InvalidFileExtension extension) {
            System.err.println(extension.getMessage());
            extension.printStackTrace();
            System.exit(1);
        }
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(args[0]);
        }
        catch (FileNotFoundException exception) {
            System.err.println(exception.getMessage());
            exception.printStackTrace();
            System.exit(2);
        }
        /*
        String word = "";
        try {
            word = fileReader.getNextWord();
        }
        catch (IOException exception) {
            System.err.println(exception.getMessage());
            exception.printStackTrace();
            System.exit(3);
        }
        System.out.println(word);
        */
    }
}
