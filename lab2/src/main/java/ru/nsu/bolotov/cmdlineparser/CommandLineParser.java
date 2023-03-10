package ru.nsu.bolotov.cmdlineparser;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class CommandLineParser {
    @Parameter(names = {"--path", "-p"})
    public static String inputPath;

    public static void main(String[] args) {
        CommandLineParser target = new CommandLineParser();
        JCommander.newBuilder()
                .addObject(target)
                .build()
                .parse(args);
    }
}
