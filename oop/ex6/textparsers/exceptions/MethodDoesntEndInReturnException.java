package oop.ex6.textparsers.exceptions;

/**
 * This exception is abstract and represents all method calls exceptions.
 * @author Shany Gindi and Roy Urbach
 */
public class MethodDoesntEndInReturnException extends MethodCreationException {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Tried to create a method without using return at the last line.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
