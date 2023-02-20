package ru.nsu.bolotov.application;

import ru.nsu.bolotov.file.FileChecker;
import ru.nsu.bolotov.file.*;
import ru.nsu.bolotov.exceptions.InvalidFileExtension;
import ru.nsu.bolotov.container.*;
import ru.nsu.bolotov.storageunit.*;
import static ru.nsu.bolotov.utility.UtilityInfo.printInfo;
import static ru.nsu.bolotov.utility.UtilityStringConsts.EMPTY;
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
        catch (InvalidFileExtension exception) {
            System.err.println(exception.getMessage());
            exception.printStackTrace();
            System.exit(1);
        }
        MyFileReader myFileReader = null;
        try {
            myFileReader = new MyFileReader(args[0]);
        }
        catch (FileNotFoundException exception) {
            System.err.println(exception.getMessage());
            exception.printStackTrace();
            System.exit(2);
        }
        Container container = new Container();
        String word;
        try {
            while (true) {
                word = myFileReader.getNextWord();
                if (word == null) {
                    break;
                }
                if (!word.equals(EMPTY)) {
                    container.addWordToMap(word);
                }
            }
        }
        catch (IOException exception) {
            System.err.println(exception.getMessage());
            exception.printStackTrace();
            System.exit(3);
        }
        finally {
            try {
                myFileReader.close();
            }
            catch (IOException exception) {
                System.out.println(exception.getMessage());
                exception.printStackTrace();
                System.exit(4);
            }
        }
        ArrayOfResult arrayOfResult = new ArrayOfResult(container);
        MyFileWriter myFileWriter;
        try {
            myFileWriter = new MyFileWriter();
            myFileWriter.outputData(arrayOfResult);
        }
        catch (IOException exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
            System.exit(5);
        }
    }
}
