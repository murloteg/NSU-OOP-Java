package ru.nsu.bolotov.file;

import java.util.regex.*;
import ru.nsu.bolotov.exceptions.InvalidFileExtension;

public class FileChecker {
    public FileChecker(String path) {
        this.path = path;
        patterns = new Pattern[] {Pattern.compile("^[\\w\\W]*\\.txt$"), Pattern.compile("^[\\w\\W]*\\.doc$"),
        Pattern.compile("^(\\.)?[\\w\\W&&[^.]]*[^.]")};
    }
    public void checkExtension() throws InvalidFileExtension {
        boolean status = false;
        for (Pattern pattern : patterns) {
            status |= path.matches(pattern.pattern());
        }
        if (!status) {
            throw new InvalidFileExtension();
        }
    }
    private final Pattern[] patterns;
    private final String path;
}
