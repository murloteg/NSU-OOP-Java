package ru.nsu.bolotov.file;

import ru.nsu.bolotov.storageunit.*;
import ru.nsu.bolotov.exceptions.FailedFileWriteException;
import ru.nsu.bolotov.exceptions.InvalidFilePath;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MyFileWriter {
    private final BufferedWriter out;
    private static final String DELIMITER = "\t";

    public MyFileWriter() throws InvalidFilePath {
        File output = new File("output.csv");
        try {
            out = new BufferedWriter(new FileWriter(output));
        } catch (IOException exception) {
            throw new InvalidFilePath(exception.getMessage());
        }
    }

    public void outputData(ArrayOfResult arrayOfResult) throws FailedFileWriteException {
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
