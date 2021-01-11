package oop.ex6.textparsers.exceptions;

/**
 * This exception is thrown when calling a method without enough arguments.
 * @author Shany Gindi and Roy Urbach
 */
public class TooLittleArgumentsException extends MethodCallException {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Tried to call a method with less arguments than its parameters.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
