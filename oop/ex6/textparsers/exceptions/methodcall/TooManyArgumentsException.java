package oop.ex6.textparsers.exceptions.methodcall;

/**
 * This exception is thrown when calling a method with too many arguments.
 * @author Shany Gindi and Roy Urbach
 */
public class TooManyArgumentsException extends MethodCallException {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Tried to call a method with more arguments than its parameters.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}

