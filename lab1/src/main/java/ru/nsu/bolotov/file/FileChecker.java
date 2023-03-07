package ru.nsu.bolotov.file;

import ru.nsu.bolotov.exceptions.InvalidFileExtension;

import java.util.regex.Pattern;

public class FileChecker {
    private final Pattern[] patterns;
    private final String path;

    public FileChecker(String path) {
        this.path = path;
        patterns = new Pattern[] {Pattern.compile("^[\\w&&[^.]]*\\.txt$"), Pattern.compile("^[\\w&&[^.]]*\\.doc$"),
                Pattern.compile("^[\\w&&[^.]]*$")};
    }

    public void checkExtension() {
        boolean status = false;
        for (Pattern pattern : patterns) {
            status |= path.matches(pattern.pattern());
        }
        if (!status) {
            throw new InvalidFileExtension();
        }
    }
}
