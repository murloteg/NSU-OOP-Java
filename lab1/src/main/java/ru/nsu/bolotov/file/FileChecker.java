package ru.nsu.bolotov.file;

import java.util.regex.Pattern;

import ru.nsu.bolotov.exceptions.InvalidFileExtension;

public class FileChecker {
    private final Pattern[] patterns;
    private final String path;

    public FileChecker(String path) {
        this.path = path;
        patterns = new Pattern[] {Pattern.compile("^[\\w&&[^.]]*\\.txt$"), Pattern.compile("^[\\w&&[^.]]*\\.doc$"),
                Pattern.compile("^[\\w&&[^.]]*$")};
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
}
