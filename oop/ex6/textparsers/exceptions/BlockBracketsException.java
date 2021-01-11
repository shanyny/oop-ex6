package oop.ex6.textparsers.exceptions;

import oop.ex6.blocks.exceptions.BlockException;

/**
 * This exception is thrown when reached the end of the file without closing a statement.
 * That is, missing a closing bracket.
 * @author Shany Gindi and Roy Urbach
 */
public class BlockBracketsException extends BlockException {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Reached end of program missing a bracket.";
    @Override
    public String getMessage() {
        return MESSAGE;
    }

}