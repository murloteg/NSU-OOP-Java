package ru.nsu.bolotov.file;

import java.io.*;
import ru.nsu.bolotov.storageunit.*;

public class MyFileWriter {
    public MyFileWriter() throws IOException {
        out = new BufferedWriter(new FileWriter(output));
    }
    public void outputData(ArrayOfResult arrayOfResult) throws IOException {
        for (StorageUnit storageUnit : arrayOfResult.getArray()) {
            out.write(storageUnit.getWord());
            out.write(DELIMITER);
            out.write(storageUnit.getFrequency());
            out.write(DELIMITER);
            out.write(storageUnit.getRatioInPercents());
            out.newLine();
        }
        out.close();
    }
    private final File output = new File("output.csv");
    private final BufferedWriter out;
    private static final String DELIMITER = "\t";
}
