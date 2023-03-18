package ru.nsu.bolotov.sharedlogic.action;

import ru.nsu.bolotov.exceptions.InvalidFieldPositionException;
import ru.nsu.bolotov.exceptions.InvalidNumberOfArguments;
import ru.nsu.bolotov.exceptions.UnexpectedArgumentType;
import ru.nsu.bolotov.sharedlogic.field.Field;
import ru.nsu.bolotov.util.UtilConsts;

import java.util.ArrayList;
import java.util.List;

@ActionAnnotation
public class OpenCell implements Action {
    @Override
    public void doAction(ArrayList<Object> args) {
        checkNumberOfArguments(args);
        int fieldSize;
        if (args.get(0) instanceof Field) {
            fieldSize = ((Field) args.get(0)).getFieldSize();
        } else {
            throw new UnexpectedArgumentType(Field.class.toString(), args.get(0).getClass().getSimpleName());
        }
        if (!(args.get(1) instanceof Field)) {
            throw new UnexpectedArgumentType(Field.class.toString(), args.get(0).getClass().getSimpleName());
        }

        int x;
        if (args.get(2) instanceof String) {
            x = Integer.parseInt((String) args.get(2));
        } else {
            throw new UnexpectedArgumentType(Integer.class.toString(), args.get(2).getClass().getSimpleName());
        }
        int y;
        if (args.get(3) instanceof String) {
            y = Integer.parseInt((String) args.get(3));
        } else {
            throw new UnexpectedArgumentType(Integer.class.toString(), args.get(3).getClass().getSimpleName());
        }
        checkPosition(x, y, fieldSize);
        int position = y * fieldSize + x;
        ((Field) args.get(0)).getArrayOfFieldCells()[position] = UtilConsts.StatusesOfCells.OPEN;
        tryExtendedOpening((Field) args.get(0), (Field) args.get(1), x, y);
    }

    @Override
    public void checkNumberOfArguments(ArrayList<Object> args) {
        if (args.size() != 4) {
            throw new InvalidNumberOfArguments(2, args.size() - 2);
        }
    }

    private void checkPosition(int x, int y, int fieldSize) {
        if (x < 0 || x >= fieldSize || y < 0 || y >= fieldSize) {
            throw new InvalidFieldPositionException(x, y, fieldSize);
        }
    }

    private void tryExtendedOpening(Field userField, Field logicField, int x, int y) {
        int fieldSize = userField.getFieldSize();
        int position = y * fieldSize + x;
        if (logicField.getArrayOfFieldCells()[position] == UtilConsts.StatusesOfCells.EMPTY) {
            logicField.getArrayOfFieldCells()[position] = UtilConsts.StatusesOfCells.VISITED_EMPTY;
            int topNeighbor = position - fieldSize;
            int bottomNeighbor = position + fieldSize;
            int leftNeighbor = position - 1;
            int rightNeighbor = position + 1;
            if (isVerticalNeighbors(position, topNeighbor, fieldSize)) {
                doAction(new ArrayList<>(List.of(userField, logicField, Integer.toString(x), Integer.toString(y - 1))));
            }
            if (isVerticalNeighbors(position, bottomNeighbor, fieldSize)) {
                doAction(new ArrayList<>(List.of(userField, logicField, Integer.toString(x), Integer.toString(y + 1))));
            }
            if (isHorizontalNeighbors(position, leftNeighbor, fieldSize)) {
                doAction(new ArrayList<>(List.of(userField, logicField, Integer.toString(x - 1), Integer.toString(y))));
            }
            if (isHorizontalNeighbors(position, rightNeighbor, fieldSize)) {
                doAction(new ArrayList<>(List.of(userField, logicField, Integer.toString(x + 1), Integer.toString(y))));
            }
        }
    }

    private boolean isVerticalNeighbors(int firstPosition, int secondPosition, int fieldSize) {
        return (firstPosition % fieldSize == secondPosition % fieldSize && secondPosition > 0 && secondPosition < fieldSize * fieldSize);
    }

    private boolean isHorizontalNeighbors(int firstPosition, int secondPosition, int fieldSize) {
        return (firstPosition / fieldSize == secondPosition / fieldSize && secondPosition > 0 && secondPosition < fieldSize * fieldSize);
    }
}
