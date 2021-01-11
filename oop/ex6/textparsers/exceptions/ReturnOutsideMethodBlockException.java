package oop.ex6.textparsers.exceptions;

/**
 * This exception is thrown when there is a return statement outside of a method block.
 * @author Shany Gindi and Roy Urbach
 */
public class ReturnOutsideMethodBlockException extends OneLinerException {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "A return statement outside of a method block.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
