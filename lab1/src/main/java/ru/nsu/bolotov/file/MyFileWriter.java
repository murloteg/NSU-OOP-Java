package ru.nsu.bolotov.file;

import ru.nsu.bolotov.exceptions.FailedFileWriteException;
import ru.nsu.bolotov.exceptions.InvalidFilePath;
import ru.nsu.bolotov.storageunit.ArrayOfResult;
import ru.nsu.bolotov.storageunit.StorageUnit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static ru.nsu.bolotov.utility.UtilityStringConsts.DELIMITER;

public class MyFileWriter {
    private final BufferedWriter out;

    public MyFileWriter() {
        File output = new File("output.csv");
        try {
            out = new BufferedWriter(new FileWriter(output));
        } catch (IOException exception) {
            throw new InvalidFilePath(exception.getMessage());
        }
    }

    public void outputData(ArrayOfResult arrayOfResult) {
        try {
            for (StorageUnit storageUnit : arrayOfResult.getArray()) {
                out.write(storageUnit.getWord());
                out.write(DELIMITER);
                out.write(storageUnit.getFrequency());
                out.write(DELIMITER);
                out.write(storageUnit.getRatioInPercents());
                out.newLine();
            }
            out.close();
        } catch (IOException exception) {
            throw new FailedFileWriteException(exception.getMessage());
        }
    }
}
