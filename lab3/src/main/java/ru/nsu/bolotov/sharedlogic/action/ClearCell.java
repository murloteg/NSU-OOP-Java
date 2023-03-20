package ru.nsu.bolotov.sharedlogic.action;

import ru.nsu.bolotov.exceptions.InvalidFieldPositionException;
import ru.nsu.bolotov.exceptions.InvalidNumberOfArguments;
import ru.nsu.bolotov.exceptions.TryingToClearCellWithoutFlagException;
import ru.nsu.bolotov.exceptions.UnexpectedArgumentType;
import ru.nsu.bolotov.sharedlogic.field.Field;
import ru.nsu.bolotov.util.UtilConsts;

import java.util.ArrayList;

@ActionAnnotation
public class ClearCell implements Action {
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
        checkFlagOnThisCell((Field) args.get(0), x, y);
        int position = y * fieldSize + x;
        ((Field) args.get(0)).getArrayOfFieldCells()[position] = UtilConsts.StatusesOfCells.HIDDEN;
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

    private void checkFlagOnThisCell(Field userField, int x, int y) {
        int position = y * userField.getFieldSize() + x;
        if (userField.getArrayOfFieldCells()[position] != UtilConsts.StatusesOfCells.FLAGGED) {
            throw new TryingToClearCellWithoutFlagException(x, y);
        }
    }
}
