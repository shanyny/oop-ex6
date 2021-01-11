package oop.ex6.textparsers.exceptions;

/**
 * This exception is thrown when trying to close a block outside of a block's scope.
 * @author Shany Gindi and Roy Urbach
 */
public class CloseBracketOutsideBlockException extends OneLinerException {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Tried to close a block out of block context.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
