package oop.ex6.textparsers.exceptions.methodcreation;

/**
 * This exception is abstract and represents all method calls exceptions.
 * @author Shany Gindi and Roy Urbach
 */
public class MethodNotInMainBlockException extends MethodCreationException {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Tried to declare a method in a lower hierarchy than allowed. not Main block.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
