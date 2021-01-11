package oop.ex6.textparsers.exceptions;

import oop.ex6.blocks.exceptions.BlockException;

/**
 * This exception is thrown when trying to open a condition block in the main block of the program.
 * @author Shany Gindi and Roy Urbach
 */
public class ConditionInMainBlockException extends BlockException {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Tried to open a condition block in the main block of the program.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
