package ru.nsu.bolotov.application;

import ru.nsu.bolotov.container.Container;
import ru.nsu.bolotov.file.FileChecker;
import ru.nsu.bolotov.file.MyFileReader;
import ru.nsu.bolotov.file.MyFileWriter;
import ru.nsu.bolotov.storageunit.ArrayOfResult;

import static ru.nsu.bolotov.utility.UtilityInfo.printInfo;
import static ru.nsu.bolotov.utility.UtilityStringConsts.EMPTY;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            printInfo();
            System.exit(0);
        }
        FileChecker fileChecker = new FileChecker(args[0]);
        fileChecker.checkExtension();

        Container container = new Container();
        try (MyFileReader myFileReader = new MyFileReader(args[0])) {
            String word;
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

        ArrayOfResult arrayOfResult = new ArrayOfResult(container);
        MyFileWriter myFileWriter = new MyFileWriter();
        myFileWriter.outputData(arrayOfResult);
    }
}
