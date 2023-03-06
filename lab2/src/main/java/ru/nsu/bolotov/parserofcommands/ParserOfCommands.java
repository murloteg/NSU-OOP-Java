package ru.nsu.bolotov.parserofcommands;

import ru.nsu.bolotov.commands.*;
import ru.nsu.bolotov.context.Context;
import ru.nsu.bolotov.exceptions.*;
import ru.nsu.bolotov.factory.Factory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import static ru.nsu.bolotov.util.UtilConsts.*;

public class ParserOfCommands {
    private String nextCommandLine;
    private Scanner scanner = null;
    private BufferedReader in = null;
    private final Context context;

    public ParserOfCommands(Context context) {
        scanner = new Scanner(System.in);
        this.context = context;
    }

    public ParserOfCommands(String path, Context context) {
        this.context = context;
        try {
            in = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException exception) {
            throw new IllegalFilePathException();
        }
    }

    public void findAndExecuteNextCommand() {
        if (scanner != null) {
            nextCommandLine = scanner.nextLine();
        } else {
            try {
                nextCommandLine = in.readLine();
            } catch (IOException exception) {
                throw new FileReadingException();
            }
        }

        Command nextCommand;
        String[] separatedCommand;
        if (nextCommandLine.contains(PUSH)) {
            separatedCommand = nextCommandLine.split(" ");
            if (!checkNumberOfArgs(PUSH, separatedCommand)) {
                throw new InvalidNumberOfArgsException();
            }
            try {
                nextCommand = Factory.create(PUSH);
            } catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException exception) {
                throw new FailedCreationException();
            }
            nextCommand.execute(new Object[] {separatedCommand[1]}, context);
            // TODO: finish this command and other commands.
            // TODO: think about passing args to execute.
        } else if (nextCommandLine.contains(POP)) {
            separatedCommand = nextCommandLine.split(" ");
            if (!checkNumberOfArgs(POP, separatedCommand)) {
                throw new InvalidNumberOfArgsException();
            }
        } else if (nextCommandLine.contains(PLUS)) {
            separatedCommand = nextCommandLine.split(" ");
            if (!checkNumberOfArgs(PLUS, separatedCommand)) {
                throw new InvalidNumberOfArgsException();
            }
        } else if (nextCommandLine.contains(MINUS)) {
            separatedCommand = nextCommandLine.split(" ");
            if (!checkNumberOfArgs(MINUS, separatedCommand)) {
                throw new InvalidNumberOfArgsException();
            }
        } else if (nextCommandLine.contains(MULTIPLY)) {
            separatedCommand = nextCommandLine.split(" ");
            if (!checkNumberOfArgs(MULTIPLY, separatedCommand)) {
                throw new InvalidNumberOfArgsException();
            }
        } else if (nextCommandLine.contains(DIVIDE)) {
            separatedCommand = nextCommandLine.split(" ");
            if (!checkNumberOfArgs(DIVIDE, separatedCommand)) {
                throw new InvalidNumberOfArgsException();
            }
        } else if (nextCommandLine.contains(SQRT)) {
            separatedCommand = nextCommandLine.split(" ");
            if (!checkNumberOfArgs(SQRT, separatedCommand)) {
                throw new InvalidNumberOfArgsException();
            }
        } else if (nextCommandLine.contains(PRINT)) {
            separatedCommand = nextCommandLine.split(" ");
            if (!checkNumberOfArgs(PRINT, separatedCommand)) {
                throw new InvalidNumberOfArgsException();
            }
        } else if (nextCommandLine.contains(DEFINE)) {
            separatedCommand = nextCommandLine.split(" ");
            if (!checkNumberOfArgs(DEFINE, separatedCommand)) {
                throw new InvalidNumberOfArgsException();
            }
        } else {
            throw new InvalidTypeOfArgumentException();
        }
    }

    private boolean checkNumberOfArgs(String commandType, String[] separatedCommand) {
        switch (commandType) {
            case PUSH: {
                return (separatedCommand.length == 2);
            }
            case POP:
            case PLUS:
            case MINUS:
            case MULTIPLY:
            case DIVIDE:
            case SQRT:
            case PRINT: {
                return (separatedCommand.length == 1);
            }
            case DEFINE: {
                return (separatedCommand.length == 3);
            }
            default: {
                throw new InvalidTypeOfArgumentException();
            }
        }
    }
}
