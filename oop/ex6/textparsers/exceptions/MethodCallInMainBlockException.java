package oop.ex6.textparsers.exceptions;

/**
 * This exception is thrown whenever trying to call a method outside of a method block.
 * @author Shany Gindi and Roy Urbach
 */
public class MethodCallInMainBlockException extends MethodCallException {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Called a method in the global scope.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
